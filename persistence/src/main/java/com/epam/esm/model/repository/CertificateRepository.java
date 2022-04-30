package com.epam.esm.model.repository;

import com.epam.esm.model.entity.Certificate;

import java.util.List;
import java.util.Map;

/**
 * The CertificateRepository interface describes data access functionality for the Certificate entity.
 */
public interface CertificateRepository extends CommonRepository<Certificate, Long> {
    /**
     * Establishes a link between a provided certificate and its corresponded tags
     *
     * @param certificate a certificate object
     * @return whether a certificate and its corresponded tags are linked successfully
     */
    boolean linkCertificateWithTags(Certificate certificate);

    /**
     * Removes a link between a provided certificate and its corresponded tags
     *
     * @param certificate a certificate object
     * @return whether certificate's tags were successfully removed from the certificate
     */
    boolean unlinkCertificateWithTags(Certificate certificate);

    /**
     * Finds all certificates with specified parameters such as search query, tags and sort.
     *
     * @param parameters a map of request parameters
     * @return a list of certificate objects.
     */
    List<Certificate> findAllWithParameters(Map<String, String> parameters);
}
