-- CATEGORIA
INSERT INTO product_category (name, inactive) VALUES
('GERAL', FALSE),
('PORCOES', FALSE),
('BEBIDAS', FALSE);

-- PRODUTOS
INSERT INTO product (category_id, name, description, price, inactive) VALUES
(1, 'Hamburguer artesanal', 'Hamburguer 180g com queijo e salada', 28.90, FALSE),
(2, 'Fritas pequena 300g', 'Batata frita no oleo com sal e pimenta', 14.99, FALSE),
(2, 'Fritas media 700g', 'Batata frita no oleo com sal e pimenta', 21.99, FALSE),
(2, 'Fritas grande 1kg', 'Batata frita no oleo com sal e pimenta', 29.99, FALSE),
(2, 'Porcao de calabresa', 'Calabresa acebolada com molho especial', 24.50, FALSE),
(3, 'Coca Cola Lata 310ml', '', 4.50, FALSE),
(3, 'Coca Cola 600ml', '', 6.90, FALSE);

-- SESSÕES DE CAIXA
INSERT INTO cash_shift (opened_at, closed_at, status) VALUES
('2026-03-01 18:00:00', '2026-03-01 22:00:00', 'CLOSED'),
('2026-03-02 18:00:00', '2026-03-02 22:00:00', 'CLOSED'),
('2026-03-03 18:00:00', '2026-03-03 22:00:00', 'CLOSED'),
('2026-03-04 18:00:00', '2026-03-04 22:00:00', 'CLOSED');