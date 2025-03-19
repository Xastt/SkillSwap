CREATE TABLE IF NOT EXISTS review(
    reviewId UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    rating NUMERIC(4,2) NOT NULL,
    comment VARCHAR(300) NOT NULL,
    reviewer UUID NOT NULL,
    userEvaluated UUID NOT NULL
);
