INSERT INTO users (email, password, role) VALUES ('dima.kazmiruk.05@gmail.com', '$2a$12$d49lnBg6AYKQyyAgS0e5zOnA69dd/UrzpoNjL3W3ols382kWqT7Eu', 'ROLE_ADMIN');
INSERT INTO countries (name) VALUES
                    ('Ukraine'),
                    ('Poland'),
                    ('Great Britain'),
                    ('France'),
                    ('Italy'),
                    ('Sweden'),
                    ('Netherlands');
INSERT INTO clients (first_name, last_name, passport_number) VALUES
                    ('Imogene', 'Crane', '574593475'),
                    ('Winford', 'Lara', '654934567'),
                    ('Marta', 'Fletcher', '879458642'),
                    ('Della', 'Combs', '903457423'),
                    ('Ivory', 'Moss', '248557227');
INSERT INTO guides (first_name, last_name) VALUES
                    ('Tamara ', 'Donovan'),
                    ('Cecelia', 'Palmer'),
                    ('Arnulfo', 'Barr'),
                    ('Gayle', 'Miller');

-- most popular destination in 2024 - France (id - 4)
-- most popular destination in 2025 - Italy (id - 5)
INSERT INTO tours(departure_id, destination_id, departure_at, return_at, initial_price, guide_id) VALUES
                    (1, 2, '2024-04-20', '2024-04-24', 1000.0, 1),
                    (2, 4, '2024-06-11', '2024-06-16', 2445.4, 2),
                    (7, 4, '2024-09-01', '2024-09-13', 4222.1, 1),
                    (3, 5, '2025-01-01', '2026-01-05', 5224.45, 4),
                    (7, 5, '2025-03-04', '2025-03-07', 3565, 2),
                    (2, 1, '2025-01-05', '2025-01-07', 1535, 2);

-- Client get the highest discount - Marta Fletcher
-- Client without tour in 2024 - Della Combs, Ivory Moss
-- Client with highest discount - Marta Fletcher
-- Client generated the highest revenue - Imogene Crane
INSERT INTO client_tours(tour_id, client_id, selling_price) VALUES
                    (1, 1, 845),
                    (2, 2, 2100),
                    (3, 3, 3500),
                    (4, 4, 5255.234),
                    (4, 1, 5000.144),
                    (6, 2, 1300);

