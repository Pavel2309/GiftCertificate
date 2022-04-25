package com.epam.esm.controller;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.impl.CertificateServiceImpl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates")
public class CertificateController {

    private final CertificateServiceImpl certificateService;

    public CertificateController(CertificateServiceImpl certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping
    public List<Certificate> getAll() {
        List<Certificate> certificates = certificateService.findAll();
        return certificates;
    }
}
