INSERT INTO achievement (name, description, type, target_count) VALUES
('Hot Streak', 'Log in 3 day in row', 'LOGIN_STREAK', 3),
('Hottest Streak', 'Log in 10 day in row', 'LOGIN_STREAK', 10)
ON CONFLICT (name) DO NOTHING;
