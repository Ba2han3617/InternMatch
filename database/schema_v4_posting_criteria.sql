-- ============================================================
-- InternMatch PostgreSQL Schema
-- Bolum 6: Staj ilani kriterleri ve agirlik sistemi
-- ============================================================
-- Hibernate ddl-auto=update ile bu tablo otomatik olusabilir.
-- Production ortaminda bu script manuel migration olarak kullanilabilir.
-- Toplam agirlik <= 100 kurali uygulama servis katmaninda kontrol edilir.
-- ============================================================

SET search_path TO public;

CREATE TABLE IF NOT EXISTS posting_criteria (
    id BIGSERIAL PRIMARY KEY,
    posting_id BIGINT NOT NULL,
    type VARCHAR(30) NOT NULL,
    skill_id BIGINT,
    required_skill_level VARCHAR(20),
    string_value VARCHAR(255),
    numeric_value NUMERIC(5, 2),
    is_mandatory BOOLEAN NOT NULL DEFAULT FALSE,
    weight NUMERIC(5, 2) NOT NULL,
    CONSTRAINT fk_posting_criteria_posting
        FOREIGN KEY (posting_id) REFERENCES internship_postings(id) ON DELETE CASCADE,
    CONSTRAINT fk_posting_criteria_skill
        FOREIGN KEY (skill_id) REFERENCES skills(id),
    CONSTRAINT posting_criteria_type_check
        CHECK (type IN ('SKILL', 'LANGUAGE', 'DEPARTMENT', 'GPA', 'GRADE_LEVEL', 'LOCATION', 'WORK_MODE', 'CUSTOM')),
    CONSTRAINT posting_criteria_skill_level_check
        CHECK (required_skill_level IS NULL OR required_skill_level IN ('BEGINNER', 'INTERMEDIATE', 'ADVANCED')),
    CONSTRAINT posting_criteria_weight_check
        CHECK (weight >= 1 AND weight <= 100),
    CONSTRAINT posting_criteria_gpa_check
        CHECK (numeric_value IS NULL OR (numeric_value >= 0 AND numeric_value <= 4))
);

CREATE INDEX IF NOT EXISTS idx_posting_criteria_posting_id
    ON posting_criteria (posting_id);

CREATE INDEX IF NOT EXISTS idx_posting_criteria_skill_id
    ON posting_criteria (skill_id);

CREATE INDEX IF NOT EXISTS idx_posting_criteria_type
    ON posting_criteria (type);

COMMENT ON TABLE posting_criteria IS 'Staj ilanlarina ait degerlendirme kriterleri ve agirlik puanlari';
COMMENT ON COLUMN posting_criteria.posting_id IS 'Kriterin bagli oldugu staj ilani';
COMMENT ON COLUMN posting_criteria.type IS 'Kriter tipi: SKILL, LOCATION, WORK_MODE, GPA, GRADE_LEVEL, CUSTOM';
COMMENT ON COLUMN posting_criteria.skill_id IS 'SKILL kriteri icin beklenen beceri';
COMMENT ON COLUMN posting_criteria.required_skill_level IS 'SKILL kriteri icin beklenen beceri seviyesi';
COMMENT ON COLUMN posting_criteria.string_value IS 'LOCATION, WORK_MODE, GRADE_LEVEL veya CUSTOM kriter metni';
COMMENT ON COLUMN posting_criteria.numeric_value IS 'GPA kriteri icin minimum not ortalamasi';
COMMENT ON COLUMN posting_criteria.is_mandatory IS 'Kriterin zorunlu olup olmadigi';
COMMENT ON COLUMN posting_criteria.weight IS 'Kriter agirligi; ayni ilanda toplam agirlik servis katmaninda 100 ile sinirlanir';
