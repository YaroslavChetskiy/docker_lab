--liquibase formatted sql

-- customer

--changeset chetskiy:create-table-customer
CREATE TABLE IF NOT EXISTS customer
(
    id           BIGSERIAL PRIMARY KEY,
    email        VARCHAR(254) NOT NULL UNIQUE,
    full_name    VARCHAR(255),
    birth_date   DATE,
    address      VARCHAR(255),
    phone_number VARCHAR(20)  NOT NULL
);

-- series
--changeset chetskiy:create-table-series
CREATE TABLE IF NOT EXISTS series
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


-- publisher
--changeset chetskiy:create-table-publisher
CREATE TABLE IF NOT EXISTS publisher
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- author
--changeset chetskiy:create-table-author
CREATE TABLE IF NOT EXISTS author
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- magazine
--changeset chetskiy:create-table-magazine
CREATE TABLE IF NOT EXISTS magazine
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(255) NOT NULL,
    isbn             VARCHAR(13)  NOT NULL UNIQUE,
    series_id        INT          NOT NULL REFERENCES series (id),
    publisher_id     INT          NOT NULL REFERENCES publisher (id),
    publication_year INT,
    price DECIMAL(10, 2) NOT NULL
);

--changeset chetskiy:create-table-magazine-author
CREATE TABLE IF NOT EXISTS magazine_author
(
    id          SERIAL PRIMARY KEY,
    magazine_id INT NOT NULL REFERENCES magazine (id) ON DELETE CASCADE,
    author_id   INT NOT NULL REFERENCES author (id) ON DELETE CASCADE,
    UNIQUE (magazine_id, author_id)
);

-- store
--changeset chetskiy:create-table-store
CREATE TABLE IF NOT EXISTS store
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    address VARCHAR(255) NOT NULL
);


-- department
--changeset chetskiy:create-table-department
CREATE TABLE IF NOT EXISTS department
(
    id       SERIAL PRIMARY KEY,
    number   INT NOT NULL,
    name     VARCHAR(255),
    store_id INT NOT NULL REFERENCES store (id) ON DELETE CASCADE,
    capacity INT,
    UNIQUE (store_id, number)
);

-- inventory
--changeset chetskiy:create-table-inventory
CREATE TABLE IF NOT EXISTS inventory
(
    id            SERIAL PRIMARY KEY,
    department_id INT NOT NULL REFERENCES department (id) ON DELETE CASCADE,
    magazine_id   INT NOT NULL REFERENCES magazine (id) ON DELETE RESTRICT,
    quantity      INT NOT NULL,
    UNIQUE (department_id, magazine_id)
);


-- order
--changeset chetskiy:create-table-orders
CREATE TABLE IF NOT EXISTS orders
(
    id          SERIAL PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES customer (id) ON DELETE RESTRICT,
    store_id    INT NOT NULL REFERENCES store (id) ON DELETE RESTRICT,
    order_date  DATE,
    status VARCHAR(20) NOT NULL
);

-- order item
--changeset chetskiy:create-table-order-item
CREATE TABLE IF NOT EXISTS order_item
(
    id          SERIAL PRIMARY KEY,
    order_id    INT NOT NULL REFERENCES orders (id) ON DELETE CASCADE,
    magazine_id INT NOT NULL REFERENCES magazine (id) ON DELETE RESTRICT,
    quantity    INT NOT NULL,
    UNIQUE (order_id, magazine_id)
);
