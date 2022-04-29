package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Certificate;

import java.util.List;
import java.util.Optional;

public interface CertificateService {

    List<Certificate> findAll();

    Optional<Certificate> findOne(Long id);

    Certificate create(Certificate certificate);

    Certificate update(long id, Certificate certificate) throws ServiceException;

    boolean delete(Long id);
}
