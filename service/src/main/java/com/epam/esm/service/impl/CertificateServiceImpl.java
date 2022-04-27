package com.epam.esm.service.impl;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.CertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final TagRepository tagRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Certificate> findAll() {
        List<Certificate> certificates = certificateRepository.findAll();
        certificates.forEach(certificate ->
                certificate.setTags(tagRepository.findByCertificateId(certificate.getId())));
        return certificates;
    }

    @Override
    public Optional<Certificate> findOne(Long id) {
        Optional<Certificate> certificate = certificateRepository.findOne(id);
        if (certificate.isPresent()) {
            certificate.get().setTags(tagRepository.findByCertificateId(certificate.get().getId()));
            return certificate;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate.setCreateTime(LocalDateTime.now());
        certificate.setUpdateTime(LocalDateTime.now());
        Certificate createdCertificate = certificateRepository.create(certificate);
        if (certificate.getTags() != null) {
            List<Tag> tags = saveTags(certificate.getTags());
            createdCertificate.setTags(tags);
            certificateRepository.linkCertificateWithTags(createdCertificate);
        }
        return createdCertificate;
    }

    @Override
    public Certificate update(long id, Certificate certificate) throws ServiceException {

        Certificate certificateForUpdate = findOne(id).orElseThrow(ServiceException::new);

        certificateForUpdate.setUpdateTime(LocalDateTime.now());

        Optional.ofNullable(certificate.getTitle()).ifPresent(certificateForUpdate::setTitle);
        Optional.ofNullable(certificate.getDescription()).ifPresent(certificateForUpdate::setDescription);
        Optional.ofNullable(certificate.getPrice()).ifPresent(certificateForUpdate::setPrice);
        Optional.ofNullable(certificate.getDuration()).ifPresent(certificateForUpdate::setDuration);

        if (certificate.getTags() != null) {
            certificateRepository.unlinkCertificateWithTags(certificateForUpdate);
            List<Tag> tags = saveTags(certificate.getTags());
            certificateForUpdate.setTags(tags);
            certificateRepository.linkCertificateWithTags(certificateForUpdate);
        }
        certificateRepository.update(certificateForUpdate);

        return certificateForUpdate;
    }

    @Override
    public boolean delete(Long id) {
        return certificateRepository.delete(id);
    }

    private List<Tag> saveTags(List<Tag> tags) {
        List<Tag> savedTags = new ArrayList<>();
        tags.forEach(tag -> {
            Optional<Tag> currentTag = tagRepository.findByTitle(tag.getTitle());
            if (currentTag.isEmpty()) {
                savedTags.add(tagRepository.create(tag));
            } else {
                savedTags.add(currentTag.get());
            }
        });
        return savedTags;
    }
}
