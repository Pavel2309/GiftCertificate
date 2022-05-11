package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.OrderConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.repository.CertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public PagedModel<OrderDto> findAll(Map<String, String> parameters) {
        PagedModel<Order> orders = orderRepository.findAll(parameters);
        List<OrderDto> orderDtos = orders.getContent().stream().map(orderConverter::convertEntityToDto).toList();
        return PagedModel.of(orderDtos, orders.getMetadata());
    }

    @Override
    public PagedModel<OrderDto> findByUserId(Long id, Map<String, String> parameters) {
        PagedModel<Order> orders = orderRepository.findOrdersByUserId(id, parameters);
        List<OrderDto> orderDtos = orders.getContent().stream().map(orderConverter::convertEntityToDto).toList();
        return PagedModel.of(orderDtos, orders.getMetadata());
    }

    @Override
    public Optional<OrderDto> findOne(Long id) {
        Optional<Order> order = orderRepository.findOne(id);
        return order.map(orderConverter::convertEntityToDto);
    }

    @Override
    public OrderDto create(OrderDto orderDto) throws ServiceException {
        orderDto.setUserId(orderDto.getUserId());
        Order order = orderConverter.convertDtoToEntity(orderDto);
        Order createdOrder = orderRepository.create(order);
        orderDto.setId(createdOrder.getId());
        orderDto.setPrice(createdOrder.getPrice());
        orderDto.setPurchaseDate(createdOrder.getPurchaseDate());
        return orderDto;
    }

    @Override
    public boolean delete(Long id) {
        return orderRepository.delete(id);
    }
}
