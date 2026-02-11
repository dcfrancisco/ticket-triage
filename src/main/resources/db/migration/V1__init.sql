CREATE TABLE IF NOT EXISTS tickets (
    id UUID PRIMARY KEY,
    subject VARCHAR(120) NOT NULL,
    description VARCHAR(4000) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    status VARCHAR(32) NOT NULL,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_tickets_status ON tickets (status);
