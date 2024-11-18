CREATE TABLE products (
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(255) NOT NULL,
    fulfillment_center VARCHAR(255) NOT NULL,
    quantity INTEGER NOT NULL,
    price NUMERIC(20, 2) NOT NULL
);