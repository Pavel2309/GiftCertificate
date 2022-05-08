package com.epam.esm.assembler;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.model.entity.Certificate;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CertificateModelAssembler implements RepresentationModelAssembler<Certificate, EntityModel<Certificate>> {
    @Override
    public EntityModel<Certificate> toModel(Certificate certificate) {
        return EntityModel.of(certificate,
                linkTo(methodOn(CertificateController.class).getOne(certificate.getId())).withSelfRel());
    }

    public PagedModel<EntityModel<Certificate>> toCollectionModel(PagedModel<Certificate> certificates, Map<String, String> parameters) {
        List<EntityModel<Certificate>> entityModelList = new ArrayList<>();
        certificates.forEach(certificate -> {
            entityModelList.add(toModel(certificate));
        });
        PagedModel.PageMetadata metadata = certificates.getMetadata();
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(CertificateController.class).getWithParameters(parameters)).withSelfRel());
        parameters.putIfAbsent("page", String.valueOf(1));
        parameters.replace("page", String.valueOf(1));
        links.add(linkTo(methodOn(CertificateController.class).getWithParameters(parameters)).withRel("first page"));
        if (metadata.getNumber() > 1) {
            parameters.replace("page", String.valueOf(metadata.getNumber() - 1));
            links.add(linkTo(methodOn(CertificateController.class).getWithParameters(parameters)).withRel("previous page"));
        }
        if (metadata.getNumber() < metadata.getTotalPages()) {
            parameters.replace("page", String.valueOf(metadata.getNumber() + 1));
            links.add(linkTo(methodOn(CertificateController.class).getWithParameters(parameters)).withRel("next page"));
        }
        parameters.replace("page", String.valueOf(metadata.getTotalPages()));
        links.add(linkTo(methodOn(CertificateController.class).getWithParameters(parameters)).withRel("last page"));
        return PagedModel.of(entityModelList, certificates.getMetadata(), links);
    }
}
