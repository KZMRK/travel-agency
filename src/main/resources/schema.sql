DROP TABLE IF EXISTS tour_client;
DROP TABLE IF EXISTS guide;
DROP TABLE IF EXISTS tour_selling_price;
DROP TABLE IF EXISTS tour;
DROP TABLE IF EXISTS country;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS _user;

CREATE TABLE IF NOT EXISTS "country" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS "_user" (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS "tour" (
    id BIGSERIAL PRIMARY KEY,
    departure_id INTEGER REFERENCES country(id) NOT NULL,
    destination_id INTEGER REFERENCES country(id) NOT NULL,
    departure_at DATE NOT NULL,
    return_at DATE NOT NULL,
    initial_price DOUBLE PRECISION NOT NULL,
    guide_id BIGINT NOT NULL
);

CREATE TABLE IF NOT EXISTS "guide" (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "client" (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    passport_number VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS "tour_selling_price" (
    tour_id BIGINT REFERENCES tour(id),
    client_id BIGINT REFERENCES client(id),
    selling_price DOUBLE PRECISION NOT NULL,
    PRIMARY KEY (tour_id, client_id)
);