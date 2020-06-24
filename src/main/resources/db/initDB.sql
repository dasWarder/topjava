DROP TABLE IF EXISTS meals;
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS global_seq;
DROP SEQUENCE IF EXISTS global_seq_meal;


CREATE SEQUENCE global_seq START WITH 100000;

CREATE TABLE users
(
  id               INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name             VARCHAR                 NOT NULL,
  email            VARCHAR                 NOT NULL,
  password         VARCHAR                 NOT NULL,
  registered       TIMESTAMP DEFAULT now() NOT NULL,
  enabled          BOOL DEFAULT TRUE       NOT NULL,
  calories_per_day INTEGER DEFAULT 2000    NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR,
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE SEQUENCE global_seq_meal START WITH 1;

CREATE TABLE meals
(
    id              INTEGER PRIMARY KEY DEFAULT nextval('global_seq_meal'),
    id_user         INTEGER     NOT NULL,
    date_time       TIMESTAMP WITH TIME ZONE DEFAULT now() NOT NULL,
    description     VARCHAR     NOT NULL,
    calories        INTEGER     NOT NULL,

    FOREIGN KEY (id_user) REFERENCES users (id) ON DELETE CASCADE
);
CREATE UNIQUE INDEX date_time_uniq_inx ON meals(id_user, date_time);