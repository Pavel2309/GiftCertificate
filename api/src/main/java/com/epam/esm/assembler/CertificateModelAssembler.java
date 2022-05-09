package com.epam.esm.assembler;

import com.epam.esm.controller.CertificateController;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.util.PaginateLinkCreator;
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

    private final PaginateLinkCreator paginateLinkCreator;

    public CertificateModelAssembler(PaginateLinkCreator paginateLinkCreator) {
        this.paginateLinkCreator = paginateLinkCreator;
    }

    @Override
    public EntityModel<Certificate> toModel(Certificate certificate) {
        return EntityModel.of(certificate,
                linkTo(methodOn(CertificateController.class).getOne(certificate.getId())).withSelfRel(),
                linkTo(methodOn(CertificateController.class).getWithParameters(null)).withRel("certificates"));
    }

    public PagedModel<EntityModel<Certificate>> toCollectionModel(PagedModel<Certificate> certificates, Map<String, String> parameters) {
        List<EntityModel<Certificate>> entityModelList = new ArrayList<>();
        certificates.forEach(certificate -> {
            entityModelList.add(toModel(certificate));
        });
        PagedModel.PageMetadata metadata = certificates.getMetadata();
        List<Link> links = paginateLinkCreator.createPaginateLinks(metadata, parameters);
        return PagedModel.of(entityModelList, certificates.getMetadata(), links);
    }
}
