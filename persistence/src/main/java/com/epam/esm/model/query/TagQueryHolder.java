package com.epam.esm.model.query;

public final class TagQueryHolder {

    public static final String SQL_FIND_ALL_TAGS = """
            SELECT tags.id, tags.title
            FROM tags
            """;
    public static final String SQL_FIND_TAG_BY_ID = """
            SELECT tags.id, tags.title
            FROM tags
            WHERE tags.id = ?
            """;
    public static final String SQL_FIND_TAG_BY_TITLE = """
            SELECT tags.id, tags.title
            FROM tags
            WHERE tags.title = ?
            """;
    public static final String SQL_FIND_TAGS_BY_CERTIFICATE_ID = """
            SELECT tags.id, tags.title
            FROM tags
            INNER JOIN certificates_has_tags ON certificates_has_tags.tags_id = tags.id
            WHERE certificates_has_tags.certificates_id = ?
            """;
    public static final String SQL_CREATE_TAG = """
            INSERT INTO tags (title) VALUES ( ? )
            """;
    public static final String SQL_DELETE_TAG = """
            DELETE FROM tags WHERE tags.id = ?
            """;
    public static final String SQL_SELECT_MOST_POPULAR_TAGS_FROM_USERS_WHO_SPENT_THE_MOST = """
            WITH top_users_tags_with_frequency AS
                     (SELECT users_who_spent_the_most.id AS user_id, users_who_spent_the_most.spent_amount,
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
                               INNER JOIN certificates_has_tags ON orders_has_certificates.certificates_id = certificates_has_tags.certificates_id
                      GROUP BY users_who_spent_the_most.id, users_who_spent_the_most.spent_amount, certificates_has_tags.tags_id
                      ORDER BY user_id, tag_count DESC)
            SELECT tags.id, tags.title
            FROM top_users_tags_with_frequency
                     INNER JOIN
                 (SELECT user_id, MAX(tag_count) AS max_tag_count
                  FROM top_users_tags_with_frequency
                  GROUP BY user_id) top_users_max_tag_count
                 ON top_users_tags_with_frequency.user_id = top_users_max_tag_count.user_id
                     AND top_users_tags_with_frequency.tag_count = top_users_max_tag_count.max_tag_count
            INNER JOIN tags ON tag_id = tags.id;
            """;

    public static final String HQL_FIND_ALL = "FROM Tag t";
    public static final String HQL_FIND_BY_TITLE = "FROM Tag t WHERE t.title = :title";
    public static final String HQL_DELETE_BY_ID = "DELETE FROM Tag t WHERE t.id = :id";
    public static final String HQL_FIND_BY_CERTIFICATE_ID = "SELECT t FROM Tag t INNER JOIN Certificate c WHERE c.id = :id";

    private TagQueryHolder() {
    }
}
