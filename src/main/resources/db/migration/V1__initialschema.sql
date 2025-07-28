CREATE SEQUENCE order_id_seq START 1;

CREATE TABLE "order" (
                         id BIGINT DEFAULT nextval('order_id_seq') PRIMARY KEY,
                         customer_id BIGINT,
                         customer_email VARCHAR(255),
                         total NUMERIC(19, 2) NOT NULL,
                         status VARCHAR(255) NOT NULL,
                         payment_type VARCHAR(10) NOT NULL,
                         payment_status VARCHAR(10) NOT NULL,
                         payment_qr_code VARCHAR(15),
                         waiting_time_in_minutes BIGINT,
                         created_at TIMESTAMP NOT NULL
);

CREATE SEQUENCE order_item_id_seq START 1;

CREATE TABLE order_item (
                            id BIGINT DEFAULT nextval('order_item_id_seq') PRIMARY KEY,
                            order_id BIGINT NOT NULL,
                            product_id BIGINT NOT NULL,
                            quantity INTEGER,
                            created_at TIMESTAMP NOT NULL,
                            CONSTRAINT fk_order FOREIGN KEY (order_id) REFERENCES "order" (id),
                            CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product (id)
);