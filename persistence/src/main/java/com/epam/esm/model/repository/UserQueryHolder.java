package com.epam.esm.model.repository;

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

    private UserQueryHolder() {
    }
}
