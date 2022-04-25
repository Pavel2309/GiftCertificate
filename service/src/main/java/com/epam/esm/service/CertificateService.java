package com.epam.esm.service;

import com.epam.esm.model.entity.Certificate;

import java.util.List;

public interface CertificateService {

    List<Certificate> findAll();

    Certificate create(Certificate certificate);

}
