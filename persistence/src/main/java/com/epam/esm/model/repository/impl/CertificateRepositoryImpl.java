package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.query.impl.HibernateCertificateQueryBuilder;
import com.epam.esm.model.repository.CertificateRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.model.query.CertificateQueryHolder.*;

@Repository
public class CertificateRepositoryImpl implements CertificateRepository {

    private final SessionFactory sessionFactory;
    private final HibernateCertificateQueryBuilder hibernateCertificateQueryBuilder;

    public CertificateRepositoryImpl(SessionFactory sessionFactory,
                                     HibernateCertificateQueryBuilder hibernateCertificateQueryBuilder) {
        this.sessionFactory = sessionFactory;
        this.hibernateCertificateQueryBuilder = hibernateCertificateQueryBuilder;
    }

    @Override
    public List<Certificate> findAll() {
        throw new UnsupportedOperationException("find all is not supported");
    }

    @Override
    public PagedModel<Certificate> findAllWithParameters(Map<String, String> parameters) {
        Session session = sessionFactory.getCurrentSession();
        Query query = hibernateCertificateQueryBuilder.buildQuery(session, parameters);
        PagedModel.PageMetadata pageMetadata = hibernateCertificateQueryBuilder.paginateQuery(query, parameters);
        List<Certificate> certificates = query.getResultList();
        return PagedModel.of(certificates, pageMetadata);
    }

    @Override
    public PagedModel<Certificate> findByOrderId(Long id, Map<String, String> parameters) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_FIND_BY_ORDER_ID);
        query.setParameter("id", id);
        PagedModel.PageMetadata pageMetadata = hibernateCertificateQueryBuilder.paginateQuery(query, parameters);
        List<Certificate> certificates = query.getResultList();
        return PagedModel.of(certificates, pageMetadata);
    }

    @Override
    public Optional<Certificate> findOne(Long id) {
        Certificate certificate = sessionFactory.getCurrentSession().get(Certificate.class, id);
        return Optional.ofNullable(certificate);
    }

    @Override
    @Transactional
    public Certificate create(Certificate certificate) {
        sessionFactory.getCurrentSession().save(certificate);
        return certificate;
    }

    @Override
    @Transactional
    public Certificate update(Certificate certificate) {
        sessionFactory.getCurrentSession().update(certificate);
        return certificate;
    }

    @Override
    @Transactional
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate() >= 1;
    }
}
