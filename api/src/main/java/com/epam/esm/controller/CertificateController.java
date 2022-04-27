package com.epam.esm.controller;

import com.epam.esm.exception.ResourceNotFoundException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {

    private final CertificateServiceImpl certificateService;

    public CertificateController(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

//    @GetMapping
//    public List<Certificate> getAll() {
//        return certificateService.findAll();
//    }

    @GetMapping("/{id}")
    public Certificate getOne(@PathVariable("id") Long id) {
        return certificateService.findOne(id)
                .orElseThrow(() -> new ResourceNotFoundException(id));
    }

    @GetMapping
    public List<Certificate> getWithParameters(@RequestParam Map<String, String> parameters) {
        return certificateService.findWithParameters(parameters);
    }

    @PostMapping
    public Certificate addCertificate(@RequestBody Certificate certificate) {
        return certificateService.create(certificate);
    }

    @PatchMapping("/{id}")
    public Certificate updateCertificate(@PathVariable("id") Long id,
                                         @RequestBody Certificate certificate) {
        try {
            return certificateService.update(id, certificate);
        } catch (ServiceException e) {
            throw new ResourceNotFoundException(id);
        }
    }

    @DeleteMapping("/{id}")
    public boolean deleteCertificate(@PathVariable("id") Long id) {
        return certificateService.delete(id);
    }
}
