INSERT INTO achievement (name, description, type, target_count, experience) VALUES
('Hot Streak', 'Log in 3 day in row', 'LOGIN_STREAK', 3, 100),
('Hottest Streak', 'Log in 10 day in row', 'LOGIN_STREAK', 10, 200)
ON CONFLICT (name) DO NOTHING;
