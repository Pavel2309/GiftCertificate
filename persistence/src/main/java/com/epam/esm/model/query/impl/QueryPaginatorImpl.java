package com.epam.esm.model.query.impl;

import com.epam.esm.model.query.QueryPaginator;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import java.util.Map;

@Component
public class QueryPaginatorImpl implements QueryPaginator {

    public static final String PAGE = "page";
    public static final String SIZE = "size";

    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int DEFAULT_PAGE_NUMBER = 1;

    @Override
    public PagedModel.PageMetadata paginateQuery(Query query, Map<String, String> parameters) {
        int pageNumber = DEFAULT_PAGE_NUMBER;
        int pageSize = DEFAULT_PAGE_SIZE;
        long totalElement = query.getResultList().size();
        if (parameters.containsKey(PAGE)) {
            pageNumber = parsePageParameter(parameters.get(PAGE));
        }
        if (parameters.containsKey(SIZE)) {
            pageSize = parseSizeParameter(parameters.get(SIZE));
        }
        long totalPages = totalElement / pageSize;
        if (totalElement > pageSize || totalPages == 0) {
            totalPages = totalPages + 1;
        }

        query.setMaxResults(pageSize);
        if (pageNumber * pageSize > pageSize) {
            query.setFirstResult(pageNumber * pageSize - pageSize);
        }
        return new PagedModel.PageMetadata(pageSize, pageNumber, totalElement, totalPages);
    }

    private int parsePageParameter(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_NUMBER;
        }
    }

    private int parseSizeParameter(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            return DEFAULT_PAGE_SIZE;
        }
    }
}
