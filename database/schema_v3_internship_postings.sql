-- ============================================================
-- InternMatch PostgreSQL Schema
-- Bölüm 5: Staj İlanı Yönetim Modülü - Yeni Sütunlar
-- ============================================================
-- Hibernate ddl-auto=update ile bu sütunlar otomatik eklenecektir.
-- Production ortamında bu script manuel migration olarak kullanılabilir.
-- ============================================================

SET search_path TO public;

-- internship_postings tablosuna yeni sütunlar ekle (zaten varsa atla)
ALTER TABLE internship_postings
    ADD COLUMN IF NOT EXISTS position_name           VARCHAR(150),
    ADD COLUMN IF NOT EXISTS department              VARCHAR(100),
    ADD COLUMN IF NOT EXISTS city                    VARCHAR(100),
    ADD COLUMN IF NOT EXISTS preferred_grade_level   VARCHAR(50),
    ADD COLUMN IF NOT EXISTS start_date              DATE,
    ADD COLUMN IF NOT EXISTS end_date                DATE,
    ADD COLUMN IF NOT EXISTS quota                   INTEGER;

-- Mevcut 'location' sütunu korundu (geriye dönük uyumluluk)
-- Mevcut 'department_requirement' sütunu korundu (geriye dönük uyumluluk)

-- work_mode ve status sütunlarının NOT NULL kısıtlamalarını doğrula
DO $$
BEGIN
    -- work_mode sütunu yoksa ekle (Hibernate normalde oluşturur)
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'internship_postings'
          AND column_name = 'work_mode'
    ) THEN
        ALTER TABLE internship_postings
            ADD COLUMN work_mode VARCHAR(20) NOT NULL DEFAULT 'ONSITE';
    END IF;
END $$;

DO $$
BEGIN
    -- status sütunu yoksa ekle
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.columns
        WHERE table_name = 'internship_postings'
          AND column_name = 'status'
    ) THEN
        ALTER TABLE internship_postings
            ADD COLUMN status VARCHAR(20) NOT NULL DEFAULT 'DRAFT';
    END IF;
END $$;

-- CHECK constraint: status değerleri
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'internship_postings'
          AND constraint_type = 'CHECK'
          AND constraint_name = 'internship_postings_status_check'
    ) THEN
        ALTER TABLE internship_postings
            ADD CONSTRAINT internship_postings_status_check
            CHECK (status IN ('DRAFT', 'PUBLISHED', 'CLOSED', 'PASSIVE'));
    END IF;
END $$;

-- CHECK constraint: work_mode değerleri
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'internship_postings'
          AND constraint_type = 'CHECK'
          AND constraint_name = 'internship_postings_work_mode_check'
    ) THEN
        ALTER TABLE internship_postings
            ADD CONSTRAINT internship_postings_work_mode_check
            CHECK (work_mode IN ('REMOTE', 'ONSITE', 'HYBRID'));
    END IF;
END $$;

-- CHECK constraint: quota >= 1
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'internship_postings'
          AND constraint_type = 'CHECK'
          AND constraint_name = 'internship_postings_quota_check'
    ) THEN
        ALTER TABLE internship_postings
            ADD CONSTRAINT internship_postings_quota_check
            CHECK (quota IS NULL OR quota >= 1);
    END IF;
END $$;

-- CHECK constraint: tarih tutarlılığı (start_date <= end_date)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'internship_postings'
          AND constraint_type = 'CHECK'
          AND constraint_name = 'internship_postings_date_check'
    ) THEN
        ALTER TABLE internship_postings
            ADD CONSTRAINT internship_postings_date_check
            CHECK (start_date IS NULL OR end_date IS NULL OR start_date <= end_date);
    END IF;
END $$;

-- Performance için index'ler
CREATE INDEX IF NOT EXISTS idx_internship_postings_status
    ON internship_postings (status);

CREATE INDEX IF NOT EXISTS idx_internship_postings_company_id
    ON internship_postings (company_id);

CREATE INDEX IF NOT EXISTS idx_internship_postings_city
    ON internship_postings (city);

CREATE INDEX IF NOT EXISTS idx_internship_postings_work_mode
    ON internship_postings (work_mode);

CREATE INDEX IF NOT EXISTS idx_internship_postings_deadline
    ON internship_postings (application_deadline);

-- Sütun açıklamaları
COMMENT ON COLUMN internship_postings.position_name IS 'Pozisyon adı (ör. Backend Developer Intern)';
COMMENT ON COLUMN internship_postings.department IS 'Departman adı (ör. Software Engineering)';
COMMENT ON COLUMN internship_postings.city IS 'Staj yapılacak şehir';
COMMENT ON COLUMN internship_postings.preferred_grade_level IS 'Tercih edilen sınıf seviyesi (ör. 3. Sınıf)';
COMMENT ON COLUMN internship_postings.start_date IS 'Staj başlangıç tarihi';
COMMENT ON COLUMN internship_postings.end_date IS 'Staj bitiş tarihi';
COMMENT ON COLUMN internship_postings.quota IS 'Kontenjan sayısı (min: 1)';
COMMENT ON COLUMN internship_postings.status IS 'İlan durumu: DRAFT, PUBLISHED, CLOSED, PASSIVE';
COMMENT ON COLUMN internship_postings.work_mode IS 'Çalışma modeli: REMOTE, ONSITE, HYBRID';
