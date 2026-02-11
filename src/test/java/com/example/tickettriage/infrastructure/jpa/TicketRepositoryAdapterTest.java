package com.example.tickettriage.infrastructure.jpa;

import com.example.tickettriage.application.support.PagedResult;
import com.example.tickettriage.domain.Ticket;
import com.example.tickettriage.domain.TicketStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DataJpaTest
@Import(TicketRepositoryAdapter.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TicketRepositoryAdapterTest {

    @Container
    private static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine");

    static {
        POSTGRES.start();
    }

    @Autowired
    private TicketRepositoryAdapter ticketRepository;

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @Test
    void saveAndFindById() {
        Ticket ticket = Ticket.createNew(UUID.randomUUID(), "Example subject",
                "Example description that is long enough", "user@example.com", Instant.now());

        Ticket saved = ticketRepository.save(ticket);
        Ticket fetched = ticketRepository.findById(saved.getId()).orElseThrow();

        assertThat(fetched.getId()).isEqualTo(saved.getId());
        assertThat(fetched.getSubject()).isEqualTo("Example subject");
    }

    @Test
    void findAllSupportsPaginationAndFilter() {
        Ticket ticket = Ticket.createNew(UUID.randomUUID(), "Another subject",
                "Another description that is long enough", "user2@example.com", Instant.now());
        ticketRepository.save(ticket);

        PagedResult<Ticket> all = ticketRepository.findAll(null, 0, 10);
        assertThat(all.content()).hasSizeGreaterThanOrEqualTo(1);

        PagedResult<Ticket> filtered = ticketRepository.findAll(TicketStatus.NEW, 0, 10);
        assertThat(filtered.content()).extracting(Ticket::getStatus).contains(TicketStatus.NEW);
    }
}
