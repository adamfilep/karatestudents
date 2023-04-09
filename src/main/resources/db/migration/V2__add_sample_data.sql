
insert into student (date_of_birth, is_student, name, std_rank, started_karate)
values ('1998-04-05', true, 'Bruce Lee', 'WHITE_BELT', '2010-10-05');

insert into student (date_of_birth, is_student, name, std_rank, started_karate)
values ( '1960-11-21', true, 'IP Man', 'BLUE_BELT', '1990-05-20');

insert into student (date_of_birth, is_student, name, std_rank, started_karate)
values ( '2010-12-11', false, 'Scott Adkins', 'FIRST_DAN', '2017-02-01');

insert into student (date_of_birth, is_student, name, std_rank, started_karate)
values ( '1930-07-10', true, 'Dexter Constable', 'GREEN_BELT', '1950-01-16');

insert into student (date_of_birth, is_student, name, std_rank, started_karate)
values ( '1997-05-19', false, 'Andrina Haight', 'YELLOW_BELT', '2005-08-10');

insert into student (date_of_birth, is_student, name, std_rank, started_karate)
values ( '2015-03-22', true, 'Nat Waller', 'WHITE_BELT', '2021-05-05');

insert into trainer (name, trn_rank)
values ('Neli Starr', 'SECOND_DAN');

insert into trainer (name, trn_rank)
values ('Ethan Lindon', 'THIRD_DAN');

insert into trainer (name, trn_rank)
values ('Yuzuru Cantrell', 'THIRD_DAN');

insert into training (name, starts_at, ends_at, location, training_day, trainer_id)
values ('Kumite training', '18:00:00', '19:00:00', 'Central dojo', 'MONDAY', 1);

insert into training (name, starts_at, ends_at, location, training_day, trainer_id)
values ('Kata training', '18:00:00', '19:00:00', 'BP - 1', 'WEDNESDAY', 2);

insert into training (name, starts_at, ends_at, location, training_day, trainer_id)
values ('Mixed training', '18:00:00', '19:00:00', 'BP - 2', 'FRIDAY', 3);

insert into training_student(training_id, student_id) values (1, 1);
insert into training_student(training_id, student_id) values (2, 1);
insert into training_student(training_id, student_id) values (3, 1);
insert into training_student(training_id, student_id) values (1, 2);
insert into training_student(training_id, student_id) values (3, 2);
insert into training_student(training_id, student_id)values (1, 3);
insert into training_student(training_id, student_id)values (2, 3);
insert into training_student(training_id, student_id) values (1, 4);
insert into training_student(training_id, student_id) values (2, 4);
insert into training_student(training_id, student_id) values (3, 4);
insert into training_student(training_id, student_id) values (3, 5);
insert into training_student(training_id, student_id) values (3, 6);