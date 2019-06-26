DELETE from meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (datetime, description, calories, user_id) VALUES
  ('2019-05-30 10:00:00.0', 'Breakfast', 500, 100000),
  ('2019-05-30 13:00:00.0', 'Dinner', 1000, 100000),
  ('2019-05-30 20:00:00.0', 'Supper', 500, 100001);