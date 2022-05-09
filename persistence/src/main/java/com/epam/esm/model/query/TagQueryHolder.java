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

    public static final String HQL_FIND_ALL = "FROM Tag t";
    public static final String HQL_FIND_BY_TITLE = "FROM Tag t WHERE t.title = :title";
    public static final String HQL_DELETE_BY_ID = "DELETE FROM Tag t WHERE t.id = :id";
    public static final String HQL_FIND_BY_CERTIFICATE_ID = "SELECT t FROM Tag t INNER JOIN Certificate c WHERE c.id = :id";

    private TagQueryHolder() {
    }
}
