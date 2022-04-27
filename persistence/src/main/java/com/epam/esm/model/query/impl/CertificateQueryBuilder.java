package com.epam.esm.model.query.impl;

import com.epam.esm.model.query.QueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Map;

@Component
public class CertificateQueryBuilder implements QueryBuilder {

    public static final String SQL_WHERE = " WHERE ";
    public static final String SQL_AND = " AND ";
    public static final String SQL_ORDER_BY = " ORDER BY ";
    public static final String SQL_ASC = " ASC ";
    public static final String SQL_DESC = " DESC ";
    public static final String SQL_TAG_TITLE_EQUALS_PARAMETER = " tags.title = ? ";
    public static final String SQL_TITLE_OR_DESCRIPTION_LIKE =
            " certificates.title LIKE CONCAT('%', ? ,'%') OR certificates.description LIKE CONCAT('%', ? ,'%') ";
    public static final String SQL_CERTIFICATE_TITLE = " certificates.title ";
    public static final String SQL_CERTIFICATE_UPDATE_DATE = " certificates.update_date ";
    public static final String SQL_GROUP_BY = " GROUP BY certificates.id, certificates.title, certificates.description, certificates.price, certificates.duration, certificates.create_date, certificates.update_date ";

    public static final String EMPTY_STRING = "";
    public static final String COMMA = " , ";
    public static final String TAG_TITLE = "tag_title";
    public static final String SEARCH_QUERY = "search_query";
    public static final String SORT = "sort";


    @Override
    public String buildQuery(String sql, Map<String, String> parameters) {
        StringBuilder query = new StringBuilder(sql);
        if (!parameters.isEmpty()) {
            query.append(buildQueryFromSearchParameters(parameters));
            query.append(buildQueryFromSortParameters(parameters));
        }
        return query.toString();
    }

    public ArrayList<String> extractQueryArguments(Map<String, String> parameters) {
        ArrayList<String> arguments = new ArrayList<>();
        if (parameters.containsKey(TAG_TITLE)) {
            arguments.add(parameters.get(TAG_TITLE));
        }
        if (parameters.containsKey(SEARCH_QUERY)) {
            arguments.add(parameters.get(SEARCH_QUERY));
            arguments.add(parameters.get(SEARCH_QUERY));
        }
        return arguments;
    }

    private String buildQueryFromSearchParameters(Map<String, String> parameters) {
        if (!parameters.containsKey(TAG_TITLE) &&
                !parameters.containsKey(SEARCH_QUERY)) {
            return EMPTY_STRING;
        }
        StringBuilder query = new StringBuilder(SQL_WHERE);
        boolean isNotEmpty = false;
        if (parameters.containsKey(TAG_TITLE)) {
            query.append(SQL_TAG_TITLE_EQUALS_PARAMETER);
            isNotEmpty = true;
        }
        if (parameters.containsKey(SEARCH_QUERY)) {
            if (isNotEmpty) {
                query.append(SQL_AND);
            }
            query.append(SQL_TITLE_OR_DESCRIPTION_LIKE);
            isNotEmpty = true;
        }
        query.append(SQL_GROUP_BY);
        return query.toString();
    }

    private String buildQueryFromSortParameters(Map<String, String> parameters) {
        if (!parameters.containsKey(SORT)) {
            return EMPTY_STRING;
        }
        StringBuilder query = new StringBuilder(SQL_ORDER_BY);
        String sortParameters = parameters.get(SORT);
        boolean isNotEmpty = false;
        if (sortParameters.contains("update_date(asc)")) {
            query.append(SQL_CERTIFICATE_UPDATE_DATE + SQL_ASC);
            isNotEmpty = true;
        }
        if (sortParameters.contains("update_date(desc)")) {
            if (isNotEmpty) {
                query.append(COMMA);
            }
            query.append(SQL_CERTIFICATE_UPDATE_DATE + SQL_DESC);
            isNotEmpty = true;
        }
        if (sortParameters.contains("title(asc)")) {
            if (isNotEmpty) {
                query.append(COMMA);
            }
            query.append(SQL_CERTIFICATE_TITLE + SQL_ASC);
            isNotEmpty = true;
        }
        if (sortParameters.contains("title(desc")) {
            if (isNotEmpty) {
                query.append(COMMA);
            }
            query.append(SQL_CERTIFICATE_TITLE + SQL_DESC);
        }
        return query.toString();
    }
}
