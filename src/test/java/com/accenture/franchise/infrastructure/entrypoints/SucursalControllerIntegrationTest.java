package com.accenture.franchise.infrastructure.entrypoints;

import com.accenture.franchise.application.dto.BranchRequest;
import com.accenture.franchise.infrastructure.drivenadapters.jpa.JpaSucursalRepositorySpring;
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
class SucursalControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private JpaSucursalRepositorySpring jpaRepository;

    @BeforeEach
    void setUp() {
        jpaRepository.deleteAll();
    }

    @Test
    void createBranch_validRequest_returnsCreated() throws Exception {
        BranchRequest request = new BranchRequest("Sucursal Norte");

        mockMvc.perform(post("/api/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value("Sucursal Norte"));
    }

    @Test
    void createBranch_blankName_returns400WithErrors() throws Exception {
        BranchRequest request = new BranchRequest("");

        mockMvc.perform(post("/api/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").exists());
    }

    @Test
    void getAllBranches_returnsOkWithList() throws Exception {
        BranchRequest request = new BranchRequest("Sucursal A");
        mockMvc.perform(post("/api/branches")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        mockMvc.perform(get("/api/branches"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void getBranchById_existingId_returnsOk() throws Exception {
        BranchRequest request = new BranchRequest("Sucursal Sur");
        String body = mockMvc.perform(post("/api/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(body).get("id").asText();

        mockMvc.perform(get("/api/branches/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sucursal Sur"));
    }

    @Test
    void getBranchById_nonExistingId_returns404() throws Exception {
        mockMvc.perform(get("/api/branches/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void deleteBranch_existingId_returnsNoContent() throws Exception {
        BranchRequest request = new BranchRequest("Sucursal Eliminar");
        String body = mockMvc.perform(post("/api/branches")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andReturn().getResponse().getContentAsString();

        String id = objectMapper.readTree(body).get("id").asText();

        mockMvc.perform(delete("/api/branches/{id}", id))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/api/branches/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteBranch_nonExistingId_returns404() throws Exception {
        mockMvc.perform(delete("/api/branches/{id}", UUID.randomUUID()))
                .andExpect(status().isNotFound());
    }
}
