package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.OrderConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.repository.CertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;
    private final OrderConverter orderConverter;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository,
                            CertificateRepository certificateRepository,
                            TagRepository tagRepository,
                            OrderConverter orderConverter) {
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
        this.orderConverter = orderConverter;
    }

    @Override
    public List<OrderDto> findAll() {
        List<Order> orders = orderRepository.findAll();
        List<OrderDto> orderDtos = new ArrayList<>(orders.size());
        populateCertificatesAndTags(orders).forEach(order -> orderDtos.add(orderConverter.convertEntityToDto(order)));
        return orderDtos;
    }

    @Override
    public List<OrderDto> findByUserId(Long id) {
        List<Order> orders = orderRepository.findOrdersByUserId(id);
        List<OrderDto> orderDtos = new ArrayList<>(orders.size());
        populateCertificatesAndTags(orders).forEach(order -> orderDtos.add(orderConverter.convertEntityToDto(order)));
        return orderDtos;
    }

    @Override
    public Optional<OrderDto> findOne(Long id) {
        Optional<Order> order = findOneOrder(id);
        return order.map(orderConverter::convertEntityToDto);
    }

    @Override
    public OrderDto create(OrderDto orderDto) throws ServiceException {
        orderDto.setUserId(orderDto.getUserId());
        Order order = orderConverter.convertDtoToEntity(orderDto);
        Order createdOrder = orderRepository.create(order);
        orderRepository.linkOrderWithCertificates(createdOrder);
        orderDto.setId(createdOrder.getId());
        orderDto.setPrice(createdOrder.getPrice());
        orderDto.setPurchaseDate(createdOrder.getPurchaseDate());
        return orderDto;
    }

    @Override
    public boolean delete(Long id) {
        orderRepository.unlinkOrderWithCertificates(id);
        return orderRepository.delete(id);
    }

    private List<Order> populateCertificatesAndTags(List<Order> orders) {
        orders.forEach(order -> {
            List<Certificate> certificates = certificateRepository.findByOrderId(order.getId());
            certificates.forEach(certificate ->
                    certificate.setTags(tagRepository.findByCertificateId(certificate.getId())));
            order.setCertificates(certificates);
        });
        return orders;
    }

    private Optional<Order> findOneOrder(Long id) {
        Optional<Order> order = orderRepository.findOne(id);
        if (order.isPresent()) {
            List<Certificate> certificates = certificateRepository.findByOrderId(order.get().getId());
            certificates.forEach(certificate ->
                    certificate.setTags(tagRepository.findByCertificateId(certificate.getId())));
            order.get().setCertificates(certificates);
            return order;
        } else {
            return Optional.empty();
        }
    }
}
