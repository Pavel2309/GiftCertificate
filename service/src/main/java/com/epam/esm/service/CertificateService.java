package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Certificate;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The Certificate service interface.
 */
public interface CertificateService {
    /**
     * Finds all certificates with specified parameters such as search query, tags and sort.
     *
     * @param parameters a map of request parameters
     * @return a list of certificate objects
     */
    PagedModel<Certificate> findWithParameters(Map<String, String> parameters);

    /**
     * Finds one certificate with the specified id.
     *
     * @param id a certificate's id
     * @return an optional object of a certificate
     */
    Optional<Certificate> findOne(Long id);

    List<Certificate> findByOrderId(Long id);

    /**
     * Creates a new certificate.
     *
     * @param certificate a certificate object
     * @return a created certificate
     */
    Certificate create(Certificate certificate);

    /**
     * Updates an existing certificates with the specified id.
     *
     * @param id a certificate's id for update
     * @param certificate a certificate object to update
     * @return an updated certificate object
     * @throws ServiceException if a certificate with the provided id was not found.
     */
    Certificate update(long id, Certificate certificate) throws ServiceException;

    /**
     * Deletes a certificate with the specified id.
     *
     * @param id a certificate's id
     * @return whether a certificate was deleted successfully
     */
    boolean delete(Long id);
}
