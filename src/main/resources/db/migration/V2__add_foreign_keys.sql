ALTER TABLE IF EXISTS training
    ADD CONSTRAINT fk_training_trainerId
        FOREIGN KEY (trainer_id)
            REFERENCES trainer;

ALTER TABLE IF EXISTS training_student
    ADD CONSTRAINT fk_training_student_studentId
        FOREIGN KEY (student_id)
            REFERENCES student;

ALTER TABLE IF EXISTS training_student
    ADD CONSTRAINT fk_training_student_trainerId
        FOREIGN KEY (training_id)
            REFERENCES training;