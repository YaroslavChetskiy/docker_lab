-- Индекс на поле department_id в таблице inventory
CREATE INDEX IF NOT EXISTS idx_inventory_department_id ON inventory (department_id);

-- Индекс на поле magazine_id в таблице inventory
CREATE INDEX IF NOT EXISTS idx_inventory_magazine_id ON inventory (magazine_id);

-- Индекс на поле order_id в таблице order_item
CREATE INDEX IF NOT EXISTS idx_order_item_order_id ON order_item (order_id);

-- Индекс на поле magazine_id в таблице order_item
CREATE INDEX IF NOT EXISTS idx_order_item_magazine_id ON order_item (magazine_id);

-- Индекс на поле customer_id в таблице orders
CREATE INDEX IF NOT EXISTS idx_orders_customer_id ON orders (customer_id);

-- Индекс на поле title в таблице magazine
CREATE INDEX IF NOT EXISTS idx_magazine_title ON magazine (title);