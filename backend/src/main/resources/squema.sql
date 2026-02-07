CREATE TABLE product_category (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(40) NOT NULL,
    inactive BOOLEAN DEFAULT FALSE
);

CREATE TABLE product (
    id IDENTITY PRIMARY KEY,
    fk_category BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL,
    description VARCHAR(255),
    price NUMERIC(10,2) NOT NULL,
    inactive BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_product_category
        FOREIGN KEY (fk_category)
        REFERENCES product_category(id)
);

CREATE TABLE order (
    id IDENTITY PRIMARY KEY,
    table_number INT NOT NULL,
    waiter VARCHAR(20) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING'
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
);

CREATE TABLE order_item (
    id IDENTITY PRIMARY KEY,
    fk_order BIGINT NOT NULL,
    fk_product BIGINT NOT NULL,
    quantity INT NOT NULL CHECK (quantity > 0),
    notes VARCHAR(80),
    canceled BOOLEAN DEFAULT FALSE,

    CONSTRAINT fk_order_item_orders
        FOREIGN KEY (fk_order)
        REFERENCES orders(id),
    CONSTRAINT fk_order_item_product
        FOREIGN KEY (fk_product)
        REFERENCES product(id)
);

-- Criar indices depois