DROP TABLE IF EXISTS Document;
CREATE TABLE Document (
    documentId varchar(50) NOT NULL,
    documentNumber VARCHAR(30) NOT NULL,
    documentIssueDate DATE NOT NULL,
    documentIssueCountry VARCHAR(30) NOT NULL,
    documentTypeCode VARCHAR(30) NOT NULL,
    PRIMARY KEY(documentId)
);

DROP TABLE IF EXISTS Address;
CREATE TABLE Address (
    addressId varchar(50) NOT NULL,
    street VARCHAR(50) NOT NULL,
    houseNumber INT NOT NULL,
    postalCode VARCHAR(10) NOT NULL,
    city VARCHAR(30) NOT NULL,
    country VARCHAR(30) NOT NULL,
    PRIMARY KEY(addressId)
);

DROP TABLE IF EXISTS Customer;
CREATE TABLE Customer (
    customerId varchar(50)  NOT NULL,
    givenName VARCHAR(50)  NOT NULL,
    nameInitial VARCHAR(1)  NOT NULL,
    surname VARCHAR(50)  NOT NULL,
    birthDate DATE  NOT NULL,
    userName VARCHAR(50)  NOT NULL,
    password VARCHAR(255)  NOT NULL,
    documentId varchar(50)  NOT NULL,
    addressId varchar(50)  NOT NULL,
    created datetime NOT NULL,
    PRIMARY KEY (customerId),
    unique (userName),
    FOREIGN KEY (documentId) REFERENCES Document(documentId),
    FOREIGN KEY (addressId) REFERENCES Address(addressId)
);

DROP TABLE IF EXISTS Account;
CREATE TABLE Account (
    accountId varchar(50) NOT NULL,
    iban VARCHAR(30) NOT NULL,
    customerId varchar(50) NOT NULL,
    PRIMARY KEY(accountId),
    unique (iban),
    FOREIGN KEY (customerId) REFERENCES Customer(customerId)
);
