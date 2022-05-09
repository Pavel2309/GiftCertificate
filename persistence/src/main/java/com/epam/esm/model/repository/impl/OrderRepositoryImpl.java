package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.Order;
import com.epam.esm.model.query.QueryPaginator;
import com.epam.esm.model.repository.OrderRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.model.query.OrderQueryHolder.*;

@Repository
public class OrderRepositoryImpl implements OrderRepository {

    private final SessionFactory sessionFactory;
    private final QueryPaginator queryPaginator;

    @Autowired
    public OrderRepositoryImpl(SessionFactory sessionFactory,
                               QueryPaginator queryPaginator) {
        this.sessionFactory = sessionFactory;
        this.queryPaginator = queryPaginator;
    }

    @Override
    public PagedModel<Order> findAll(Map<String, String> parameters) {
        Query query = sessionFactory.getCurrentSession().createQuery(HQL_FIND_ALL);
        PagedModel.PageMetadata metadata = queryPaginator.paginateQuery(query, parameters);
        List<Order> orders = query.getResultList();
        return PagedModel.of(orders, metadata);
    }

    @Override
    public Optional<Order> findOne(Long id) {
        Order order = sessionFactory.getCurrentSession().get(Order.class, id);
        return Optional.ofNullable(order);
    }

    @Override
    public PagedModel<Order> findOrdersByUserId(Long id, Map<String, String> parameters) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_FIND_BY_USER_ID);
        query.setParameter("id", id);
        PagedModel.PageMetadata metadata = queryPaginator.paginateQuery(query, parameters);
        List<Order> orders = query.getResultList();
        return PagedModel.of(orders, metadata);
    }

    @Override
    @Transactional
    public Order create(Order order) {
        sessionFactory.getCurrentSession().save(order);
        return order;
    }

    @Override
    public Order update(Order order) {
        throw new UnsupportedOperationException("update order functionality is not supported");
    }

    @Override
    public boolean delete(Long id) {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery(HQL_DELETE_BY_ID);
        query.setParameter("id", id);
        return query.executeUpdate() >= 1;
    }
}
