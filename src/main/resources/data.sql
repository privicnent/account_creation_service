INSERT INTO Document(documentId,documentNumber,documentIssueDate,documentIssueCountry,documentTypeCode) values ('1','AB123456','2020-01-22','NL','PASSPORT');
INSERT INTO Address(addressId,street,houseNumber,postalCode,city,country) values ('1','abcStreet',123,'1234 AB','Utrecht','NL');
INSERT INTO Customer(customerId,givenName,nameInitial,surname,birthDate,userName,password,documentId,addressId,created) values ('1','testName','T','Test','2020-01-22','testUserName','test123','1','1',CURRENT_TIMESTAMP);
INSERT INTO Account(accountId,iban,customerId) values ('1','NL008910995322367791','1');
