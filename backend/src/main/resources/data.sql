-- Exemplos de dados, serão alterados depois

INSERT INTO product_category (id, name, inactive) VALUES
(1, 'PORÇÕES', FALSE),
(2, 'BEBIDAS', FALSE),
(3, 'LANCHES', FALSE);

INSERT INTO product (id, category_id, name, description, price, inactive) VALUES
(1, 3, 'Hambúrguer de costela e cheddar', 'Hambúrguer de 200g com queijo cheddar e pão brioche', 39.90, FALSE),
(2, 3, 'Hambúrguer com bacon', 'Hambúrguer 180g com bacon triturado', 36.90, FALSE),
(3, 2, 'Coca-Cola 310ml', 'Copo especial com gelo e limão', 4.99, FALSE),
(4, 2, 'Coca-Cola zero 310ml', 'Copo especial com gelo e limão', 4.99, FALSE),
(5, 1, 'Batata frita 500g', 'Porção de 500g de batata frita crocante', 20.90, 1, FALSE),
(6, 1, 'Batata frita 1kg', 'Porção de 1kg de batata frita crocante', 32.99, 1, FALSE);