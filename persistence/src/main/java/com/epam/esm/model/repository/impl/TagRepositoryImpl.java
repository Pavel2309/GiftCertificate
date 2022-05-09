package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.query.QueryPaginator;
import com.epam.esm.model.repository.TagRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
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
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException("update tag operation is not supported");
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate() >= 1;
    }

    @Override
    public Optional<Tag> findByTitle(String title) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_FIND_BY_TITLE);
        query.setParameter("title", title);
        Tag tag = (Tag) query.getSingleResult();
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> findByCertificateId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_FIND_BY_CERTIFICATE_ID);
        query.setParameter("id", id);
        return query.getResultList();
    }
}
