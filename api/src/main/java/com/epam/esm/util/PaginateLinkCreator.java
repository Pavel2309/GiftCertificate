package com.epam.esm.util;

import com.epam.esm.controller.CertificateController;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class PaginateLinkCreator {

    private static final String PAGE = "page";
    private static final String FIRST_PAGE = "first page";
    private static final String PREVIOUS_PAGE = "previous page";
    private static final String NEXT_PAGE = "next page";
    private static final String LAST_PAGE = "last page";

    public List<Link> createPaginateLinks(PagedModel.PageMetadata metadata, Map<String, String> pageParameters) {
        List<Link> links = new ArrayList<>();
        links.add(linkTo(methodOn(CertificateController.class).getWithParameters(pageParameters)).withSelfRel());
        pageParameters.putIfAbsent(PAGE, String.valueOf(1));
        pageParameters.replace(PAGE, String.valueOf(1));
        links.add(linkTo(methodOn(CertificateController.class).getWithParameters(pageParameters)).withRel(FIRST_PAGE));
        if (metadata.getNumber() > 1 && metadata.getNumber() <= metadata.getTotalPages()) {
            pageParameters.replace(PAGE, String.valueOf(metadata.getNumber() - 1));
            links.add(linkTo(methodOn(CertificateController.class).getWithParameters(pageParameters)).withRel(PREVIOUS_PAGE));
        }
        if (metadata.getNumber() < metadata.getTotalPages()) {
            pageParameters.replace(PAGE, String.valueOf(metadata.getNumber() + 1));
            links.add(linkTo(methodOn(CertificateController.class).getWithParameters(pageParameters)).withRel(NEXT_PAGE));
        }
        pageParameters.replace(PAGE, String.valueOf(metadata.getTotalPages()));
        links.add(linkTo(methodOn(CertificateController.class).getWithParameters(pageParameters)).withRel(LAST_PAGE));
        return links;
    }
}
