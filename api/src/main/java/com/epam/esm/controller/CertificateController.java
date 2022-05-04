package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * The REST API Certificate controller.
 */
@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {

    private final CertificateServiceImpl certificateService;

    public CertificateController(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

    /**
     * Gets one certificate with the specified id.
     *
     * @param id a certificate's id
     * @return a certificate object
     */
    @GetMapping("/{id}")
    public Certificate getOne(@PathVariable("id") Long id) {
        return certificateService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    /**
     * Gets a list of certificate with the specified parameters.
     *
     * @param parameters a map of request parameters including a tag title, search query and sort
     * @return a list of certificates
     */
    @GetMapping
    public List<Certificate> getWithParameters(@RequestParam Map<String, String> parameters) {
        List<Certificate> certificates = certificateService.findWithParameters(parameters);
        return certificates;
    }

    /**
     * Creates a new certificate
     *
     * @param certificate a certificate object in the JSON format
     * @return an object of a created certificate
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Certificate addCertificate(@RequestBody @Valid Certificate certificate) {
        return certificateService.create(certificate);
    }

    /**
     * Updates an existing certificate.
     *
     * @param id the certificate's id for update
     * @param certificate a certificate object in the JSON format containing fields to update
     * @return an updated certificate object
     */
    @PatchMapping("/{id}")
    public Certificate updateCertificate(@PathVariable("id") Long id,
                                         @RequestBody Certificate certificate) {
        try {
            return certificateService.update(id, certificate);
        } catch (ServiceException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    /**
     * Deletes a certificate with a specified id.
     *
     * @param id a certificate's id
     * @return whether a certificate was deleted successfully
     */
    @DeleteMapping("/{id}")
    public boolean deleteCertificate(@PathVariable("id") Long id) {
        return certificateService.delete(id);
    }
}
