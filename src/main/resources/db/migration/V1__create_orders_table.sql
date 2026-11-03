CREATE TABLE orders(
                       id UUID PRIMARY KEY,
                       description VARCHAR(255) NOT NULL,
                       amount_in_paise BIGINT NOT NULL,
                       currency VARCHAR(3) NOT NULL DEFAULT 'INR',
                       status VARCHAR(30) NOT NULL,
                       created_at TIMESTAMPTZ NOT NULL,
                       updated_at TIMESTAMP NOT NULL,
                       CONSTRAINT chk_orders_amount
                           CHECK ( amount_in_paise >= 100 ),
                       CONSTRAINT chk_orders_currency
                           CHECK ( currency = 'INR' ),
                       CONSTRAINT chk_orders_status
                           CHECK (
                               status IN (
                                          'CREATED',
                                          'PAYMENT_PENDING',
                                          'PAID',
                                          'FAILED'
                                   )
                               )
);