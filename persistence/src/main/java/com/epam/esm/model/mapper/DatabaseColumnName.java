package com.epam.esm.model.mapper;

import org.springframework.stereotype.Component;

@Component
public class DatabaseColumnName {

    public static final String CERTIFICATE_ID = "certificates.id";
    public static final String CERTIFICATE_TITLE = "certificates.title";
    public static final String CERTIFICATE_DESCRIPTION = "certificates.description";
    public static final String CERTIFICATE_PRICE = "certificates.price";
    public static final String CERTIFICATE_DURATION = "certificates.duration";
    public static final String CERTIFICATE_CREATE_DATE = "certificates.create_date";
    public static final String CERTIFICATE_UPDATE_DATE = "certificates.update_date";

    public static final String TAG_ID = "tags.id";
    public static final String TAG_TITLE = "tags.title";

    public static final String USER_ID = "users.id";
    public static final String USER_NAME = "users.name";

    public static final String ORDER_ID = "orders.id";
    public static final String ORDER_USER_ID = "orders.users_id";
    public static final String ORDER_CERTIFICATE_ID = "orders.certificates_id";
    public static final String ORDER_PRICE = "orders.price";
    public static final String ORDER_PURCHASE_DATE = "orders.purchase_date";

    private DatabaseColumnName() {
    }
}
