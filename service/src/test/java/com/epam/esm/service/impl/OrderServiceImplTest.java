package com.epam.esm.service.impl;

import com.epam.esm.converter.impl.OrderConverter;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.dto.OrderDto;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Order;
import com.epam.esm.model.entity.User;
import com.epam.esm.model.repository.OrderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.PagedModel;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private static OrderRepository orderRepository;

    @Mock
    private static OrderConverter orderConverter;

    @InjectMocks
    private OrderServiceImpl service;

    private Order order;
    private OrderDto orderDto;
    private PagedModel<Order> pagedModel;
    private PagedModel<OrderDto> pagedModelDto;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);
        user.setName("User Name");

        Certificate certificate = new Certificate();
        certificate.setId(1L);
        List<Certificate> certificates = new ArrayList<>();
        certificates.add(certificate);

        order = new Order();
        order.setId(1L);
        order.setUser(user);
        order.setPrice(BigDecimal.TEN);
        order.setPurchaseDate(Timestamp.valueOf(LocalDateTime.now()));
        order.setCertificates(certificates);

        orderDto = new OrderDto();
        orderDto.setId(1L);
        orderDto.setUserId(user.getId());
        orderDto.setPrice(order.getPrice());
        orderDto.setPurchaseDate(order.getPurchaseDate());
        List<Long> certificatesId = new ArrayList<>();
        certificatesId.add(certificate.getId());
        orderDto.setCertificatesId(certificatesId);

        List<Order> orders = new ArrayList<>();
        orders.add(order);

        List<OrderDto> orderDtos = new ArrayList<>();
        orderDtos.add(orderDto);

        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(10, 1, 1, 1);
        pagedModel = PagedModel.of(orders, metadata);
        pagedModelDto = PagedModel.of(orderDtos, metadata);
    }

    @AfterEach
    void tearDown() {
        order = null;
        orderDto = null;
        pagedModel = null;
        pagedModelDto = null;
    }

    @Test
    void findAll() {
        Mockito.when(orderRepository.findAll(anyMap())).thenReturn(pagedModel);
        Mockito.when(orderConverter.convertEntityToDto(any())).thenReturn(orderDto);
        PagedModel<OrderDto> actual = service.findAll(anyMap());
        Assertions.assertEquals(pagedModelDto, actual);
    }

    @Test
    void findByUserId() {
        Mockito.when(orderRepository.findOrdersByUserId(anyLong(), anyMap())).thenReturn(pagedModel);
        Mockito.when(orderConverter.convertEntityToDto(any())).thenReturn(orderDto);
        PagedModel<OrderDto> actual = service.findByUserId(anyLong(), anyMap());
        Assertions.assertEquals(pagedModelDto, actual);
    }

    @Test
    void findOne() {
        Mockito.when(orderRepository.findOne(anyLong())).thenReturn(Optional.ofNullable(order));
        Mockito.when(orderConverter.convertEntityToDto(any())).thenReturn(orderDto);
        Optional<OrderDto> actual = service.findOne(anyLong());
        Assertions.assertEquals(orderDto, actual.get());
    }

    @Test
    void create() throws ServiceException {
        Mockito.when(orderRepository.create(order)).thenReturn(order);
        Mockito.when(orderConverter.convertDtoToEntity(orderDto)).thenReturn(order);
        OrderDto actual = service.create(orderDto);
        Assertions.assertEquals(orderDto, actual);
    }

    @Test
    void delete() {
        Mockito.when(orderRepository.delete(1L)).thenReturn(true);
        boolean result = service.delete(1L);
        Assertions.assertTrue(result);
    }
}