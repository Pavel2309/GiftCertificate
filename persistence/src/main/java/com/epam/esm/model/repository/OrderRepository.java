package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Order;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Map;

/**
 * The OrderRepository interface describes data access functionality for the Order entity.
 */
public interface OrderRepository extends CommonRepository<Order, Long> {

    /**
     * Finds all orders with the specified user id and page parameters including page and size.
     *
     * @param id a user's id
     * @param parameters page number and size parameters for pagination functionality
     * @return a list of order objects
     */
    PagedModel<Order> findOrdersByUserId(Long id, Map<String, String> parameters);

}
