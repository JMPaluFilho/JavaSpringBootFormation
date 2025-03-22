alter table patients add column is_active tinyint;

update patients set is_active = 1;

alter table patients modify is_active tinyint not null;