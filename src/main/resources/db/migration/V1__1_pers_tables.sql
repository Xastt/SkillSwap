CREATE TABLE IF NOT EXISTS persinf (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         surname VARCHAR(60) NOT NULL,
                         name VARCHAR(60) NOT NULL,
                         birthyear INT NOT NULL CHECK (birthyear > 1900),
                         phonenumber VARCHAR NOT NULL,
                         email VARCHAR NOT NULL
);

CREATE INDEX idx_persinf_surname ON persinf(surname);

CREATE TABLE IF NOT EXISTS profinf (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         pers_id UUID NOT NULL,
                         skillname VARCHAR(60) NOT NULL,
                         skilldescription VARCHAR(1000) NOT NULL,
                         cost DOUBLE PRECISION NOT NULL CHECK (cost >= 0),
                         persdescription VARCHAR(1000) NOT NULL,
                         exp DOUBLE PRECISION NOT NULL CHECK (exp >= 0),
                         rating DOUBLE PRECISION,
                         FOREIGN KEY (pers_id) REFERENCES persinf(id) ON DELETE CASCADE
);

CREATE INDEX idx_profinf_skillname ON profinf(skillname);
CREATE INDEX idx_profinf_pers_id ON profinf(pers_id);