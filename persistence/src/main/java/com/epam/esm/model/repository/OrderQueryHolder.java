package com.epam.esm.model.repository;

public class OrderQueryHolder {

    public static final String SQL_FIND_ALL_ORDERS = """
            SELECT orders.id, orders.price, orders.purchase_date, users.id, users.name
            FROM orders
            INNER JOIN users on orders.users_id = users.id
            """;
    public static final String SQL_FIND_ORDER_BY_ID = """
            SELECT orders.id, orders.price, orders.purchase_date, users.id, users.name
            FROM orders
            INNER JOIN users on orders.users_id = users.id
            WHERE orders.id = ?
            """;
    public static final String SQL_FIND_ALL_ORDERS_BY_USER_ID = """
            SELECT orders.id, orders.price, orders.purchase_date, users.id, users.name
            FROM orders
            INNER JOIN users on orders.users_id = users.id
            WHERE users.id = ?
            """;
    public static final String SQL_CREATE_ORDER = """
            INSERT INTO orders(orders.users_id, orders.price, orders.purchase_date)
            VALUES (?, ?, ?)
            """;
    public static final String SQL_DELETE_ORDER_BY_ID = """
            DELETE FROM orders
            WHERE orders.id = ?
            """;
    public static final String SQL_LINK_ORDER_WITH_CERTIFICATE = """
            INSERT INTO orders_has_certificates(orders_id, certificates_id)
            VALUES (?, ?)
            """;
    public static final String SQL_UNLINK_ORDER_WITH_CERTIFICATES = """
            DELETE FROM orders_has_certificates
            WHERE orders_id = ?
            """;

    public static final String SQL_MOST_FREQUENT_TAGS_OF_TOP_SPENDING_USERS = """
            WITH top_users_tags_with_frequency AS
                (SELECT users_who_spent_the_most.id AS user_id, users_who_spent_the_most.spent_amount,\s
                        certificates_has_tags.tags_id AS tag_id, COUNT(certificates_has_tags.tags_id) AS tag_count
                FROM (SELECT orders.users_id AS id, SUM(orders.price) AS spent_amount
                FROM orders
                GROUP BY orders.users_id
                HAVING SUM(orders.price) =
                                      (SELECT sum(orders.price) AS spend_sum
                                       FROM orders
                                       GROUP BY orders.users_id
                                       ORDER BY spend_sum DESC
                                       LIMIT 1)) users_who_spent_the_most
                INNER JOIN orders ON orders.users_id = users_who_spent_the_most.id
                INNER JOIN orders_has_certificates ON orders.id = orders_has_certificates.orders_id
                INNER JOIN certificates ON orders_has_certificates.certificates_id = certificates.id
                INNER JOIN certificates_has_tags ON certificates.id = certificates_has_tags.certificates_id
                GROUP BY users_who_spent_the_most.id, users_who_spent_the_most.spent_amount, certificates_has_tags.tags_id
                ORDER BY user_id, tag_count DESC)
            SELECT top_users_tags_with_frequency.user_id, spent_amount, tag_id, tag_count
            FROM top_users_tags_with_frequency
            INNER JOIN
                    (SELECT user_id, MAX(tag_count) AS max_tag_count
                    FROM top_users_tags_with_frequency
                    GROUP BY user_id) top_users_max_tag_count
            ON top_users_tags_with_frequency.user_id = top_users_max_tag_count.user_id
            AND top_users_tags_with_frequency.tag_count = top_users_max_tag_count.max_tag_count
            """;

    private OrderQueryHolder() {
    }
}
