-- CATEGORIA
INSERT INTO product_category (name, inactive) VALUES
('GERAL', FALSE);

-- PRODUTOS
INSERT INTO product (category_id, name, description, price, inactive) VALUES
(1, 'Fritas media', 'Batata frita no oleo com sal e pimenta', 21.99, FALSE),
(1, 'Bife de picanha', 'Picanha feita na brasa, escolha o ponto por favor', 39.99, FALSE),
(1, 'Refrigerante lata', 'Refrigerante lata 350ml', 6.50, FALSE),
(1, 'Suco natural', 'Suco natural da fruta 400ml', 9.90, FALSE),
(1, 'Hamburguer artesanal', 'Hamburguer 180g com queijo e salada', 28.90, FALSE),
(1, 'Porcao de calabresa', 'Calabresa acebolada com molho especial', 24.50, FALSE);

-- SESSÕES DE CAIXA
INSERT INTO cash_shift (opened_at, closed_at, status) VALUES
('2026-02-25 18:00:00', '2026-02-25 23:30:00', 'CLOSED'),
('2026-02-26 18:10:00', '2026-02-26 23:45:00', 'CLOSED'),
('2026-02-27 17:55:00', '2026-02-27 23:10:00', 'CLOSED'),
('2026-02-28 18:05:00', '2026-02-28 23:59:00', 'CLOSED'),
('2026-03-01 18:00:00', '2026-03-01 23:20:00', 'CLOSED'),
('2026-03-02 18:15:00', '2026-03-02 23:40:00', 'CLOSED'),
('2026-03-03 08:00:00', '2026-03-03 12:00:00', 'CLOSED'),
('2026-03-03 18:00:00', NULL, 'OPEN');