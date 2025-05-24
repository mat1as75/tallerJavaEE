INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (1);
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (2);
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (3);

INSERT INTO commerce_Commerce (email, password, account_accountNumber) VALUES ("comercio1@mail.com", "pass1", 1);
INSERT INTO commerce_Commerce (email, password, account_accountNumber) VALUES ("comercio2@mail.com", "pass2", 2);
INSERT INTO commerce_Commerce (email, password, account_accountNumber) VALUES ("comercio3@mail.com", "pass3", 3);

INSERT INTO commerce_Pos (status) VALUES (1);
INSERT INTO commerce_Pos (status) VALUES (0);
INSERT INTO commerce_Pos (status) VALUES (1);
INSERT INTO commerce_Pos (status) VALUES (0);

INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (1, 1);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (2, 4);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (2, 2);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (3, 3);

INSERT INTO commerce_Complaint (message) VALUES ("Error en el cobro de varias transacciones hoy.");
INSERT INTO commerce_Complaint (message) VALUES ("El sistema estuvo caído por más de 1 hora.");
INSERT INTO commerce_Complaint (message) VALUES ("Stock desactualizado en la plataforma de ventas.");

INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (1, 1);
INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (1, 2);
INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (2, 3);