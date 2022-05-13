package com.epam.esm.converter.impl;

import com.epam.esm.converter.ObjectMapper;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.CertificateRepository;
import com.epam.esm.model.repository.UserRepository;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
public class OrderConverter implements ObjectMapper<Order, OrderDto> {

    private final UserRepository userRepository;
    private final CertificateRepository certificateRepository;

    public OrderConverter(UserRepository userRepository, CertificateRepository certificateRepository) {
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public OrderDto convertEntityToDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setUserId(order.getUser().getId());
        orderDto.setPrice(order.getPrice());
        orderDto.setPurchaseDate(order.getPurchaseDate());
        List<Long> certificatesId = new ArrayList<>(order.getCertificates().size());
        order.getCertificates().forEach(certificate -> {
            certificatesId.add(certificate.getId());
        });
        orderDto.setCertificatesId(certificatesId);
        return orderDto;
    }

    @Override
    public Order convertDtoToEntity(OrderDto orderDto) throws ServiceException {
        Order order = new Order();
        User user = userRepository.findOne(orderDto.getUserId()).orElseThrow(() ->
                new ServiceException("can't find a user with id: " + orderDto.getUserId())
        );
        order.setUser(user);
        order.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now()));
        List<Certificate> certificates = new ArrayList<>(orderDto.getCertificatesId().size());
        List<Long> certificatesId = orderDto.getCertificatesId();
        BigDecimal overallPrice = BigDecimal.ZERO;
        for (Long id : certificatesId) {
            Certificate certificate = certificateRepository.findOne(id).orElseThrow(() ->
                    new ServiceException("can't find a certificate with the id " + id));
            certificates.add(certificate);
            overallPrice = overallPrice.add(certificate.getPrice());
        }
        order.setCertificates(certificates);
        order.setPrice(overallPrice);
        return order;
    }
}
