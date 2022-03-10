INSERT INTO punch_list.pendency
    (id_contract, area_identification, tag, description, priority, discipline, status,
     registered_by, registered_to, expected_in) VALUES
    (1, 'GAS TURBINE', '11GT', 'Touch-up paint on the east side of the enclosure.', 'B', 'Painting', 'OPEN',
     2, 3, (now() + interval 4 day)),
    (1, 'STEAM TURBINE', '10ST', 'Replace leaking safety valve.', 'A', 'Piping', 'OPEN',
     1, 2, (now() + interval 2 day)),
    (1, 'GAS TURBINE', '12GT', 'Oil leakage from the drain pump.', 'B', 'Mechanical', 'OPEN',
     1, 3, (now() + interval 8 day));