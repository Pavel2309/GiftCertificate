package com.epam.esm.model.query;

public class UserQueryHolder {

    public static final String SQL_FIND_ALL_USERS = """
            SELECT users.id, users.name
            FROM users
            """;
    public static final String SQL_FIND_USER_BY_ID = """
            SELECT users.id, users.name
            FROM users
            WHERE users.id = ?
            """;

    public static final String HQL_FIND_ALL = "FROM User u";
    public static final String HQL_FIND_BY_TITLE = "FROM User u WHERE u.id = :id";
    public static final String HQL_DELETE_BY_ID = "DELETE FROM User u WHERE u.id = :id";

    private UserQueryHolder() {
    }
}
