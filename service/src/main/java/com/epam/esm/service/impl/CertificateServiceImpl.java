package com.epam.esm.service.impl;

import com.epam.esm.exception.ServiceException;
import com.epam.esm.model.entity.Certificate;
import com.epam.esm.model.entity.Tag;
import com.epam.esm.model.repository.CertificateRepository;
import com.epam.esm.model.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

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
    public Optional<Certificate> findOne(Long id) {
        return certificateRepository.findOne(id);
    }

    @Override
    public List<Certificate> findByOrderId(Long id) {
        return certificateRepository.findByOrderId(id);
    }

    @Override
    public List<Certificate> findWithParameters(Map<String, String> parameters) {
        List<Certificate> certificates = certificateRepository.findAllWithParameters(parameters);
        certificates.forEach(certificate ->
                certificate.setTags(tagRepository.findByCertificateId(certificate.getId())));
        return certificates;
    }

    @Override
    public Certificate create(Certificate certificate) {
        certificate.setCreateDate(LocalDateTime.now());
        certificate.setUpdateDate(LocalDateTime.now());
        if (certificate.getTags() != null) {
            Set<Tag> tags = saveTags(certificate.getTags());
            certificate.setTags(tags);
        }
        return certificateRepository.create(certificate);
    }

    @Override
    public Certificate update(long id, Certificate certificate) throws ServiceException {
        Certificate certificateForUpdate = findOne(id).orElseThrow(ServiceException::new);
        certificateForUpdate.setUpdateDate(LocalDateTime.now());
        Optional.ofNullable(certificate.getTitle()).ifPresent(certificateForUpdate::setTitle);
        Optional.ofNullable(certificate.getDescription()).ifPresent(certificateForUpdate::setDescription);
        Optional.ofNullable(certificate.getPrice()).ifPresent(certificateForUpdate::setPrice);
        Optional.ofNullable(certificate.getDuration()).ifPresent(certificateForUpdate::setDuration);
        if (certificate.getTags() != null) {
            //certificateRepository.unlinkCertificateWithTags(certificateForUpdate.getId());
            Set<Tag> tags = saveTags(certificate.getTags());
            certificateForUpdate.setTags(tags);
        }
        certificateRepository.update(certificateForUpdate);
        return certificateForUpdate;
    }

    @Override
    public boolean delete(Long id) {
        return certificateRepository.delete(id);
    }

    private Set<Tag> saveTags(Set<Tag> tags) {
        Set<Tag> savedTags = new HashSet<>();
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
