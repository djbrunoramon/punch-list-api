CREATE TABLE punch_list.responsible_person (
    id bigserial not null,
    name text not null,
    occupation text not null,
    department text not null,
    created_at datetime not null default CURRENT_TIMESTAMP,
    active boolean not null default true,
    inactive_in datetime null,
    contract text not null,
    constraint responsible_person_pk primary key (id)
);