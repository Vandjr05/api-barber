CREATE TABLE USERS(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password HASH NOT NULL,
    cpf VARCHAR(255) NOT NULL,
    barber BOOLEAN NOT NULL,
    code VARCHAR(5) NOT NULL
);

CREATE TABLE ENTERPRISE(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    cnpj VARCHAR(255) NOT NULL
);

