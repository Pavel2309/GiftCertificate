package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.repository.CertificateRepository;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public List<Certificate> findAll() {
        List<Certificate> certificates = certificateRepository.findAll();
        return certificates;
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate.setCreateTime(LocalDateTime.now());
        certificate.setUpdateTime(LocalDateTime.now());
        Certificate createdCertificate = certificateRepository.create(certificate);
        return createdCertificate;
    }
}
