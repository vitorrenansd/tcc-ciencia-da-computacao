-- Exemplos de dados, serão alterados depois

INSERT INTO product_category (id, name, inactive) VALUES
(1, 'Pratos Principais', FALSE);

INSERT INTO product (id, category_id, name, description, price, inactive) VALUES
(107, 1, 'Picanha na brasa', 'Bife de 250g de picanha feita na brasa', 39.90, FALSE),
(49, 1, 'Hambúrguer Artesanal', 'Hambúrguer 180g com queijo', 32.50, FALSE);

