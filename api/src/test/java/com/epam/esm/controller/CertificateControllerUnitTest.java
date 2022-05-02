package com.epam.esm.controller;

import com.epam.esm.model.entity.Certificate;
import com.epam.esm.service.impl.CertificateServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CertificateControllerUnitTest {

    @Mock
    private CertificateServiceImpl certificateService;
    private Certificate certificate;
    private List<Certificate> certificateList;

    @Autowired
    @InjectMocks
    private CertificateController certificateController;

    @Autowired
    private MockMvc mockMvc;

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    void setUp() {
        certificate = new Certificate();
        certificate.setId(1L);
        certificateList = new ArrayList<>();
        certificateList.add(certificate);
        mockMvc = MockMvcBuilders.standaloneSetup(certificateController).build();
    }

    @AfterEach
    void tearDown() {
        certificate = null;
    }

    @Test
    void getOne() throws Exception {
        when(certificateService.findOne(certificate.getId())).thenReturn(Optional.ofNullable(certificate));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(certificate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void getWithParameters() throws Exception {
        when(certificateService.findWithParameters(anyMap())).thenReturn(certificateList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(certificate)))
                .andDo(MockMvcResultHandlers.print());
        verify(certificateService).findWithParameters(anyMap());
        verify(certificateService, times(1)).findWithParameters(anyMap());
    }

    @Test
    void addCertificate() throws Exception {
        when(certificateService.create(any())).thenReturn(certificate);
        mockMvc.perform(post("/api/v1/certificates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(certificate)))
                .andExpect(status().isCreated());
        verify(certificateService, times(1)).create(any());
    }

    @Test
    void updateCertificate() throws Exception {
        when(certificateService.update(anyLong(), any())).thenReturn(certificate);
        mockMvc.perform(patch("/api/v1/certificates/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(certificate)))
                .andExpect(status().isOk());
        verify(certificateService, times(1)).update(anyLong(), any());
    }

    @Test
    void deleteCertificate() throws Exception {
        when(certificateService.delete(certificate.getId())).thenReturn(true);
        mockMvc.perform(delete("/api/v1/certificates/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(certificate)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}