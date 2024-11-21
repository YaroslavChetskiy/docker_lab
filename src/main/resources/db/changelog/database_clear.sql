TRUNCATE TABLE order_item, orders, inventory,
    magazine_author, magazine, department,
    customer, store, author,
    publisher, series
RESTART IDENTITY CASCADE;
