
-- COMMERCE MODULE
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (1);
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (2);
INSERT INTO commerce_CommercialBankAccount (accountNumber) VALUES (3);

INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (123,"comercio1@mail.com", "pass1", 1);
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (456,"comercio2@mail.com", "pass2", 2);
INSERT INTO commerce_Commerce (rut, email, password, account_accountNumber) VALUES (789,"comercio3@mail.com", "pass3", 3);

INSERT INTO commerce_Pos (status) VALUES (1);
INSERT INTO commerce_Pos (status) VALUES (0);
INSERT INTO commerce_Pos (status) VALUES (1);
INSERT INTO commerce_Pos (status) VALUES (0);

INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (123, 1);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (456, 4);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (456, 2);
INSERT INTO commerce_CommercePos (COMMERCE_RUT, POS_ID) VALUES (789, 3);

INSERT INTO commerce_Complaint (message) VALUES ("Error en el cobro de varias transacciones hoy.");
INSERT INTO commerce_Complaint (message) VALUES ("El sistema estuvo caído por más de 1 hora.");
INSERT INTO commerce_Complaint (message) VALUES ("Stock desactualizado en la plataforma de ventas.");

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
INSERT INTO Purchase (id, amount, commerce_rut, pos_id, date, description) VALUES (1, 1500.00, 1, 1, '2025-05-20 10:33:55.683000', 'Compra realizada desde POS 1');
INSERT INTO Purchase (id, amount, commerce_rut, pos_id, date, description) VALUES (2, 3000.50, 2, 2, '2025-05-21 12:11:20.123000', 'Compra online desde POS 2');
INSERT INTO Purchase (id, amount, commerce_rut, pos_id, date, description) VALUES (3, 8275.75, 3, 3, '2025-05-20 14:45:33.456000', 'Compra presencial desde POS 3');
