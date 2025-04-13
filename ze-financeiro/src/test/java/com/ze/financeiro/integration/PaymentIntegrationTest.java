package com.ze.financeiro.integration;

import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class PaymentIntegrationTest extends BaseIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void shouldCreatePaymentSuccessfully() {
        PaymentRequestDTO request = new PaymentRequestDTO(
                UUID.randomUUID(),
                new BigDecimal("199.99"),
                "CREDIT_CARD"
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<PaymentRequestDTO> httpEntity = new HttpEntity<>(request, headers);

        ResponseEntity<PaymentResponseDTO> response = restTemplate.postForEntity(
                "/api/v1/payments", httpEntity, PaymentResponseDTO.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().amount()).isEqualTo(request.amount());
        assertThat(response.getBody().orderId()).isEqualTo(request.orderId());
        assertThat(response.getBody().method().name()).isEqualTo(request.method());

    }
}
