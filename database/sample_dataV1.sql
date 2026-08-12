SET search_path TO public;

INSERT INTO roles (name)
VALUES 
('ROLE_STUDENT'),
('ROLE_COMPANY'),
('ROLE_ADMIN')
ON CONFLICT (name) DO NOTHING;

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