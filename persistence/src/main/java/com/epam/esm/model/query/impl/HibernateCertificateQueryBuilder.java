package com.epam.esm.model.query.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.query.HibernateQueryBuilder;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import javax.persistence.Query;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class HibernateCertificateQueryBuilder implements HibernateQueryBuilder {

    public static final String COMMA = ",";
    public static final String TAG_TITLE = "tag_title";
    public static final String SEARCH_QUERY = "search_query";
    public static final String SORT = "sort";

    public static final String SORT_BY_UPDATE_DATE_ASC = "update_date(asc)";
    public static final String SORT_BY_UPDATE_DATE_DESC = "update_date(desc)";
    public static final String SORT_BY_TITLE_ASC = "title(asc)";
    public static final String SORT_BY_TITLE_DESC = "title(desc)";

    @Override
    public Query buildQuery(Session session, Map<String, String> parameters) {
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Certificate> criteriaQuery = criteriaBuilder.createQuery(Certificate.class);
        Root<Certificate> root = criteriaQuery.from(Certificate.class);
        Optional<Predicate> predicate = buildQueryFromSearchParameters(criteriaBuilder, criteriaQuery, root, parameters);
        predicate.ifPresent(criteriaQuery::where);
        criteriaQuery.groupBy(root.get("id"));
        List<Order> orderList = buildOrderList(criteriaBuilder, root, parameters);
        if (!orderList.isEmpty()) {
            criteriaQuery.orderBy(orderList);
        }
        return session.createQuery(criteriaQuery);
    }

    private Optional<Predicate> buildQueryFromSearchParameters(CriteriaBuilder criteriaBuilder, CriteriaQuery<Certificate> criteriaQuery, Root<Certificate> root, Map<String, String> parameters) {
        List<Predicate> totalPredicates = new ArrayList<>();
        if (parameters.containsKey(TAG_TITLE)) {
            List<Predicate> predicates = new ArrayList<>();
            List<String> tags = List.of(parameters.get(TAG_TITLE).split(COMMA));
            Join<Certificate, Tag> certificateTagJoin = root.join("tags", JoinType.LEFT);
            for (String tag : tags) {
                predicates.add(criteriaBuilder.equal(certificateTagJoin.get("title"), tag));
            }
            totalPredicates.add(criteriaBuilder.or(predicates.toArray(Predicate[]::new)));
            criteriaQuery.having(criteriaBuilder.equal(criteriaBuilder.count(root.get("id")), tags.size()));
        }
        if (parameters.containsKey(SEARCH_QUERY)) {
            List<Predicate> predicates = new ArrayList<>();
            String searchQuery = parameters.get(SEARCH_QUERY);
            Predicate titleIsLike = criteriaBuilder.like(root.get("title"), "%" + searchQuery + "%");
            Predicate descriptionIsLike = criteriaBuilder.like(root.get("description"), "%" + searchQuery + "%");
            predicates.add(titleIsLike);
            predicates.add(descriptionIsLike);
            totalPredicates.add(criteriaBuilder.or(predicates.toArray(Predicate[]::new)));
        }
        return !totalPredicates.isEmpty()
                ? Optional.of(criteriaBuilder.and(totalPredicates.toArray(Predicate[]::new)))
                : Optional.empty();
    }

    private List<Order> buildOrderList(CriteriaBuilder criteriaBuilder, Root<Certificate> root, Map<String, String> parameters) {
        List<Order> orderList = new ArrayList<>();
        if (parameters.containsKey(SORT)) {
            String sortParameters = parameters.get(SORT);
            if (sortParameters.contains(SORT_BY_UPDATE_DATE_ASC)) {
                orderList.add(criteriaBuilder.asc(root.get("updateDate")));
            }
            if (sortParameters.contains(SORT_BY_UPDATE_DATE_DESC)) {
                orderList.add(criteriaBuilder.desc(root.get("updateDate")));
            }
            if (sortParameters.contains(SORT_BY_TITLE_ASC)) {
                orderList.add(criteriaBuilder.asc(root.get("title")));
            }
            if (sortParameters.contains(SORT_BY_TITLE_DESC)) {
                orderList.add(criteriaBuilder.desc(root.get("title")));
            }
        }
        return orderList;
    }
}
