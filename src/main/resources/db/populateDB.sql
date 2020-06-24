DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq_meal RESTART WITH 1;
ALTER SEQUENCE global_seq RESTART WITH 100000;


INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (id_user, date_time, description, calories) VALUES
    (100000, '25.11.2020 10:33:00', 'Lunch', 600),
    (100000, '21.12.2020 10:38:00', 'Lunch', 500),
    (100000, '22.12.2020 11:00:00', 'Breakfast', 600),
    (100000, '26.11.2020 12:10:00', 'Fastfood', 900),
    (100001, '21.5.2020 11:15:00', 'Random food event', 1200);
