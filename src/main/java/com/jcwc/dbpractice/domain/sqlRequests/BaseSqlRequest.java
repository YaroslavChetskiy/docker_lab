package com.jcwc.dbpractice.domain.sqlRequests;

import lombok.experimental.UtilityClass;

@UtilityClass
public class BaseSqlRequest {

    // Какие журналы были куплены определенным покупателем?
    public static final String GET_MAGAZINES_BY_CUSTOMER_ID = """
            SELECT m.*
            FROM magazine m
            JOIN order_item oi ON m.id = oi.magazine_id
            JOIN orders o ON oi.order_id = o.id
            JOIN customer c ON c.id = o.customer_id
            WHERE o.status = 'COMPLETED' AND c.id = ?
            LIMIT 100
            """;

    // Как называется журнал с заданным ISBN?
    public static final String GET_MAGAZINE_TITLE_BY_ISBN = """
            SELECT m.title
            FROM magazine m
            WHERE m.isbn = ?
            LIMIT 100
            """;

    // Какой ISBN у журнала с заданным названием?
    public static final String GET_ISBN_BY_MAGAZINE_TITLE = """
            SELECT m.isbn
            FROM magazine m
            WHERE m.title = ?
            LIMIT 100
            """;

    // Когда журнал был куплен?
    public static final String GET_ORDER_DATE_BY_MAGAZINE_TITLE = """
            SELECT o.order_date
            FROM orders o
            JOIN order_item oi ON o.id = oi.order_id
            JOIN magazine m ON m.id = oi.magazine_id
            WHERE o.status = 'COMPLETED' AND m.title = ?
            LIMIT 100
            """;

    // Кто из покупателей купил журнал более месяца тому назад? (Только покупатели)
    public static final String GET_CUSTOMERS_WITH_ORDERS_OLDER_THAN_ONE_MONTH = """
            SELECT DISTINCT c.*
            FROM customer c
            JOIN orders o ON c.id = o.customer_id
            WHERE o.order_date < NOW() - INTERVAL '1 month' AND o.status = 'COMPLETED'
            LIMIT 100
            """;

    // Кто из покупателей купил журнал более месяца тому назад? (С датой заказа)
    public static final String GET_CUSTOMERS_WITH_ORDERS_OLDER_THAN_ONE_MONTH_WITH_DATE = """
            SELECT c.*, o.order_date
            FROM customer c
            JOIN orders o ON c.id = o.customer_id
            WHERE o.order_date < NOW() - INTERVAL '1 month' AND o.status = 'COMPLETED'
            LIMIT 100
            """;

    // Найти покупателя самых редких журналов (по наличию в магазине)
    public static final String GET_CUSTOMERS_OF_RAREST_MAGAZINES = """
            WITH rare_magazines_in_store AS (
                SELECT d.store_id, i.magazine_id, SUM(i.quantity) AS sum_quantity
                FROM inventory i
                JOIN department d ON i.department_id = d.id
                GROUP BY d.store_id, i.magazine_id
            ),
            min_quantity_per_store AS (
                SELECT store_id, MIN(sum_quantity) AS min_quantity
                FROM rare_magazines_in_store
                GROUP BY store_id
            ),
            rare_magazines_per_store AS (
                SELECT rm.store_id, rm.magazine_id
                FROM rare_magazines_in_store rm
                JOIN min_quantity_per_store mqps ON mqps.min_quantity = rm.sum_quantity
            )
            SELECT DISTINCT
                s.id AS store_id, s.name AS store_name,
                m.id AS magazine_id, m.title AS magazine_title,
                c.id AS customer_id, c.full_name AS customer_full_name
            FROM customer c
            JOIN orders o ON c.id = o.customer_id
            JOIN order_item oi ON o.id = oi.order_id
            JOIN magazine m ON m.id = oi.magazine_id
            JOIN store s ON o.store_id = s.id
            JOIN rare_magazines_per_store rmps ON rmps.store_id = s.id AND rmps.magazine_id = m.id
            ORDER BY s.id, m.id, c.id
            LIMIT 100
            """;

    // Какое число покупателей пользуется определенным магазином?
    public static final String GET_CUSTOMER_COUNT_BY_STORE_ID = """
            SELECT s.id, s.name, COUNT(DISTINCT c.id) AS customer_count
            FROM store s
            LEFT JOIN orders o ON s.id = o.store_id
            LEFT JOIN customer c ON o.customer_id = c.id
            GROUP BY s.id
            ORDER BY s.id
            LIMIT 100
            """;

    // Сколько покупателей младше 20 лет? (Только количество)
    public static final String COUNT_CUSTOMERS_YOUNGER_THAN_20 = """
            SELECT COUNT(*) AS young_customer_count
            FROM customer c
            WHERE EXTRACT(YEAR FROM age(c.birth_date)) < 20
            LIMIT 100
            """;

    // Сколько покупателей младше 20 лет? (Список покупателей)
    public static final String GET_YOUNG_CUSTOMERS = """
            SELECT c.*
            FROM customer c
            WHERE EXTRACT(YEAR FROM age(c.birth_date)) < 20
            LIMIT 100
            """;

    // Получить 100 журналов без сортировки
    public static final String GET_100_MAGAZINES_WITH_LIMIT = """
            SELECT m.*
            FROM magazine m
            LIMIT 100
            """;

    // Получить 100 журналов с сортировкой по названию
    public static final String GET_100_MAGAZINES_WITH_SORTING = """
            SELECT m.*
            FROM magazine m
            ORDER BY m.title
            LIMIT 100
            """;

}


