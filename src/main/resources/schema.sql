CREATE TABLE IF NOT EXISTS teachers (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    specialization VARCHAR(120) NOT NULL,
    hire_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS courses (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(120) NOT NULL,
    description TEXT,
    teacher_id BIGINT NOT NULL,
    start_date DATE NOT NULL,
    FOREIGN KEY (teacher_id) REFERENCES teachers(id)
        ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS students (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(120) NOT NULL,
    email VARCHAR(120) NOT NULL UNIQUE,
    birth_date DATE NOT NULL,
    enrollment_date DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS enrollments (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    student_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    enrolled_at DATE NOT NULL,
    grade INT,
    FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(id) ON DELETE CASCADE ON UPDATE CASCADE
);

INSERT INTO teachers (name, email, specialization, hire_date) VALUES 
('Алексей Корнейчук', "AKorneychuk@ibagroup.eu", 'Java Development', '2019-06-12'),
('Виктор Викторович', 'viktor@gmail.com', 'Python Development', '2025-12-15'),
('Анатолий Михайлович', 'AMihailovich@mail.ru', 'Databases', '2023-10-25'),
('Михаил Семёнович', 'MSemenovich@gmail.com', 'Java Development', '2026-01-04');


INSERT INTO courses (title, description, teacher_id, start_date) VALUES
('Java Core', 'Синтаксис Java, ООП, Collections, Stream API', 1, '2025-11-25'),
('SQL Basics', 'Основы SQL и проектирования баз данны', 3, '2026-03-20'),
('Spring Basics', 'Введение в Spring Framework', 4, '2026-01-10');

INSERT INTO students (name, email, birth_date, enrollment_date) VALUES
('Гулюк Никита', 'nikaron4ik@gmail.com', '2007-06-04', '2025-10-01'),
('Орлов Дмитрий', 'orlov@gmail.com', '2007-10-10', '2025-10-01'),
('Семёнов Анатлоий', 'SAnat@gmail.com', '2004-01-02', '2026-02-10'),
('Вилковна Виктория', 'VVilkovna@gmail.com', '2004-10-24', '2026-04-01');

INSERT INTO enrollments (student_id, course_id, enrolled_at, grade) VALUES 
(1, 1, '2025-10-02', 88),
(1, 2, '2026-03-21', 80),
(2, 3, '2025-01-11', 79),
(2, 2, '2026-04-10', 91),
(4, 1, '2025-11-28', NULL),
(4, 3, '2026-01-12', 100);
