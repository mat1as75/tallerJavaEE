-- Cards
INSERT INTO Card (number, brand, expirationDate) VALUES
  (100001, 'VISA', '05/2026'),
  (100002, 'MASTERCARD', '12/2027'),
  (100003, 'AMEX', '09/2025');

-- PurchaseCommerce
INSERT INTO purchase_commerce (rut) VALUES
  ( 20123456),
  ( 20456789);

-- Purchases
-- Se asume que los números de tarjeta y comercios insertados arriba existen.
INSERT INTO Purchase (id, amount, description, card_number, commerce_rut) VALUES
  (1, 1500.00, 'Compra realizada desde POS 101', 100001,20123456 ),
  (2, 3000.50, 'Compra online desde sitio web', 100002, 20123456),
  (3, 875.75, 'Compra presencial', 100003, 20456789);

-- PurchasePos (POS = terminales de punto de venta)
INSERT INTO purchase_pos (id,status) VALUES
   (101,true),
   (102,true),
   (103,false),
   (104,true);
