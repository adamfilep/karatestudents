CREATE TABLE IF NOT EXISTS student (
       id             SERIAL PRIMARY KEY,
       date_of_birth  DATE,
       is_student     BOOLEAN NOT NULL,
       name           VARCHAR(255),
       std_rank       VARCHAR(255),
       started_karate DATE
);

CREATE TABLE IF NOT EXISTS trainer (
       id       SERIAL PRIMARY KEY,
       name     VARCHAR(255),
       trn_rank VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS training (
        id           SERIAL PRIMARY KEY,
        name         VARCHAR(255),
        starts_at    TIME,
        ends_at      TIME,
        location     VARCHAR(255),
        training_day VARCHAR(255),
        trainer_id   BIGINT
);

CREATE TABLE IF NOT EXISTS training_student (
        training_id BIGINT NOT NULL,
        student_id  BIGINT NOT NULL
);