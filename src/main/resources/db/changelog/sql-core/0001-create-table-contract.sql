CREATE TABLE punch_list.contract (
    id bigserial not null,
    number_contract text not null,
    description text not null,
    address text not null,
    created_at datetime not null default CURRENT_TIMESTAMP,
    start_at datetime,
    scheduled_to datetime,
    active boolean not null default true,
    inactive_at datetime,
    estimated_at decimal(15,2),
    constraint contract_pk primary key (id)
);