package com.epam.esm.model.repository.impl;

import com.epam.esm.config.DataIntegrationConfig;
import com.epam.esm.model.entity.Certificate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {DataIntegrationConfig.class})
@ActiveProfiles("development")
class CertificateRepositoryImplTest {

    private final CertificateRepositoryImpl certificateRepository;
    private final TagRepositoryImpl tagRepository;

    Certificate certificateOne;

    @Autowired
    public CertificateRepositoryImplTest(CertificateRepositoryImpl certificateRepository, TagRepositoryImpl tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @BeforeEach
    void setUp() {
        certificateOne = new Certificate();
        certificateOne.setId(1L);
        certificateOne.setTitle("One");
        certificateOne.setDescription("Description one");
    }

    @Test
    void findAllTest() {
        List<Certificate> certificates = certificateRepository.findAll();
        Assertions.assertTrue(certificates.size() > 0);
    }

    @Test
    void findAllWithParametersTest() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("tag_title", "tag_1");
        parameters.put("search_query", "Title_1");
        parameters.put("sort", "title(asc)");
        List<Certificate> certificates = certificateRepository.findAllWithParameters(parameters);
        assertEquals(1, certificates.size());

    }

    @Test
    void findOneTest() {
        Optional<Certificate> certificate = certificateRepository.findOne(1L);
        Assertions.assertTrue(certificate.isPresent());
    }

    @Test
    void createTest() {
        Certificate certificate = new Certificate();
        certificate.setTitle("One");
        certificate.setDescription("Description one");
        certificate.setPrice(BigDecimal.valueOf(123));
        certificate.setDuration(11);
        certificate.setCreateTime(LocalDateTime.now());
        certificate.setUpdateTime(LocalDateTime.now());

        Certificate createdCertificate = certificateRepository.create(certificate);
        Assertions.assertEquals(certificate, createdCertificate);
    }

    @Test
    void updateTest() {
        Certificate certificate = new Certificate();
        certificate.setTitle("UPDATED Title_1");
        certificate.setDescription("Description_1");
        certificate.setPrice(BigDecimal.valueOf(111));
        certificate.setDuration(11);
        certificate.setCreateTime(LocalDateTime.parse("2022-04-28T18:11:13.825125546"));
        certificate.setUpdateTime(LocalDateTime.parse("2022-04-28T18:11:13.825125546"));
        certificate.setId(1L);

        Certificate updatedCertificate = certificateRepository.update(certificate);
        Assertions.assertEquals(certificate.getId(), updatedCertificate.getId());
    }

    @Test
    void deleteTest() {
        boolean result = certificateRepository.delete(1L);
        Assertions.assertTrue(result);
    }

}