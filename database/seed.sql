-- ============================================================
-- Royal Education Center Management System
-- Seed Data
-- ============================================================

SET FOREIGN_KEY_CHECKS = 0;

-- ============================================================
-- TRUNCATE ALL TABLES (make seed idempotent / re-runnable)
-- ============================================================
TRUNCATE TABLE activity_logs;
TRUNCATE TABLE notifications;
TRUNCATE TABLE receipts;
TRUNCATE TABLE payments;
TRUNCATE TABLE exam_results;
TRUNCATE TABLE exam_questions;
TRUNCATE TABLE exams;
TRUNCATE TABLE assignment_submissions;
TRUNCATE TABLE assignments;
TRUNCATE TABLE attendance;
TRUNCATE TABLE timetable_slots;
TRUNCATE TABLE online_classes;
TRUNCATE TABLE physical_classes;
TRUNCATE TABLE enrollments;
TRUNCATE TABLE batches;
TRUNCATE TABLE course_subjects;
TRUNCATE TABLE subjects;
TRUNCATE TABLE courses;
TRUNCATE TABLE staff;
TRUNCATE TABLE teachers;
TRUNCATE TABLE students;
TRUNCATE TABLE role_permissions;
TRUNCATE TABLE permissions;
TRUNCATE TABLE users;
TRUNCATE TABLE roles;
TRUNCATE TABLE settings;
TRUNCATE TABLE notices;
TRUNCATE TABLE inquiries;

-- ============================================================
-- ROLES
-- ============================================================
INSERT INTO roles (id, name, display_name, description) VALUES
(1, 'admin', 'Administrator', 'Full system access and configuration'),
(2, 'manager', 'Manager', 'Manage operations, approve courses/batches, view reports'),
(3, 'teacher', 'Teacher', 'Teach classes, mark attendance, evaluate assignments'),
(4, 'student', 'Student', 'Enroll in courses, attend classes, take exams'),
(5, 'receptionist', 'Receptionist', 'Student registration, inquiries, admissions'),
(6, 'cashier', 'Cashier', 'Payment collection and receipts');

-- ============================================================
-- PERMISSIONS
-- ============================================================
INSERT INTO permissions (name, display_name, module) VALUES
-- User Management
('users.view', 'View Users', 'users'),
('users.create', 'Create Users', 'users'),
('users.edit', 'Edit Users', 'users'),
('users.delete', 'Delete Users', 'users'),
-- Student Management
('students.view', 'View Students', 'students'),
('students.create', 'Create Students', 'students'),
('students.edit', 'Edit Students', 'students'),
('students.delete', 'Delete Students', 'students'),
-- Teacher Management
('teachers.view', 'View Teachers', 'teachers'),
('teachers.create', 'Create Teachers', 'teachers'),
('teachers.edit', 'Edit Teachers', 'teachers'),
('teachers.delete', 'Delete Teachers', 'teachers'),
-- Staff Management
('staff.view', 'View Staff', 'staff'),
('staff.create', 'Create Staff', 'staff'),
('staff.edit', 'Edit Staff', 'staff'),
('staff.delete', 'Delete Staff', 'staff'),
-- Course Management
('courses.view', 'View Courses', 'courses'),
('courses.create', 'Create Courses', 'courses'),
('courses.edit', 'Edit Courses', 'courses'),
('courses.delete', 'Delete Courses', 'courses'),
('courses.approve', 'Approve Courses', 'courses'),
-- Subject Management
('subjects.view', 'View Subjects', 'subjects'),
('subjects.create', 'Create Subjects', 'subjects'),
('subjects.edit', 'Edit Subjects', 'subjects'),
('subjects.delete', 'Delete Subjects', 'subjects'),
-- Batch Management
('batches.view', 'View Batches', 'batches'),
('batches.create', 'Create Batches', 'batches'),
('batches.edit', 'Edit Batches', 'batches'),
('batches.delete', 'Delete Batches', 'batches'),
('batches.approve', 'Approve Batches', 'batches'),
-- Timetable Management
('timetables.view', 'View Timetables', 'timetables'),
('timetables.create', 'Create Timetables', 'timetables'),
('timetables.edit', 'Edit Timetables', 'timetables'),
-- Assignment Management
('assignments.view', 'View Assignments', 'assignments'),
('assignments.create', 'Create Assignments', 'assignments'),
('assignments.edit', 'Edit Assignments', 'assignments'),
('assignments.delete', 'Delete Assignments', 'assignments'),
('assignments.submit', 'Submit Assignments', 'assignments'),
('assignments.grade', 'Grade Assignments', 'assignments'),
-- Online Classes
('online_classes.view', 'View Online Classes', 'online_classes'),
('online_classes.create', 'Create Online Classes', 'online_classes'),
('online_classes.edit', 'Edit Online Classes', 'online_classes'),
('online_classes.join', 'Join Online Classes', 'online_classes'),
-- Physical Classes
('physical_classes.view', 'View Physical Classes', 'physical_classes'),
('physical_classes.create', 'Create Physical Classes', 'physical_classes'),
-- Attendance
('attendance.view', 'View Attendance', 'attendance'),
('attendance.mark', 'Mark Attendance', 'attendance'),
-- Examinations
('exams.view', 'View Exams', 'exams'),
('exams.create', 'Create Exams', 'exams'),
('exams.edit', 'Edit Exams', 'exams'),
('exams.delete', 'Delete Exams', 'exams'),
('exams.take', 'Take Exams', 'exams'),
('exams.grade', 'Grade Exams', 'exams'),
-- Results
('results.view', 'View Results', 'results'),
('results.publish', 'Publish Results', 'results'),
-- Payments
('payments.view', 'View Payments', 'payments'),
('payments.collect', 'Collect Payments', 'payments'),
('payments.make', 'Make Payments', 'payments'),
-- Notices
('notices.view', 'View Notices', 'notices'),
('notices.create', 'Create Notices', 'notices'),
('notices.delete', 'Delete Notices', 'notices'),
-- Reports
('reports.view', 'View Reports', 'reports'),
('reports.generate', 'Generate Reports', 'reports'),
('reports.export', 'Export Reports', 'reports'),
-- Settings
('settings.view', 'View Settings', 'settings'),
('settings.edit', 'Edit Settings', 'settings'),
-- System
('system.backup', 'Database Backup', 'system'),
('system.restore', 'Database Restore', 'system'),
('system.logs', 'View Activity Logs', 'system');

-- ============================================================
-- ROLE_PERMISSIONS (Admin gets all permissions)
-- ============================================================
INSERT INTO role_permissions (role_id, permission_id)
SELECT 1, id FROM permissions;

-- Manager permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 2, id FROM permissions WHERE module IN (
    'students', 'teachers', 'courses', 'batches', 'timetables',
    'attendance', 'exams', 'payments', 'notices', 'reports'
);

-- Teacher permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 3, id FROM permissions WHERE module IN (
    'timetables', 'assignments', 'online_classes', 'attendance',
    'exams', 'results', 'notices'
);

-- Student permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 4, id FROM permissions WHERE module IN (
    'courses', 'timetables', 'online_classes', 'assignments', 'exams', 'results',
    'attendance', 'payments', 'notices'
);

-- Receptionist permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 5, id FROM permissions WHERE module IN (
    'students', 'courses', 'batches', 'notices', 'reports'
);

-- Cashier permissions
INSERT INTO role_permissions (role_id, permission_id)
SELECT 6, id FROM permissions WHERE module IN (
    'students', 'payments', 'reports'
);

-- ============================================================
-- ADMIN USER (default: admin / admin123)
-- ============================================================
INSERT INTO users (role_id, username, email, password_hash, full_name, phone, status) VALUES
(1, 'admin', 'admin@royaledu.com', '$2y$10$LPPNG9rMUYVZWhEH8SQX..u1YtpedZQBJMcqEKnqeFKxnHIsN6eAi', 'System Administrator', '+94 77 123 4567', 'active');

-- ============================================================
-- MANAGER USER (default: manager / manager123)
-- ============================================================
INSERT INTO users (role_id, username, email, password_hash, full_name, phone, status) VALUES
(2, 'manager', 'manager@royaledu.com', '$2y$10$SvKsZ7Q6CjmTkMZJKIRueeqjo73KJNGwhSTzRvOWDvLi6SMwgnbxa', 'Operations Manager', '+94 77 234 5678', 'active');

-- ============================================================
-- RECEPTIONIST USER (default: reception / reception123)
-- ============================================================
INSERT INTO users (role_id, username, email, password_hash, full_name, phone, status) VALUES
(5, 'reception', 'reception@royaledu.com', '$2y$10$w1C9svHOyq5yktbKNrNr3O/XlrbmfMvMjshC6J/ivyXLDSeJaz7si', 'Front Desk Receptionist', '+94 77 345 6789', 'active');

-- ============================================================
-- CASHIER USER (default: cashier / cashier123)
-- ============================================================
INSERT INTO users (role_id, username, email, password_hash, full_name, phone, status) VALUES
(6, 'cashier', 'cashier@royaledu.com', '$2y$10$x7ZjnDCegCf3KX1L3TqleO4pEopmm9UfrZ6YmBnnMexlUoB1mhv5m', 'Finance Cashier', '+94 77 456 7890', 'active');

-- ============================================================
-- SUBJECTS
-- ============================================================
INSERT INTO subjects (code, name, description) VALUES
('MATH', 'Mathematics', 'Advanced mathematics including calculus, algebra, and statistics'),
('PHYS', 'Physics', 'Classical physics, mechanics, and modern physics concepts'),
('CHEM', 'Chemistry', 'Organic and inorganic chemistry fundamentals'),
('BIO', 'Biology', 'Biological sciences covering molecular to organism level'),
('ENG', 'English Language', 'English language proficiency and literature'),
('SIN', 'Sinhala', 'Sinhala language and literature'),
('TAM', 'Tamil', 'Tamil language and literature'),
('ICT', 'Information Technology', 'Computer science, programming, and digital literacy'),
('ECO', 'Economics', 'Micro and macro economics principles'),
('ACC', 'Accounting', 'Financial and management accounting'),
('BUS', 'Business Studies', 'Business management and entrepreneurship');

-- ============================================================
-- COURSES
-- ============================================================
INSERT INTO courses (code, name, description, course_fee, registration_fee, monthly_fee, duration_months, status, created_by) VALUES
('AL-SCI', 'Advanced Level Science Stream', 'A/L Science stream with Physics, Chemistry, and Combined Mathematics', 50000.00, 5000.00, 8000.00, 24, 'approved', 1),
('AL-COM', 'Advanced Level Commerce Stream', 'A/L Commerce stream with Economics, Accounting, and Business Studies', 45000.00, 5000.00, 7000.00, 24, 'approved', 1),
('AL-ART', 'Advanced Level Arts Stream', 'A/L Arts stream with multiple subject combinations', 35000.00, 5000.00, 6000.00, 24, 'approved', 1),
('OL-REV', 'O/L Revision Class', 'O/L exam preparation and revision for all subjects', 25000.00, 3000.00, 5000.00, 12, 'approved', 1),
('GRA-5', 'Grade 5 Scholarship', 'Grade 5 scholarship examination preparation', 20000.00, 3000.00, 4000.00, 10, 'approved', 1),
('ICT-CERT', 'ICT Certificate Course', 'Basic to intermediate ICT and programming fundamentals', 30000.00, 3000.00, 5000.00, 6, 'approved', 1),
('ENG-BEG', 'English for Beginners', 'English language course for beginners', 15000.00, 2000.00, 3000.00, 6, 'approved', 1);

-- ============================================================
-- TEACHERS
-- ============================================================
INSERT INTO users (role_id, username, email, password_hash, full_name, phone, status) VALUES
(3, 'teacher1', 'kamal@royaledu.com', '$2y$10$W4kJR4aRyV94Cwcnqzs1Zu0IEVnmLP4lVFePFq6dR5FTa5jNigORG', 'Kamal Perera', '+94 71 111 2222', 'active'),
(3, 'teacher2', 'nimali@royaledu.com', '$2y$10$W4kJR4aRyV94Cwcnqzs1Zu0IEVnmLP4lVFePFq6dR5FTa5jNigORG', 'Nimali Fernando', '+94 71 222 3333', 'active'),
(3, 'teacher3', 'sunil@royaledu.com', '$2y$10$W4kJR4aRyV94Cwcnqzs1Zu0IEVnmLP4lVFePFq6dR5FTa5jNigORG', 'Sunil Silva', '+94 71 333 4444', 'active'),
(3, 'teacher4', 'ruwan@royaledu.com', '$2y$10$W4kJR4aRyV94Cwcnqzs1Zu0IEVnmLP4lVFePFq6dR5FTa5jNigORG', 'Ruwan Jayasinghe', '+94 71 444 5555', 'active'),
(3, 'teacher5', 'kumari@royaledu.com', '$2y$10$W4kJR4aRyV94Cwcnqzs1Zu0IEVnmLP4lVFePFq6dR5FTa5jNigORG', 'Kumari Wijesinghe', '+94 71 555 6666', 'active');

INSERT INTO teachers (user_id, teacher_code, first_name, last_name, gender, qualification, specialization, experience_years, phone, monthly_salary, hire_date) VALUES
(5, 'T001', 'Kamal', 'Perera', 'male', 'BSc Physics, MSc', 'Physics', 15, '+94 71 111 2222', 85000.00, '2020-01-15'),
(6, 'T002', 'Nimali', 'Fernando', 'female', 'BSc Chemistry, PhD', 'Chemistry', 12, '+94 71 222 3333', 90000.00, '2020-01-15'),
(7, 'T003', 'Sunil', 'Silva', 'male', 'BSc Mathematics', 'Mathematics', 10, '+94 71 333 4444', 80000.00, '2021-03-01'),
(8, 'T004', 'Ruwan', 'Jayasinghe', 'male', 'BSc IT, MSc', 'Information Technology', 8, '+94 71 444 5555', 95000.00, '2021-06-01'),
(9, 'T005', 'Kumari', 'Wijesinghe', 'female', 'BA English, MA', 'English Language', 14, '+94 71 555 6666', 75000.00, '2020-02-01');

-- ============================================================
-- COURSE_SUBJECTS (assign subjects to courses)
-- ============================================================
INSERT INTO course_subjects (course_id, subject_id, teacher_id) VALUES
-- AL Science
(1, 1, 3), -- Mathematics - Sunil (teachers.id = 3)
(1, 2, 1), -- Physics - Kamal (teachers.id = 1)
(1, 3, 2), -- Chemistry - Nimali (teachers.id = 2)
(1, 4, 2), -- Biology - Nimali (teachers.id = 2)
-- AL Commerce
(2, 9, 1), -- Economics - Kamal (teachers.id = 1)
(2, 10, 1), -- Accounting - Kamal (teachers.id = 1)
(2, 11, 1), -- Business - Kamal (teachers.id = 1)
-- AL Arts
(3, 5, 5), -- English - Kumari (teachers.id = 5)
(3, 6, 5), -- Sinhala - Kumari (teachers.id = 5)
-- ICT Course
(6, 8, 4), -- ICT - Ruwan (teachers.id = 4)
-- English Course
(7, 5, 5); -- English - Kumari (teachers.id = 5)

-- ============================================================
-- BATCHES
-- ============================================================
INSERT INTO batches (course_id, batch_name, start_date, end_date, max_students, status) VALUES
(1, 'AL Science 2024', '2024-01-15', '2025-12-31', 40, 'active'),
(1, 'AL Science 2025', '2025-01-15', '2026-12-31', 40, 'active'),
(2, 'AL Commerce 2024', '2024-01-15', '2025-12-31', 50, 'active'),
(2, 'AL Commerce 2025', '2025-01-15', '2026-12-31', 50, 'active'),
(3, 'AL Arts 2024', '2024-01-15', '2025-12-31', 60, 'active'),
(4, 'O/L Revision 2024', '2024-05-01', '2024-12-31', 80, 'active'),
(5, 'Grade 5 2024', '2024-01-15', '2024-10-31', 30, 'active'),
(6, 'ICT Batch Jan-2024', '2024-01-10', '2024-06-30', 25, 'active'),
(7, 'English Beginner Jan-2024', '2024-01-10', '2024-06-30', 20, 'active');

-- ============================================================
-- STUDENTS
-- ============================================================
INSERT INTO users (role_id, username, email, password_hash, full_name, phone, status) VALUES
(4, 'student1', 'amal@royaledu.com', '$2y$10$PPvQ81.9JtWLAztWdouq8OLTkybrj/2X0ualxeM/wtPyGqO2VWOBy', 'Amal Wijesinghe', '+94 77 666 7777', 'active'),
(4, 'student2', 'nadeesha@royaledu.com', '$2y$10$PPvQ81.9JtWLAztWdouq8OLTkybrj/2X0ualxeM/wtPyGqO2VWOBy', 'Nadeesha Karunaratne', '+94 77 777 8888', 'active'),
(4, 'student3', 'kasun@royaledu.com', '$2y$10$PPvQ81.9JtWLAztWdouq8OLTkybrj/2X0ualxeM/wtPyGqO2VWOBy', 'Kasun Bandara', '+94 77 888 9999', 'active'),
(4, 'student4', 'tharindu@royaledu.com', '$2y$10$PPvQ81.9JtWLAztWdouq8OLTkybrj/2X0ualxeM/wtPyGqO2VWOBy', 'Tharindu Ratnayake', '+94 77 999 0000', 'active'),
(4, 'student5', 'isuru@royaledu.com', '$2y$10$PPvQ81.9JtWLAztWdouq8OLTkybrj/2X0ualxeM/wtPyGqO2VWOBy', 'Isuru Perera', '+94 77 000 1111', 'active');

INSERT INTO students (user_id, student_code, first_name, last_name, gender, date_of_birth, address, city, guardian_name, guardian_phone, guardian_email, school, education_level, emergency_contact, blood_group) VALUES
(10, 'S0001', 'Amal', 'Wijesinghe', 'male', '2006-05-15', '123 Temple Road, Colombo', 'Colombo', 'Sumith Wijesinghe', '+94 77 111 0000', 'sumith@gmail.com', 'Royal College', 'A/L', '+94 77 111 0000', 'A+'),
(11, 'S0002', 'Nadeesha', 'Karunaratne', 'female', '2006-08-22', '45 Lake Drive, Kandy', 'Kandy', 'Karunaratne P', '+94 77 222 0000', 'karunaratne@gmail.com', 'Girls High School', 'A/L', '+94 77 222 0000', 'B+'),
(12, 'S0003', 'Kasun', 'Bandara', 'male', '2006-03-10', '78 Hill Street, Gampaha', 'Gampaha', 'Sunil Bandara', '+94 77 333 0000', 'sunilb@gmail.com', 'Lyceum International', 'A/L', '+94 77 333 0000', 'O+'),
(13, 'S0004', 'Tharindu', 'Ratnayake', 'male', '2006-11-30', '90 Main Road, Kurunegala', 'Kurunegala', 'Ratnayake K', '+94 77 444 0000', 'ratnayake@gmail.com', 'Maliyadeva College', 'A/L', '+94 77 444 0000', 'AB+'),
(14, 'S0005', 'Isuru', 'Perera', 'male', '2006-07-18', '22 Beach Road, Negombo', 'Negombo', 'Perera S', '+94 77 555 0000', 'perera@gmail.com', 'St Josephs College', 'A/L', '+94 77 555 0000', 'A-');

-- ============================================================
-- ENROLLMENTS
-- ============================================================
INSERT INTO enrollments (student_id, batch_id, enrolled_date, status) VALUES
(1, 1, '2024-01-10', 'active'), -- Amal - AL Science 2024
(2, 1, '2024-01-12', 'active'), -- Nadeesha - AL Science 2024
(3, 2, '2025-01-08', 'active'), -- Kasun - AL Science 2025
(4, 3, '2024-01-14', 'active'), -- Tharindu - AL Commerce 2024
(5, 3, '2024-01-15', 'active'); -- Isuru - AL Commerce 2024

-- ============================================================
-- TIMETABLE SLOTS (sample)
-- ============================================================
INSERT INTO timetable_slots (batch_id, subject_id, teacher_id, day_of_week, start_time, end_time, room) VALUES
(1, 1, 3, 'monday', '14:00:00', '16:00:00', 'Room A1'), -- Math - Sunil (teachers.id = 3)
(1, 2, 1, 'tuesday', '14:00:00', '16:00:00', 'Room A1'), -- Physics - Kamal (teachers.id = 1)
(1, 3, 2, 'wednesday', '14:00:00', '16:00:00', 'Room A2'), -- Chemistry - Nimali (teachers.id = 2)
(3, 9, 1, 'monday', '09:00:00', '11:00:00', 'Room B1'), -- Economics - Kamal (teachers.id = 1)
(3, 10, 1, 'wednesday', '09:00:00', '11:00:00', 'Room B1'); -- Accounting - Kamal (teachers.id = 1)

-- ============================================================
-- ONLINE CLASSES (sample)
-- ============================================================
INSERT INTO online_classes (batch_id, subject_id, teacher_id, title, description, class_date, start_time, end_time, meeting_link, status) VALUES
(1, 1, 3, 'Mathematics - Integration', 'Advanced integration techniques', '2024-07-15', '18:00:00', '19:30:00', 'https://meet.google.com/abc-defg-hij', 'completed'),
(1, 2, 1, 'Physics - Newtons Laws', 'Fundamentals of motion', '2024-07-16', '18:00:00', '19:30:00', 'https://meet.google.com/xyz-uvwx-yz', 'completed'),
(3, 9, 1, 'Economics - Supply & Demand', 'Market equilibrium concepts', '2024-07-15', '10:00:00', '11:30:00', 'https://meet.google.com/pqr-stuv-wx', 'completed');

-- ============================================================
-- ASSIGNMENTS (sample)
-- ============================================================
INSERT INTO assignments (batch_id, subject_id, teacher_id, title, description, max_marks, due_date) VALUES
(1, 1, 3, 'Integration Problem Set', 'Complete all integration problems from Chapter 5', 50, '2024-07-20 23:59:59'),
(1, 2, 1, 'Physics Lab Report', 'Submit lab report on motion experiments', 100, '2024-07-22 23:59:59'),
(3, 9, 1, 'Economics Case Study', 'Analyze market demand for a product', 75, '2024-07-21 23:59:59');

-- ============================================================
-- EXAMS (sample)
-- ============================================================
INSERT INTO exams (batch_id, subject_id, teacher_id, title, description, exam_date, duration_minutes, total_marks, passing_marks, status, is_online) VALUES
(1, 1, 3, 'Mathematics Mid-Term', 'First term examination', '2024-07-25 14:00:00', 120, 100, 40, 'published', TRUE),
(1, 2, 1, 'Physics Mid-Term', 'First term examination', '2024-07-26 14:00:00', 120, 100, 40, 'published', TRUE),
(3, 9, 1, 'Economics Mid-Term', 'First term examination', '2024-07-27 09:00:00', 90, 75, 30, 'published', TRUE);

-- ============================================================
-- EXAM QUESTIONS (sample for Math exam)
-- ============================================================
INSERT INTO exam_questions (exam_id, question_text, question_type, option_a, option_b, option_c, option_d, correct_answer, marks, display_order) VALUES
(1, 'What is the integral of 2x?', 'mcq', 'x²', '2x²', 'x² + C', '2x² + C', 'C', 2, 1),
(1, 'The derivative of sin(x) is:', 'mcq', 'cos(x)', '-cos(x)', 'sin(x)', '-sin(x)', 'A', 2, 2),
(1, 'Integration is the reverse process of differentiation.', 'true_false', 'True', 'False', NULL, NULL, 'A', 2, 3),
(1, '∫(3x² + 2x) dx = ?', 'mcq', 'x³ + x²', 'x³ + x² + C', '3x³ + x²', '3x³ + x² + C', 'B', 3, 4),
(1, 'Explain the Fundamental Theorem of Calculus.', 'essay', NULL, NULL, NULL, NULL, 'manual', 5, 5);

-- ============================================================
-- PAYMENTS (sample)
-- ============================================================
INSERT INTO payments (student_id, enrollment_id, payment_type, amount, discount, final_amount, payment_method, payment_date, payment_month, status, collected_by) VALUES
(1, 1, 'registration', 5000.00, 0.00, 5000.00, 'cash', '2024-01-10', NULL, 'completed', 6),
(1, 1, 'course_fee', 50000.00, 0.00, 50000.00, 'cash', '2024-01-10', NULL, 'completed', 6),
(1, 1, 'monthly_fee', 8000.00, 1000.00, 7000.00, 'cash', '2024-02-01', '2024-02', 'completed', 6),
(1, 1, 'monthly_fee', 8000.00, 0.00, 8000.00, 'cash', '2024-03-01', '2024-03', 'completed', 6),
(2, 2, 'registration', 5000.00, 0.00, 5000.00, 'cash', '2024-01-12', NULL, 'completed', 6),
(2, 2, 'course_fee', 50000.00, 2000.00, 48000.00, 'bank_transfer', '2024-01-12', NULL, 'completed', 6),
(4, 4, 'registration', 5000.00, 0.00, 5000.00, 'cash', '2024-01-14', NULL, 'completed', 6),
(4, 4, 'course_fee', 45000.00, 0.00, 45000.00, 'online', '2024-01-14', NULL, 'completed', 6);

-- ============================================================
-- RECEIPTS (sample)
-- ============================================================
INSERT INTO receipts (receipt_no, payment_id, student_id, amount, issued_by) VALUES
('RCP-2024-00001', 1, 1, 5000.00, 6),
('RCP-2024-00002', 2, 1, 50000.00, 6),
('RCP-2024-00003', 3, 1, 7000.00, 6),
('RCP-2024-00004', 4, 1, 8000.00, 6),
('RCP-2024-00005', 5, 2, 5000.00, 6),
('RCP-2024-00006', 6, 2, 48000.00, 6),
('RCP-2024-00007', 7, 4, 5000.00, 6),
('RCP-2024-00008', 8, 4, 45000.00, 6);

-- ============================================================
-- NOTICES (sample)
-- ============================================================
INSERT INTO notices (title, content, target_role, target_batch_id, posted_by, is_pinned) VALUES
('Welcome to Royal Education Center', 'We are pleased to welcome all new students to our institution. Please check your timetables and attend classes regularly.', 'all', NULL, 1, TRUE),
('Mid-Term Examination Schedule', 'Mid-term examinations will begin on July 25th. Please check the exam schedule on your dashboard.', 'student', NULL, 1, TRUE),
('Holiday Notice', 'The center will be closed on July 15th for a public holiday.', 'all', NULL, 2, FALSE),
('Physics Extra Class', 'An extra physics revision class will be conducted on Saturday at 2 PM for AL Science batch.', 'student', 1, 5, FALSE);

-- ============================================================
-- SETTINGS (default system settings)
-- ============================================================
INSERT INTO settings (setting_key, setting_value, setting_group) VALUES
('site_name', 'Royal Education Center', 'general'),
('site_address', '123 Education Street, Colombo, Sri Lanka', 'general'),
('site_phone', '+94 11 234 5678', 'general'),
('site_email', 'info@royaledu.com', 'general'),
('currency', 'LKR', 'general'),
('max_login_attempts', '5', 'security'),
('session_timeout', '1800', 'security'),
('receipt_prefix', 'RCP-', 'finance'),
('student_code_prefix', 'S', 'student'),
('teacher_code_prefix', 'T', 'teacher'),
('staff_code_prefix', 'ST', 'staff'),
('batch_code_prefix', 'B', 'batch');

-- ============================================================
-- ACTIVITY LOGS (sample)
-- ============================================================
INSERT INTO activity_logs (user_id, action, module, description, ip_address) VALUES
(1, 'login', 'auth', 'Admin logged in', '127.0.0.1'),
(1, 'create', 'users', 'Created user: manager', '127.0.0.1'),
(1, 'create', 'users', 'Created user: reception', '127.0.0.1'),
(1, 'create', 'users', 'Created user: cashier', '127.0.0.1'),
(5, 'create', 'students', 'Created student: Amal Wijesinghe', '127.0.0.1'),
(5, 'create', 'students', 'Created student: Nadeesha Karunaratne', '127.0.0.1'),
(6, 'create', 'payments', 'Recorded payment for student S0001', '127.0.0.1'),
(2, 'create', 'notices', 'Posted notice: Holiday Notice', '127.0.0.1');

-- ============================================================
-- ATTENDANCE (sample)
-- ============================================================
INSERT INTO attendance (enrollment_id, timetable_slot_id, date, status, marked_by) VALUES
(1, 1, '2024-07-01', 'present', 7),
(1, 2, '2024-07-02', 'present', 5),
(1, 3, '2024-07-03', 'late', 6),
(2, 1, '2024-07-01', 'present', 7),
(2, 2, '2024-07-02', 'absent', 5);

-- ============================================================
-- STUDY MATERIALS (sample)
-- ============================================================
INSERT INTO study_materials (batch_id, subject_id, teacher_id, title, description, material_type, file_path) VALUES
(1, 1, 7, 'Integration Notes Chapter 5', 'Complete notes on integration techniques', 'note', 'uploads/notes/math_integration_ch5.pdf'),
(1, 2, 5, 'Physics Motion Problems', 'Practice problems on Newtons laws', 'note', 'uploads/notes/physics_motion.pdf'),
(1, 2, 5, 'Physics Lab Tutorial Video', 'Video tutorial for physics lab experiments', 'video', 'uploads/videos/physics_lab.mp4');

-- ============================================================
-- INQUIRIES (sample)
-- ============================================================
INSERT INTO inquiries (student_id, name, email, phone, subject, message, status) VALUES
(NULL, 'Saman Kumara', 'saman@gmail.com', '+94 77 111 2222', 'Course Inquiry', 'I would like to know about the AL Science course fees and schedule.', 'new'),
(NULL, 'Kumari Perera', 'kumari@gmail.com', '+94 77 333 4444', 'Registration', 'How can I register my child for the Grade 5 scholarship class?', 'new'),
(3, 'Kasun Bandara', 'kasun@royaledu.com', '+94 77 888 9999', 'Class Schedule', 'Is there an extra class for mathematics this Saturday?', 'responded');

SET FOREIGN_KEY_CHECKS = 1;
