package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.repository.CertificateRepository;
import com.epam.esm.model.repository.OrderRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.orderRepository = orderRepository;
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = orderRepository.findAll();
        return populateCertificatesAndTags(orders);
    }

    @Override
    public List<Order> findByUserId(Long id) {
        List<Order> orders = orderRepository.findOrdersByUserId(id);
        return populateCertificatesAndTags(orders);
    }

    @Override
    public Optional<Order> findOne(Long id) {
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

    @Override
    public Order create(Order order) {
        Order createdOrder = orderRepository.create(order);
        orderRepository.linkOrderWithCertificates(createdOrder);
        return createdOrder;
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
}
