-- ============================================================
-- InternMatch PostgreSQL Schema
-- Bölüm 4: Şirket Profil Yönetimi sütunları eklendi
-- ============================================================

-- Eğer tablolar zaten Hibernate tarafından oluşturulduysa,
-- aşağıdaki ALTER TABLE komutları eksik sütunları ekler.
-- Hibernate ddl-auto=update kullanıyorsa bu dosyaya gerek kalmayabilir;
-- ancak production ortamında manuel migration olarak çalıştırılmalıdır.

SET search_path TO public;

-- companies tablosuna yeni sütunlar ekle (zaten varsa atla)
ALTER TABLE companies
    ADD COLUMN IF NOT EXISTS city             VARCHAR(100),
    ADD COLUMN IF NOT EXISTS address          TEXT,
    ADD COLUMN IF NOT EXISTS contact_email    VARCHAR(100),
    ADD COLUMN IF NOT EXISTS contact_phone    VARCHAR(20),
    ADD COLUMN IF NOT EXISTS verification_status VARCHAR(20) NOT NULL DEFAULT 'PENDING',
    ADD COLUMN IF NOT EXISTS is_active        BOOLEAN NOT NULL DEFAULT TRUE;

-- name sütununa unique constraint ekle (yoksa)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'companies'
          AND constraint_type = 'UNIQUE'
          AND constraint_name = 'companies_name_key'
    ) THEN
        ALTER TABLE companies ADD CONSTRAINT companies_name_key UNIQUE (name);
    END IF;
END $$;

-- verification_status için CHECK constraint (yoksa)
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT 1 FROM information_schema.table_constraints
        WHERE table_name = 'companies'
          AND constraint_type = 'CHECK'
          AND constraint_name = 'companies_verification_status_check'
    ) THEN
        ALTER TABLE companies
            ADD CONSTRAINT companies_verification_status_check
            CHECK (verification_status IN ('PENDING', 'VERIFIED', 'REJECTED'));
    END IF;
END $$;

COMMENT ON COLUMN companies.city IS 'Şirketin bulunduğu şehir';
COMMENT ON COLUMN companies.address IS 'Şirket tam adresi';
COMMENT ON COLUMN companies.contact_email IS 'Şirket iletişim e-postası';
COMMENT ON COLUMN companies.contact_phone IS 'Şirket iletişim telefonu';
COMMENT ON COLUMN companies.verification_status IS 'Admin doğrulama durumu: PENDING, VERIFIED, REJECTED';
COMMENT ON COLUMN companies.is_active IS 'Şirketin aktif/pasif durumu';
