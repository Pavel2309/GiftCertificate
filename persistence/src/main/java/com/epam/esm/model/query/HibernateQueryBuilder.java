package com.epam.esm.model.query;

import org.hibernate.Session;
import org.springframework.hateoas.PagedModel;

import javax.persistence.Query;
import java.util.Map;

public interface HibernateQueryBuilder {
    Query buildQuery(Session session, Map<String, String> parameters);

    PagedModel.PageMetadata paginateQuery(Query query, Map<String, String> parameters);

}
