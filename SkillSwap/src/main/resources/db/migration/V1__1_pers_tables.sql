CREATE TABLE IF NOT EXISTS users(
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         username VARCHAR(100) UNIQUE NOT NULL,
                         password VARCHAR(255),
                         role VARCHAR(50) NOT NULL
);

CREATE INDEX idx_users_username ON users(username);

CREATE TABLE IF NOT EXISTS persinf (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         user_id UUID NOT NULL,
                         surname VARCHAR(60) NOT NULL,
                         name VARCHAR(60) NOT NULL,
                         birthyear INT NOT NULL CHECK (birthyear > 1900),
                         phonenumber VARCHAR(15) NOT NULL,
                         email VARCHAR NOT NULL UNIQUE,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_persinf_user_id ON persinf(user_id);

CREATE TABLE IF NOT EXISTS profinf (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         pers_id UUID NOT NULL,
                         user_id UUID NOT NULL,
                         skillname VARCHAR(60) NOT NULL,
                         skilldescription VARCHAR(1000) NOT NULL,
                         cost DOUBLE PRECISION NOT NULL CHECK (cost >= 0),
                         persdescription VARCHAR(1000) NOT NULL,
                         exp DOUBLE PRECISION NOT NULL CHECK (exp >= 0),
                         rating DOUBLE PRECISION DEFAULT 0.0,
                         FOREIGN KEY (pers_id) REFERENCES persinf(id) ON DELETE CASCADE,
                         FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE INDEX idx_profinf_skillname ON profinf(skillname);
CREATE INDEX idx_profinf_pers_id ON profinf(pers_id);
CREATE INDEX idx_profinf_user_id ON profinf(user_id);
