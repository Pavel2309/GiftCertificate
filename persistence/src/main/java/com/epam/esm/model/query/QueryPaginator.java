package com.epam.esm.model.query;

import org.springframework.hateoas.PagedModel;

import javax.persistence.Query;
import java.util.Map;

/**
 * The Query paginator interface simplifies the implementation of the pagination
 */
public interface QueryPaginator {
    /**
     * Paginates query based on the provided page and size parameters
     *
     * @param query a Java persistence query object
     * @param parameters page and size parameters
     * @return a page metadata object
     */
    PagedModel.PageMetadata paginateQuery(Query query, Map<String, String> parameters);
}
