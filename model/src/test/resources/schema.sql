DROP TABLE IF EXISTS gift_certificates;
CREATE TABLE gift_certificates(
id SERIAL PRIMARY KEY,
name VARCHAR(50) NOT NULL UNIQUE,
description VARCHAR(255),
price DECIMAL(5, 2) NOT NULL,
duration INTEGER NOT NULL,
createDate TIMESTAMP NOT NULL,
lastUpdateDate TIMESTAMP
);

DROP TABLE IF EXISTS tags;
CREATE TABLE tags (
id SERIAL PRIMARY KEY,
name VARCHAR(20) NOT NULL
);

DROP TABLE IF EXISTS tags_certificates;
CREATE TABLE tags_certificates (
gift_certificate_id SERIAL NOT NULL,
tag_id SERIAL NOT NULL,
FOREIGN KEY (gift_certificate_id)
    REFERENCES gift_certificates(id) ON DELETE CASCADE ON UPDATE NO ACTION,
FOREIGN KEY (tag_id)
    REFERENCES tags(id) ON DELETE CASCADE ON UPDATE NO ACTION,
CONSTRAINT tags_certificates_unique_key
UNIQUE (gift_certificate_id, tag_id)
);
