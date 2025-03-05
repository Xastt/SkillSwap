CREATE TABLE IF NOT EXISTS skillExchange(
    exchangeId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    skillOffered VARCHAR(60) NOT NULL,
    userOffering UUID NOT NULL,
    userRequesting UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS transaction(
    transactionId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    date TIMESTAMP NOT NULL,
    status VARCHAR(50) NOT NULL,
    changeId UUID NOT NULL,
    FOREIGN KEY (changeId) REFERENCES skillExchange(exchangeId) ON DELETE CASCADE
);