--liquibase formatted sql

--changeset punch-list:0100-demo-data context:demo
INSERT INTO punch_list.contract (number_contract, description, address, start_at)
VALUES ('BR2022-001', 'Thermoelectric Power Plant Generation Brazil', 'Manaus-AM-BR', LOCALTIMESTAMP + INTERVAL '1' DAY);

INSERT INTO punch_list.responsible_person (id_contract, name, occupation, department)
VALUES (1, 'Bruno Machado', 'Software engineer', 'TI'),
       (1, 'William Souza', 'Electrical engineer', 'C&M'),
       (1, 'Carlos Moraes', 'Equipment Technician', 'Maintenance');

INSERT INTO punch_list.pendency (id_contract, area_identification, tag, description, priority, discipline, status,
                                 registered_by, registered_to, expected_in)
VALUES (1, 'GAS TURBINE', '11GT', 'Touch-up paint on the east side of the enclosure.', 'B', 'Painting', 'OPEN',
        2, 3, LOCALTIMESTAMP + INTERVAL '4' DAY),
       (1, 'STEAM TURBINE', '10ST', 'Replace leaking safety valve.', 'A', 'Piping', 'OPEN',
        1, 2, LOCALTIMESTAMP + INTERVAL '2' DAY),
       (1, 'GAS TURBINE', '12GT', 'Oil leakage from the drain pump.', 'B', 'Mechanical', 'OPEN',
        1, 3, LOCALTIMESTAMP + INTERVAL '8' DAY);
--rollback DELETE FROM punch_list.pendency; DELETE FROM punch_list.responsible_person; DELETE FROM punch_list.contract;
