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

    private DatabaseColumnName() {
    }
}
