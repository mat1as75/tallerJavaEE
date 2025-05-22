-- PurchaseCommerce
INSERT INTO purchase_commerce (rut, totalSalesAmount)
VALUES (20123456, 0),
       (20456789, 0);

-- PurchasePos (POS = terminales de punto de venta)
INSERT INTO purchase_pos (id, status)
VALUES (101, true),
       (102, true),
       (103, false),
       (104, true);
-- Purchases
-- Se asume que los comercios insertados arriba existen. !!IMPORTANTE el total amount de este test no se ven reflejados en el contador dailyamount del modelo purchase
INSERT INTO Purchase (id, amount, commerce_rut, pos_id, date, description)
VALUES (1, 1500.00, 20123456, 101, '2025-05-20 10:33:55.683000', 'Compra realizada desde POS 101'),
       (2, 3000.50, 20123456, 102, '2025-05-21 12:11:20.123000', 'Compra online desde POS 102'),
       (3, 8275.75, 20456789, 101, '2025-05-20 14:45:33.456000', 'Compra presencial desde POS 101');


