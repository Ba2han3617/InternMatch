-- ============================================================
-- InternMatch - Örnek Staj İlanı Verileri (Bölüm 5)
-- PostgreSQL uyumlu
-- ============================================================
-- ÖNEMLİ: Bu script çalıştırılmadan önce sample_data_v2_company.sql
-- çalıştırılmış ve şirket/kullanıcı kayıtları oluşturulmuş olmalıdır.
-- ============================================================

SET search_path TO public;

-- ─── Örnek Staj İlanları ──────────────────────────────────────────────────────

-- 1. TechNova Yazılım - Backend Developer Intern (PUBLISHED)
INSERT INTO internship_postings (
    title,
    description,
    position_name,
    department,
    city,
    location,
    work_mode,
    status,
    min_gpa,
    department_requirement,
    preferred_grade_level,
    start_date,
    end_date,
    application_deadline,
    quota,
    company_id,
    created_at,
    updated_at
)
SELECT
    'Backend Developer Stajyeri Aranıyor',
    'TechNova Yazılım olarak Spring Boot, Java ve mikroservis mimarisi üzerine çalışacak yetenekli stajyerler arıyoruz. '
    'Projelerimizde REST API geliştirme, veritabanı optimizasyonu ve DevOps süreçlerinde deneyim kazanma fırsatı sunuyoruz. '
    'Takım arkadaşlarımızla birlikte gerçek iş problemlerini çözme deneyimi edineceksiniz.',
    'Backend Developer Intern',
    'Software Engineering',
    'İstanbul',
    'Maslak',
    'HYBRID',
    'PUBLISHED',
    2.50,
    'Software Engineering',
    '3. veya 4. Sınıf',
    CURRENT_DATE + INTERVAL '15 days',
    CURRENT_DATE + INTERVAL '105 days',
    CURRENT_DATE + INTERVAL '14 days',
    3,
    c.id,
    NOW(),
    NOW()
FROM companies c
WHERE c.name = 'TechNova Yazılım'
LIMIT 1;

-- 2. DataBridge Analytics - Data Analyst Intern (PUBLISHED)
INSERT INTO internship_postings (
    title,
    description,
    position_name,
    department,
    city,
    location,
    work_mode,
    status,
    min_gpa,
    department_requirement,
    preferred_grade_level,
    start_date,
    end_date,
    application_deadline,
    quota,
    company_id,
    created_at,
    updated_at
)
SELECT
    'Veri Analisti Stajyeri Aranıyor',
    'DataBridge Analytics bünyesinde büyük veri analizi, makine öğrenmesi modelleri geliştirme ve iş zekası raporlama '
    'süreçlerinde yer alacak stajyerler arıyoruz. Python, SQL ve veri görselleştirme araçlarını kullanarak '
    'gerçek veri setleri üzerinde çalışma fırsatı sunuyoruz. Yapay zeka projelerinde aktif rol üstlenebilirsiniz.',
    'Data Analyst Intern',
    'Data Science & Analytics',
    'Ankara',
    'Çankaya',
    'REMOTE',
    'PUBLISHED',
    3.00,
    'Data Science & Analytics',
    '3. veya 4. Sınıf',
    CURRENT_DATE + INTERVAL '20 days',
    CURRENT_DATE + INTERVAL '110 days',
    CURRENT_DATE + INTERVAL '19 days',
    2,
    c.id,
    NOW(),
    NOW()
FROM companies c
WHERE c.name = 'DataBridge Analytics'
LIMIT 1;

-- 3. SoftLine Teknoloji - Frontend Developer Intern (PUBLISHED)
INSERT INTO internship_postings (
    title,
    description,
    position_name,
    department,
    city,
    location,
    work_mode,
    status,
    min_gpa,
    department_requirement,
    preferred_grade_level,
    start_date,
    end_date,
    application_deadline,
    quota,
    company_id,
    created_at,
    updated_at
)
SELECT
    'Frontend Developer Stajyeri Aranıyor',
    'SoftLine Teknoloji olarak React, TypeScript ve modern frontend teknolojilerini kullanarak '
    'e-ticaret ve mobil uygulama projelerimizde çalışacak yaratıcı stajyerler arıyoruz. '
    'UX/UI tasarım süreçlerine dahil olacak, responsive web uygulamaları geliştirecek ve '
    'kullanıcı deneyimi odaklı çözümler üreteceksiniz.',
    'Frontend Developer Intern',
    'Frontend Development',
    'İzmir',
    'Konak',
    'ONSITE',
    'PUBLISHED',
    2.20,
    'Frontend Development',
    'Tüm Sınıflar',
    CURRENT_DATE + INTERVAL '10 days',
    CURRENT_DATE + INTERVAL '100 days',
    CURRENT_DATE + INTERVAL '9 days',
    5,
    c.id,
    NOW(),
    NOW()
FROM companies c
WHERE c.name = 'SoftLine Teknoloji'
LIMIT 1;

-- 4. TechNova Yazılım - Mobil Uygulama Stajyeri (DRAFT - yayında değil)
INSERT INTO internship_postings (
    title,
    description,
    position_name,
    department,
    city,
    location,
    work_mode,
    status,
    min_gpa,
    department_requirement,
    preferred_grade_level,
    quota,
    company_id,
    created_at,
    updated_at
)
SELECT
    'Mobil Uygulama Geliştirici Stajyeri',
    'Android ve iOS geliştirme deneyimi olan veya bu alanda kariyer hedefleyen stajyerler için fırsat.',
    'Mobile Developer Intern',
    'Mobile Development',
    'İstanbul',
    'Maslak',
    'HYBRID',
    'DRAFT',
    2.70,
    'Mobile Development',
    '3. veya 4. Sınıf',
    2,
    c.id,
    NOW(),
    NOW()
FROM companies c
WHERE c.name = 'TechNova Yazılım'
LIMIT 1;

-- 5. DataBridge Analytics - ML Engineer Intern (CLOSED - kapatılmış)
INSERT INTO internship_postings (
    title,
    description,
    position_name,
    department,
    city,
    location,
    work_mode,
    status,
    min_gpa,
    department_requirement,
    preferred_grade_level,
    start_date,
    end_date,
    application_deadline,
    quota,
    company_id,
    created_at,
    updated_at
)
SELECT
    'Makine Öğrenmesi Mühendisi Stajyeri',
    'NLP, bilgisayarlı görü veya tavsiye sistemi projelerinde çalışmak isteyen stajyerler için geçmiş dönem ilanı.',
    'ML Engineer Intern',
    'Artificial Intelligence',
    'Ankara',
    'Çankaya',
    'REMOTE',
    'CLOSED',
    3.20,
    'Artificial Intelligence',
    '4. Sınıf',
    CURRENT_DATE - INTERVAL '60 days',
    CURRENT_DATE - INTERVAL '1 day',
    CURRENT_DATE - INTERVAL '30 days',
    1,
    c.id,
    NOW() - INTERVAL '90 days',
    NOW() - INTERVAL '30 days'
FROM companies c
WHERE c.name = 'DataBridge Analytics'
LIMIT 1;
