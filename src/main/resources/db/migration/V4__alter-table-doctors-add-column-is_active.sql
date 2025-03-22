alter table doctors add is_active tinyint;

update doctors set is_active = 1;

alter table doctors modify is_active tinyint not null;