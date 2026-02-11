package com.example.tickettriage.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketControllerTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    static {
        POSTGRES.start();
    }

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void createThenRetrieveTicket() throws Exception {
        String payload = """
                {
                  \"subject\": \"Integration subject\",
                  \"description\": \"Integration description that is sufficiently long for validation.\",
                  \"customerEmail\": \"int@example.com\"
                }
                """;

        var creation = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.status").value("NEW"))
                .andReturn();

        JsonNode created = objectMapper.readTree(creation.getResponse().getContentAsString());
        UUID id = UUID.fromString(created.get("id").asText());

        mockMvc.perform(get("/api/tickets/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.subject").value("Integration subject"));
    }

    @Test
    void transitionStatus() throws Exception {
        String payload = """
                {
                  \"subject\": \"Status change subject\",
                  \"description\": \"Status change description that is sufficiently long for validation.\",
                  \"customerEmail\": \"status@example.com\"
                }
                """;

        var creation = mockMvc.perform(post("/api/tickets")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
                .andExpect(status().isCreated())
                .andReturn();

        JsonNode created = objectMapper.readTree(creation.getResponse().getContentAsString());
        UUID id = UUID.fromString(created.get("id").asText());

        var statusPayload = """
                {
                  \"status\": \"TRIAGED\",
                  \"allowRollback\": false
                }
                """;

        mockMvc.perform(post("/api/tickets/" + id + "/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(statusPayload))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("TRIAGED"));

        var response = mockMvc.perform(get("/api/tickets?status=TRIAGED"))
                .andExpect(status().isOk())
                .andReturn();

        JsonNode list = objectMapper.readTree(response.getResponse().getContentAsString());
        assertThat(list.get("content").isArray()).isTrue();
    }
}
