CREATE TYPE IF NOT EXISTS account_type AS ENUM ('STANDARD', 'PREMIUM', 'GOLD');
CREATE TYPE IF NOT EXISTS transaction_type AS ENUM ('IN', 'OUT');

CREATE TABLE IF NOT EXISTS account (
    id UUID PRIMARY KEY DEFAULT uuidv4(),
    account_type account_type NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction (
    id UUID PRIMARY KEY DEFAULT uuidv4(),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_type transaction_type NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    account_id UUID NOT NULL,
    reason VARCHAR(100),
    CONSTRAINT fk_account FOREIGN KEY (account_id) REFERENCES account(id) ON DELETE CASCADE
);
