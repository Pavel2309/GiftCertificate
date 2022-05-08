package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Certificate;

import java.util.List;
import java.util.Map;

/**
 * The CertificateRepository interface describes data access functionality for the Certificate entity.
 */
public interface CertificateRepository extends CommonRepository<Certificate, Long> {

    /**
     * Finds all certificates with specified parameters such as search query, tags and sort.
     *
     * @param parameters a map of request parameters
     * @return a list of certificate objects
     */
    List<Certificate> findAllWithParameters(Map<String, String> parameters);

    /**
     * Finds all certificates with the specified order id.
     *
     * @param id a order's id
     * @return a list of certificate objects
     */
    List<Certificate> findByOrderId(Long id);
}
