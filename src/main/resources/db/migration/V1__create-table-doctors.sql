create table doctors (
    id bigint not null auto_increment,
    name varchar(100) not null,
    mail varchar(100) not null unique,
    crm varchar(6) not null unique,
    specialty varchar(100) not null,
    address_line_one varchar(100) not null,
    address_line_two varchar(100),
    zipCode varchar(9) not null,
    city varchar(100) not null,
    state varchar(2) not null,

    primary key (id)
);