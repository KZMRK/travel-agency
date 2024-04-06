INSERT INTO "country" (name) VALUES ('Ukraine');
INSERT INTO "country" (name) VALUES ('Germany');
INSERT INTO "_user" (email, password, role) VALUES ('dima.kazmiruk.05@gmail.com', '$2a$12$vcZgtFBRaE1MXICd1JjukO1Vbg.9KMe18iwcEMiRMrUKcNvD2ZFUe', 'ROLE_ADMIN');
INSERT INTO "guide" (first_name, last_name) VALUES ('Dmytro', 'Kazmiruk');
INSERT INTO "tour" (departure_id, destination_id, departure_at, return_at, initial_price, guide_id) VALUES (1, 2, '2024-03-28', '2024-04-01', 1000, 1);
INSERT INTO "client" (first_name, last_name, passport_number) VALUES ('Anna', 'Skakun', '123456789');