package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Certificate;

import java.util.List;
import java.util.Map;

public interface CertificateRepository extends CommonRepository<Certificate, Long> {

    boolean linkCertificateWithTags(Certificate certificate);

    boolean unlinkCertificateWithTags(Certificate certificate);

    List<Certificate> findAllWithParameters(Map<String, String> parameters);

}
