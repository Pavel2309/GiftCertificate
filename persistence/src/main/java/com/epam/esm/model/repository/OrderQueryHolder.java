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
            SELECT orders.id, orders.price, orders.purchase_date, u.id, u.name
            FROM orders
            INNER JOIN users u on orders.users_id = u.id
            WHERE u.id = ?
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

    private OrderQueryHolder() {
    }
}
