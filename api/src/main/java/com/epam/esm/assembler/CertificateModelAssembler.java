package com.epam.esm.assembler;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.model.entity.Certificate;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateModelAssembler implements RepresentationModelAssembler<Certificate, EntityModel<Certificate>> {
    @Override
    public EntityModel<Certificate> toModel(Certificate certificate) {
        return EntityModel.of(certificate,
                linkTo(methodOn(CertificateController.class).getOne(certificate.getId())).withSelfRel(),
                linkTo(methodOn(CertificateController.class).getWithParameters(null)).withRel("certificates"));
    }
}
