INSERT INTO achievement (name, description, type, target_count, experience) VALUES
('Hot Streak', 'Log in 3 day in row', 'LOGIN_STREAK', 1, 30),
('Still Here!', 'Log in 7 day in row', 'LOGIN_STREAK', 7, 100),
('First Steps', 'Complete your first lesson', 'COMPLETE_LESSONS', 1, 30),
('Learner', 'Complete 5 lessons', 'COMPLETE_LESSONS', 5, 30),
('Brain Builder', 'Complete 20 lessons', 'COMPLETE_LESSONS', 20, 150),
('3 Steps Closer', 'Complete 3 quizes', 'COMPLETE_QUIZES', 3, 40),
('Quiz Expert', 'Complete 10 quizes', 'COMPLETE_QUIZES', 10, 130)
ON CONFLICT (name) DO NOTHING;


INSERT INTO title (required_exp, name) VALUES
(1, 'Curious Paltus'),
(3, 'Junior Paltus'),
(5, 'Turbo Paltus'),
(7, 'Grand Paltus')
ON CONFLICT (required_exp) DO NOTHING;
