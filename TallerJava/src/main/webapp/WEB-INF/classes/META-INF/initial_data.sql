INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (1);
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (2);
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (3);

INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (1, "comercio1@mail.com", "1", 1);
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (2, "comercio2@mail.com", "2", 2);
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (3, "comercio3@mail.com", "3", 3);

INSERT INTO commerce_Pos (id, status) VALUES (1, 1);
INSERT INTO commerce_Pos (id, status) VALUES (4, 0);
INSERT INTO commerce_Pos (id, status) VALUES (2, 1);
INSERT INTO commerce_Pos (id, status) VALUES (3, 0);

INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (1, 1);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (1, 4);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (2, 2);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (3, 3);

INSERT INTO commerce_Complaint (id, message) VALUES (1, "Error en el cobro de varias transacciones hoy.");
INSERT INTO commerce_Complaint (id, message) VALUES (2, "El sistema estuvo caído por más de 1 hora.");
INSERT INTO commerce_Complaint (id, message) VALUES (3, "Stock desactualizado en la plataforma de ventas.");

INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (1, 1);
INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (1, 2);
INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (2, 3);