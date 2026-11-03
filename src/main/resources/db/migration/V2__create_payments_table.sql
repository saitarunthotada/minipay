CREATE TABLE payments
(
    id              UUID PRIMARY KEY,
    order_id        UUID        NOT NULL,
    amount_in_paise BIGINT      NOT NULL,
    currency        VARCHAR(3)  NOT NULL DEFAULT 'INR',
    status          VARCHAR(30) NOT NULL,
    created_at      TIMESTAMPTZ NOT NULL,
    updated_at      TIMESTAMPTZ NOT NULL,

    CONSTRAINT fk_payments_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id),
    CONSTRAINT chk_payments_amount
        CHECK ( amount_in_paise >= 100 ),
    CONSTRAINT chk_payments_currency
        CHECK ( currency = 'INR' ),
    CONSTRAINT chk_payments_status
        CHECK (
            status IN (
                       'CREATED',
                       'PENDING',
                       'SUCCEEDED',
                       'FAILED',
                       'CANCELLED'
                )
            )
);

CREATE INDEX idx_payments_order_id
    ON payments(order_id);

CREATE INDEX idx_payments_created_at
    ON payments(created_at);