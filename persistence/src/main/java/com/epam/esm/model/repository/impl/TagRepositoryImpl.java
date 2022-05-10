package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.query.QueryPaginator;
import com.epam.esm.model.repository.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.*;

import static com.epam.esm.model.query.TagQueryHolder.*;

@Repository
public class TagRepositoryImpl implements TagRepository {

    private final SessionFactory sessionFactory;
    private final QueryPaginator queryPaginator;

    public TagRepositoryImpl(SessionFactory sessionFactory, QueryPaginator queryPaginator) {
        this.sessionFactory = sessionFactory;
        this.queryPaginator = queryPaginator;
    }

    @Override
    public PagedModel<Tag> findAll(Map<String, String> parameters) {
        Query query = sessionFactory.getCurrentSession().createQuery(HQL_FIND_ALL);
        PagedModel.PageMetadata metadata = queryPaginator.paginateQuery(query, parameters);
        List<Tag> tags = query.getResultList();
        return PagedModel.of(tags, metadata);
    }

    @Override
    public Optional<Tag> findOne(Long id) {
        Tag tag = sessionFactory.getCurrentSession().get(Tag.class, id);
        return Optional.ofNullable(tag);
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        sessionFactory.getCurrentSession().save(tag);
        return tag;
    }

    @Override
    @Transactional
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("update tag operation is not supported");
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate() >= 1;
    }

    @Override
    public Optional<Tag> findByTitle(String title) {
        try {
            Session session = sessionFactory.getCurrentSession();
            Query query = session.createQuery(HQL_FIND_BY_TITLE);
            query.setParameter("title", title);
            Tag tag = (Tag) query.getSingleResult();
            return Optional.ofNullable(tag);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findByCertificateId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        TypedQuery<Tag> query = session.createSQLQuery(HQL_FIND_BY_CERTIFICATE_ID);
        query.setParameter("id", id);
        List<Tag> tags = query.getResultList();
        return tags;
    }

    @Override
    public List<Tag> findMostPopularTagsOfUsersWhoSpentMost() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createNativeQuery(SQL_SELECT_MOST_POPULAR_TAGS_FROM_USERS_WHO_SPENT_THE_MOST);
        List<Object[]> rows = query.getResultList();
        List<Tag> tags = new ArrayList<>(rows.size());
        for (Object[] row : rows) {
            Tag tag = new Tag();
            tag.setId(Long.valueOf(row[0].toString()));
            tag.setTitle((String) row[1]);
            tags.add(tag);
        }
        return tags;
    }
}
