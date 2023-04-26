alter table if exists training
    add constraint FK7r3b25ygw5bdjamojskmpk0b9
        foreign key (trainer_id)
            references trainer
;

alter table if exists training_student
    add constraint FKg30d1hddnmwj7qbu7iubguwlg
        foreign key (student_id)
            references student
;

alter table if exists training_student
    add constraint FKo8um3a5gjdhvsnlg3ymrchpwe
        foreign key (training_id)
            references training
;