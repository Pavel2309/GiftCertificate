package com.epam.esm.model.query.impl;

import com.epam.esm.model.query.QueryBuilder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Component
public class CertificateQueryBuilder implements QueryBuilder {

    public static final String SQL_WHERE = " WHERE ";
    public static final String SQL_AND = " AND ";
    public static final String SQL_ORDER_BY = " ORDER BY ";
    public static final String SQL_ASC = " ASC ";
    public static final String SQL_DESC = " DESC ";
    public static final String SQL_TAG_TITLE_EQUALS_PARAMETER = " tags.title = ? ";
    public static final String SQL_TAG_TITLE_IN_PARAMETER = " tags.title IN ";
    public static final String SQL_TITLE_OR_DESCRIPTION_LIKE = " certificates.title LIKE CONCAT('%', ? ,'%') OR certificates.description LIKE CONCAT('%', ? ,'%') ";
    public static final String SQL_CERTIFICATE_TITLE = " certificates.title ";
    public static final String SQL_CERTIFICATE_UPDATE_DATE = " certificates.update_date ";
    public static final String SQL_GROUP_BY = " GROUP BY certificates.id, certificates.title, certificates.description, certificates.price, certificates.duration, certificates.create_date, certificates.update_date ";
    public static final String SQL_HAVING_COUNT_DISTINCT_TAGS = " HAVING COUNT(DISTINCT tags.id) = ";
    public static final String SQL_LIMIT = " LIMIT ";
    public static final String SQL_OFFSET = " OFFSET ";

    public static final String EMPTY_STRING = "";
    public static final String COMMA = ",";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String QUESTION_MARK = "?";
    public static final String TAG_TITLE = "tag_title";
    public static final String SEARCH_QUERY = "search_query";
    public static final String SORT = "sort";
    public static final String PAGE = "page";

    public static final String SORT_BY_UPDATE_DATE_ASC = "update_date(asc)";
    public static final String SORT_BY_UPDATE_DATE_DESC = "update_date(desc)";
    public static final String SORT_BY_TITLE_ASC = "title(asc)";
    public static final String SORT_BY_TITLE_DESC = "title(desc)";

    public static final int DEFAULT_ENTITIES_PER_PAGE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    @Override
    public String buildQuery(String sql, Map<String, String> parameters) {
        return sql + buildQueryFromSearchParameters(parameters) +
                buildQueryFromSortParameters(parameters) +
                buildQueryFromPageParameters(parameters);
    }

    private String buildQueryFromSearchParameters(Map<String, String> parameters) {
        if (!parameters.containsKey(TAG_TITLE) && !parameters.containsKey(SEARCH_QUERY)) {
            return SQL_GROUP_BY;
        }
        StringBuilder query = new StringBuilder(SQL_WHERE);
        boolean isNotEmpty = false;
        int numberOfTags = 0;
        if (parameters.containsKey(TAG_TITLE)) {
            String[] tags = parameters.get(TAG_TITLE).split(COMMA);
            if (tags.length > 1) {
                query.append(SQL_TAG_TITLE_IN_PARAMETER);
                query.append(OPEN_BRACKET);
                for (int i = 0; i < tags.length; i++) {
                    query.append(QUESTION_MARK);
                    if (i != tags.length - 1) {
                        query.append(COMMA);
                    }
                }
                numberOfTags = tags.length;
                query.append(CLOSE_BRACKET);
            } else {
                query.append(SQL_TAG_TITLE_EQUALS_PARAMETER);
            }
            isNotEmpty = true;
        }
        if (parameters.containsKey(SEARCH_QUERY)) {
            if (isNotEmpty) {
                query.append(SQL_AND);
            }
            query.append(SQL_TITLE_OR_DESCRIPTION_LIKE);
        }
        query.append(SQL_GROUP_BY);
        if (numberOfTags > 1) {
            query.append(SQL_HAVING_COUNT_DISTINCT_TAGS).append(numberOfTags);
        }
        return query.toString();
    }

    private String buildQueryFromSortParameters(Map<String, String> parameters) {
        if (!parameters.containsKey(SORT)) {
            return EMPTY_STRING;
        }
        StringBuilder query = new StringBuilder(SQL_ORDER_BY);
        String sortParameters = parameters.get(SORT);
        boolean isNotEmpty = false;
        if (sortParameters.contains(SORT_BY_UPDATE_DATE_ASC)) {
            query.append(SQL_CERTIFICATE_UPDATE_DATE + SQL_ASC);
            isNotEmpty = true;
        }
        if (sortParameters.contains(SORT_BY_UPDATE_DATE_DESC)) {
            if (isNotEmpty) {
                query.append(COMMA);
            }
            query.append(SQL_CERTIFICATE_UPDATE_DATE + SQL_DESC);
            isNotEmpty = true;
        }
        if (sortParameters.contains(SORT_BY_TITLE_ASC)) {
            if (isNotEmpty) {
                query.append(COMMA);
            }
            query.append(SQL_CERTIFICATE_TITLE + SQL_ASC);
            isNotEmpty = true;
        }
        if (sortParameters.contains(SORT_BY_TITLE_DESC)) {
            if (isNotEmpty) {
                query.append(COMMA);
            }
            query.append(SQL_CERTIFICATE_TITLE + SQL_DESC);
        }
        return query.toString();
    }

    private String buildQueryFromPageParameters(Map<String, String> parameters) {
        StringBuilder query = new StringBuilder(SQL_LIMIT);
        int pageNumber = DEFAULT_PAGE_NUMBER;
        if (parameters.containsKey(PAGE)) {
            pageNumber = parsePageParameter(parameters.get(PAGE));
        }
        query.append(DEFAULT_ENTITIES_PER_PAGE);
        if (pageNumber * DEFAULT_ENTITIES_PER_PAGE > DEFAULT_ENTITIES_PER_PAGE) {
            query.append(SQL_OFFSET);
            query.append(pageNumber * DEFAULT_ENTITIES_PER_PAGE - DEFAULT_ENTITIES_PER_PAGE);
        }
        return query.toString();
    }

    public ArrayList<String> extractQueryArguments(Map<String, String> parameters) {
        ArrayList<String> arguments = new ArrayList<>();
        if (parameters.containsKey(TAG_TITLE)) {
            String[] tags = parameters.get(TAG_TITLE).split(COMMA);
            if (tags.length > 1) {
                arguments.addAll(Arrays.asList(tags));
            } else {
                arguments.add(parameters.get(TAG_TITLE));
            }
        }
        if (parameters.containsKey(SEARCH_QUERY)) {
            arguments.add(parameters.get(SEARCH_QUERY));
            arguments.add(parameters.get(SEARCH_QUERY));
        }
        return arguments;
    }

    private int parsePageParameter(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_NUMBER;
        }
    }
}
