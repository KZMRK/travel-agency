DROP TABLE IF EXISTS client_tours;
DROP TABLE IF EXISTS tours;
DROP TABLE IF EXISTS guides;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS users;

CREATE TABLE IF NOT EXISTS "countries" (
    id SERIAL PRIMARY KEY,
    name VARCHAR(80) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS "users" (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS "guides" (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "tours" (
    id BIGSERIAL PRIMARY KEY,
    departure_id INTEGER REFERENCES countries(id) NOT NULL,
    destination_id INTEGER REFERENCES countries(id) NOT NULL,
    departure_at DATE NOT NULL,
    return_at DATE NOT NULL,
    initial_price DECIMAL NOT NULL,
    guide_id BIGINT REFERENCES guides(id) NOT NULL
);

CREATE TABLE IF NOT EXISTS "clients" (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    passport_number CHAR(9) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS "client_tours" (
    id BIGSERIAL PRIMARY KEY,
    tour_id BIGINT REFERENCES tours(id),
    client_id BIGINT REFERENCES clients(id),
    selling_price DECIMAL NOT NULL

);