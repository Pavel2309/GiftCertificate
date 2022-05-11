package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.repository.CertificateRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.PagedModel;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;

class CertificateServiceImplTest {

    @Mock
    private static CertificateRepository certificateRepository;

    @Autowired
    @InjectMocks
    private CertificateServiceImpl service;

    private Certificate certificateOne;
    private Certificate certificateTwo;
    private List<Certificate> certificateList;
    private PagedModel<Certificate> pagedModel;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        certificateOne = new Certificate();
        certificateTwo = new Certificate();
        certificateOne.setId(1L);
        certificateTwo.setId(2L);
        certificateList = new ArrayList<>();
        certificateList.add(certificateOne);
        certificateList.add(certificateTwo);
        PagedModel.PageMetadata metadata = new PagedModel.PageMetadata(10, 1, 2, 1);
        pagedModel = PagedModel.of(certificateList, metadata);
    }

    @AfterEach
    void tearDown() {
        certificateOne = null;
        certificateTwo = null;
        certificateList = null;
        pagedModel = null;
    }

    @Test
    void findOne() {
        Mockito.when(certificateRepository.findOne(1L)).thenReturn(Optional.of(certificateOne));
        Optional<Certificate> certificate = service.findOne(1L);
        Assertions.assertEquals(certificateOne, certificate.get());
    }

    @Test
    void findWithParameters() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tag_title", "titleOne");
        parameters.put("search_query", "query");
        parameters.put("sort", "title(asc)");
        Mockito.when(certificateRepository.findAllWithParameters(anyMap())).thenReturn(pagedModel);
        List<Certificate> certificates = service.findWithParameters(parameters).getContent().stream().toList();
        Assertions.assertEquals(certificateList, certificates);
    }

    @Test
    void create() {
        Mockito.when(certificateRepository.create(any())).thenReturn(certificateOne);
        Certificate createdCertificate = service.create(certificateOne);
        Assertions.assertEquals(certificateOne, createdCertificate);
    }

    @Test
    void update() throws ServiceException {
        Mockito.when(certificateRepository.update(any())).thenReturn(certificateOne);
        Mockito.when(certificateRepository.findOne(any())).thenReturn(Optional.of(certificateOne));
        Certificate updatedCertificate = service.update(1L, certificateOne);
        Assertions.assertEquals(certificateOne, updatedCertificate);
    }

    @Test
    void delete() {
        Mockito.when(certificateRepository.delete(1L)).thenReturn(true);
        boolean result = service.delete(1L);
        Assertions.assertTrue(result);
    }
}