-- ============================================================
-- InternMatch - Örnek Şirket Verileri (Bölüm 4)
-- PostgreSQL uyumlu
-- ============================================================

SET search_path TO public;

-- ─── Roller (önceden eklenmediyse) ────────────────────────────────────────────
INSERT INTO roles (name)
VALUES
    ('ROLE_STUDENT'),
    ('ROLE_COMPANY'),
    ('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

-- ─── Beceriler (önceden eklenmediyse) ─────────────────────────────────────────
INSERT INTO skills (name, category)
VALUES
    ('Java', 'Backend'),
    ('Spring Boot', 'Backend'),
    ('PostgreSQL', 'Database'),
    ('SQL', 'Database'),
    ('HTML', 'Frontend'),
    ('CSS', 'Frontend'),
    ('JavaScript', 'Frontend'),
    ('React', 'Frontend'),
    ('Git', 'Tools'),
    ('REST API', 'Backend')
ON CONFLICT (name) DO NOTHING;

-- ─── Örnek Şirketler ──────────────────────────────────────────────────────────
-- Şifre: Company123! → BCrypt hash (önceden üretilmiş)
-- Gerçek uygulamada bu şifreler AuthService.register() ile oluşturulmalıdır.
-- Hash: $2a$10$1tKwkRKW1oeQGxPhD9k3MuDX5B0P5L9k6Ygb7WX3vcJZ3I4lqv.Qe
-- NOT: Aşağıdaki şirketler doğrudan companies tablosuna eklenir.
--      users tablosuna şirket yetkilisi eklenmek istenirse
--      önce user oluşturulup ardından company_id güncellenmelidir.

INSERT INTO companies (name, industry, city, location, address, description, website,
                       contact_email, contact_phone, tax_number,
                       verification_status, is_active, created_at, updated_at)
VALUES
    (
        'TechNova Yazılım',
        'Yazılım ve Teknoloji',
        'İstanbul',
        'Maslak',
        'Maslak Mahallesi, Büyükdere Caddesi No:123 Sarıyer/İstanbul',
        'Kurumsal yazılım çözümleri ve bulut teknolojileri alanında faaliyet gösteren yenilikçi teknoloji şirketi.',
        'https://www.technova.com.tr',
        'info@technova.com.tr',
        '+90 212 555 01 00',
        '1234567890',
        'VERIFIED',
        TRUE,
        NOW(),
        NOW()
    ),
    (
        'DataBridge Analytics',
        'Veri Analizi ve Yapay Zeka',
        'Ankara',
        'Çankaya',
        'Kızılırmak Mahallesi, Dumlupınar Bulvarı No:9A Çankaya/Ankara',
        'Büyük veri analitiği, makine öğrenmesi ve iş zekası çözümleri sunan veri odaklı şirket.',
        'https://www.databridge.com.tr',
        'hr@databridge.com.tr',
        '+90 312 444 02 00',
        '9876543210',
        'VERIFIED',
        TRUE,
        NOW(),
        NOW()
    ),
    (
        'SoftLine Teknoloji',
        'E-Ticaret ve Mobil Uygulamalar',
        'İzmir',
        'Konak',
        'Alsancak Mahallesi, Kıbrıs Şehitleri Caddesi No:55 Konak/İzmir',
        'Mobil uygulama geliştirme ve e-ticaret platformları üzerine uzmanlaşmış yazılım şirketi.',
        'https://www.softline.com.tr',
        'kariyer@softline.com.tr',
        '+90 232 333 03 00',
        '5555555555',
        'PENDING',
        TRUE,
        NOW(),
        NOW()
    )
ON CONFLICT (name) DO NOTHING;

-- ─── Örnek Şirket Kullanıcıları ───────────────────────────────────────────────
-- Şifre tüm şirket kullanıcıları için: Company123!
-- BCrypt hash ($2a$10$...): Bu hash değeri gerçek uygulamada register endpoint'i
-- ile üretilmeli ve veritabanına kaydedilmelidir.
-- Aşağıdaki örnek, geliştirme ortamı için önceden hashlenmiş şifre kullanır.
-- Hash: $2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi (=password)
-- Gerçek Company123! hashi için uygulamayı başlatıp /api/auth/register kullanın.

-- Şirket kullanıcıları: önce users tablosuna ekle, sonra company_id güncelle
-- (Çakışma önleme: email unique constraint)

-- TechNova yetkilisi
INSERT INTO users (email, password, first_name, last_name, phone, is_active, created_at, updated_at)
VALUES (
    'yetkili@technova.com.tr',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Ahmet',
    'Yılmaz',
    '+90 532 111 0001',
    TRUE,
    NOW(),
    NOW()
) ON CONFLICT (email) DO NOTHING;

-- DataBridge yetkilisi
INSERT INTO users (email, password, first_name, last_name, phone, is_active, created_at, updated_at)
VALUES (
    'yetkili@databridge.com.tr',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Elif',
    'Kaya',
    '+90 532 222 0002',
    TRUE,
    NOW(),
    NOW()
) ON CONFLICT (email) DO NOTHING;

-- SoftLine yetkilisi
INSERT INTO users (email, password, first_name, last_name, phone, is_active, created_at, updated_at)
VALUES (
    'yetkili@softline.com.tr',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'Mehmet',
    'Demir',
    '+90 532 333 0003',
    TRUE,
    NOW(),
    NOW()
) ON CONFLICT (email) DO NOTHING;

-- ─── ROLE_COMPANY rolünü şirket kullanıcılarına ata ───────────────────────────
INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'yetkili@technova.com.tr'
  AND r.name = 'ROLE_COMPANY'
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'yetkili@databridge.com.tr'
  AND r.name = 'ROLE_COMPANY'
ON CONFLICT DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'yetkili@softline.com.tr'
  AND r.name = 'ROLE_COMPANY'
ON CONFLICT DO NOTHING;

-- ─── Kullanıcıları şirketlere bağla (company_id güncelle) ─────────────────────
UPDATE users
SET company_id = (SELECT id FROM companies WHERE name = 'TechNova Yazılım' LIMIT 1)
WHERE email = 'yetkili@technova.com.tr'
  AND company_id IS NULL;

UPDATE users
SET company_id = (SELECT id FROM companies WHERE name = 'DataBridge Analytics' LIMIT 1)
WHERE email = 'yetkili@databridge.com.tr'
  AND company_id IS NULL;

UPDATE users
SET company_id = (SELECT id FROM companies WHERE name = 'SoftLine Teknoloji' LIMIT 1)
WHERE email = 'yetkili@softline.com.tr'
  AND company_id IS NULL;

-- ─── Admin kullanıcısı (yoksa ekle) ───────────────────────────────────────────
INSERT INTO users (email, password, first_name, last_name, is_active, created_at, updated_at)
VALUES (
    'admin@internmatch.com',
    '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi',
    'System',
    'Admin',
    TRUE,
    NOW(),
    NOW()
) ON CONFLICT (email) DO NOTHING;

INSERT INTO user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE u.email = 'admin@internmatch.com'
  AND r.name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;
