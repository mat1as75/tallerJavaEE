
-- COMMERCE MODULE
-- CommercialBankAccount
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES ("0");
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES ("1");
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES ("2");
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES ("3");

-- Commerce
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (0, "admin@mail.com", "cb71cd3f59909532b783257f5f97d0502af258231f9d17cbe762799c0bb4a0f6", "0");
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (123,"comercio1@mail.com", "e6c3da5b206634d7f3f3586d747ffdb36b5c675757b380c6a5fe5c570c714349", "1");
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (456,"comercio2@mail.com", "1ba3d16e9881959f8c9a9762854f72c6e6321cdd44358a10a4e939033117eab9", "2");
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (789,"comercio3@mail.com", "3acb59306ef6e660cf832d1d34c4fba3d88d616f0bb5c2a9e0f82d18ef6fc167", "3");

INSERT INTO `Group` (name) VALUES ("admin");
INSERT INTO `Group` (name) VALUES ("user");

INSERT INTO commerce_Commerce_Group (commerce_rut, group_name) VALUES (0, "admin"); -- ADMIN DE PRUEBA
INSERT INTO commerce_Commerce_Group (commerce_rut, group_name) VALUES (123, "user");
INSERT INTO commerce_Commerce_Group (commerce_rut, group_name) VALUES (456, "user");
INSERT INTO commerce_Commerce_Group (commerce_rut, group_name) VALUES (789, "user");

-- Pos
INSERT INTO commerce_Pos (status) VALUES (1);
INSERT INTO commerce_Pos (status) VALUES (0);
INSERT INTO commerce_Pos (status) VALUES (1);
INSERT INTO commerce_Pos (status) VALUES (0);

-- CommercePos
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (123, 1);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (456, 4);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (456, 2);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (789, 3);

-- Complaint
INSERT INTO commerce_Complaint (message) VALUES ("Error en el cobro de varias transacciones hoy.");
INSERT INTO commerce_Complaint (message) VALUES ("El sistema estuvo caído por más de 1 hora.");
INSERT INTO commerce_Complaint (message) VALUES ("Stock desactualizado en la plataforma de ventas.");

-- CommerceComplaint
INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (123, 1);
INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (123, 2);
INSERT INTO commerce_CommerceComplaint (COMMERCE_RUT, COMPLAINT_ID) VALUES (456, 3);

-- PURCHASE MODULE
-- PurchaseCommerce
INSERT INTO purchase_Commerce (rut, totalSalesAmount) VALUES (123, 0);
INSERT INTO purchase_Commerce (rut, totalSalesAmount) VALUES (456, 0);
INSERT INTO purchase_Commerce (rut, totalSalesAmount) VALUES (789, 0);

-- PurchasePos (POS = terminales de punto de venta)
INSERT INTO purchase_Pos (id, status) VALUES (1, 1);
INSERT INTO purchase_Pos (id, status) VALUES (2, 0);
INSERT INTO purchase_Pos (id, status) VALUES (3, 1);
INSERT INTO purchase_Pos (id, status) VALUES (4, 0);
-- Purchases
-- Se asume que los comercios insertados arriba existen. !!IMPORTANTE el total amount de este test no se ven reflejados en el contador dailyamount del modelo purchase
INSERT INTO Purchase (id, amount, commerce_rut, pos_id, date, description) VALUES (1, 1500.00, 123, 1, '2025-05-20 10:33:55.683000', 'Compra realizada desde POS 1');
INSERT INTO Purchase (id, amount, commerce_rut, pos_id, date, description) VALUES (2, 3000.50, 456, 2, '2025-05-21 12:11:20.123000', 'Compra online desde POS 2');
INSERT INTO Purchase (id, amount, commerce_rut, pos_id, date, description) VALUES (3, 8275.75, 789, 3, '2025-05-20 14:45:33.456000', 'Compra presencial desde POS 3');

-- TRANSFER MODULE
-- transfer_Deposit
INSERT INTO transfer_Deposit (id, commerceRut, amount, accountNumber, creation_date) VALUES (1, 123, 1350.00, "1", '2025-05-23 10:33:55.683000');
INSERT INTO transfer_Deposit (id, commerceRut, amount, accountNumber, creation_date) VALUES (2, 123, 1440.00, "1", '2025-05-24 10:33:55.683000');
INSERT INTO transfer_Deposit (id, commerceRut, amount, accountNumber, creation_date) VALUES (3, 123, 1530.00, "1", '2025-05-25 10:33:55.683000');
INSERT INTO transfer_Deposit (id, commerceRut, amount, accountNumber, creation_date) VALUES (4, 456, 2700.45, "2", '2025-05-24 10:33:55.683000');
INSERT INTO transfer_Deposit (id, commerceRut, amount, accountNumber, creation_date) VALUES (5, 789, 7448.175, "3", '2025-05-22 10:33:55.683000');