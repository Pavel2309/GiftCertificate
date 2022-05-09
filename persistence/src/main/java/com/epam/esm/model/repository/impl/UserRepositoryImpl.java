package com.epam.esm.model.repository.impl;

import com.epam.esm.model.entity.User;
import com.epam.esm.model.query.QueryPaginator;
import com.epam.esm.model.repository.UserRepository;
import org.hibernate.SessionFactory;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.epam.esm.model.query.UserQueryHolder.HQL_FIND_ALL;


@Repository
public class UserRepositoryImpl implements UserRepository {

    private final SessionFactory sessionFactory;
    private final QueryPaginator queryPaginator;

    public UserRepositoryImpl(SessionFactory sessionFactory, QueryPaginator queryPaginator) {
        this.sessionFactory = sessionFactory;
        this.queryPaginator = queryPaginator;
    }

    @Override
    public PagedModel<User> findAll(Map<String, String> parameters) {
        Query query = sessionFactory.getCurrentSession().createQuery(HQL_FIND_ALL);
        PagedModel.PageMetadata metadata = queryPaginator.paginateQuery(query, parameters);
        List<User> users = query.getResultList();
        return PagedModel.of(users, metadata);
    }

    @Override
    public Optional<User> findOne(Long id) {
        User user = sessionFactory.getCurrentSession().get(User.class, id);
        return Optional.ofNullable(user);
    }

    @Override
    public User create(User user) {
        throw new UnsupportedOperationException("create user functionality is not supported");
    }

    @Override
    public User update(User user) {
        throw new UnsupportedOperationException("update user functionality is not supported");

    }

    @Override
    public boolean delete(Long id) {
        throw new UnsupportedOperationException("delete user functionality is not supported");
    }
}
