package com.epam.esm.controller;

import com.epam.esm.assembler.CertificateModelAssembler;
import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

/**
 * The REST API Certificate controller.
 */
@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {

    private final CertificateServiceImpl certificateService;
    private final CertificateModelAssembler assembler;

    public CertificateController(CertificateServiceImpl certificateService, CertificateModelAssembler assembler) {
        this.certificateService = certificateService;
        this.assembler = assembler;
    }

    /**
     * Gets one certificate with the specified id.
     *
     * @param id a certificate's id
     * @return an entity model object of a certificate
     */
    @GetMapping("/{id}")
    public EntityModel<Certificate> getOne(@PathVariable("id") Long id) {
        Certificate certificate = certificateService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
        return assembler.toModel(certificate);
    }

    /**
     * Gets a list of certificates with the specified order id.
     *
     * @param id an order's id
     * @param parameters a page and size parameters
     * @return a paged model object certificates
     */
    @GetMapping("/orders/{id}")
    public PagedModel<EntityModel<Certificate>> getByOrderId(@PathVariable("id") Long id, @RequestParam Map<String, String> parameters) {
        PagedModel<Certificate> certificates = certificateService.findByOrderId(id, parameters);
        return assembler.toCollectionModel(certificates, parameters);
    }

    /**
     * Gets a list of certificate with the specified parameters.
     *
     * @param parameters a map of request parameters including a tag title, search query, sort, page and size
     * @return a paged model object certificates
     */
    @GetMapping
    public PagedModel<EntityModel<Certificate>> getWithParameters(@RequestParam Map<String, String> parameters) {
        PagedModel<Certificate> certificates = certificateService.findWithParameters(parameters);
        return assembler.toCollectionModel(certificates, parameters);
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
