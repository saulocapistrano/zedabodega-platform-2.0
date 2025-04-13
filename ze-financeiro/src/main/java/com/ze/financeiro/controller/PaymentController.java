package com.ze.financeiro.controller;

import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import com.ze.financeiro.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@RequestBody @Valid PaymentRequestDTO dto) {
        PaymentResponseDTO response = paymentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
