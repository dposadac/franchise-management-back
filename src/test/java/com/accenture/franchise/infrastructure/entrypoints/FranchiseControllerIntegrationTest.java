package com.accenture.franchise.infrastructure.entrypoints;

import com.accenture.franchise.application.dto.FranchiseRequest;
import com.accenture.franchise.application.dto.UpdateFranchiseRequest;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaFranquiciaRepositorySpring;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class FranchiseControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private JpaFranquiciaRepositorySpring jpaRepository;

    @BeforeEach
    void setUp() {
        jpaRepository.deleteAll();
    }

    @Test
    void createFranchise_validRequest_returnsCreated() throws Exception {
        FranchiseRequest request = new FranchiseRequest("Test Franchise");

        mockMvc.perform(post("/api/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Test Franchise"));
    }

    @Test
    void createFranchise_blankName_returns400WithErrors() throws Exception {
        FranchiseRequest request = new FranchiseRequest("");

        mockMvc.perform(post("/api/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void getAllFranchises_returnsOkWithList() throws Exception {
        FranchiseRequest request = new FranchiseRequest("Franchise A");
        mockMvc.perform(post("/api/franchises")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(get("/api/franchises"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void getFranchiseById_existingId_returnsOk() throws Exception {
        FranchiseRequest request = new FranchiseRequest("My Franchise");
        String body = mockMvc.perform(post("/api/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(body).get("id").asText();

        mockMvc.perform(get("/api/franchises/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("My Franchise"));
    }

    @Test
    void getFranchiseById_nonExistingId_returns404() throws Exception {
        mockMvc.perform(get("/api/franchises/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void updateFranchise_existingId_returnsOk() throws Exception {
        FranchiseRequest createRequest = new FranchiseRequest("Original");
        String body = mockMvc.perform(post("/api/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(body).get("id").asText();

        UpdateFranchiseRequest updateRequest = new UpdateFranchiseRequest("Updated");
        mockMvc.perform(put("/api/franchises/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void updateFranchise_nonExistingId_returns404() throws Exception {
        UpdateFranchiseRequest request = new UpdateFranchiseRequest("Name");
        mockMvc.perform(put("/api/franchises/{id}", UUID.randomUUID())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFranchise_existingId_returnsNoContent() throws Exception {
        FranchiseRequest request = new FranchiseRequest("To Delete");
        String body = mockMvc.perform(post("/api/franchises")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(body).get("id").asText();

        mockMvc.perform(delete("/api/franchises/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/franchises/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFranchise_nonExistingId_returns404() throws Exception {
        mockMvc.perform(delete("/api/franchises/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
