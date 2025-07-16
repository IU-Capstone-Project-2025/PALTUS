INSERT INTO achievement (name, description, type, target_count, experience) VALUES
('Hot Streak', 'Log in 3 day in row', 'LOGIN_STREAK', 1, 100),
('Hottest Streak', 'Log in 10 day in row', 'LOGIN_STREAK', 2, 200)
ON CONFLICT (name) DO NOTHING;


INSERT INTO title (required_exp, name) VALUES
(1, 'LOH'),
(3, 'Paltus man')
ON CONFLICT (required_exp) DO NOTHING;
