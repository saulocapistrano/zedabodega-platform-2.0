package com.ze.financeiro.controller;

import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import com.ze.financeiro.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
@Tag(name = "Payments", description = "CRUD operations for managing payments")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Create a new payment")
    @PostMapping
    public ResponseEntity<PaymentResponseDTO> create(@RequestBody @Valid PaymentRequestDTO dto) {
        PaymentResponseDTO response = paymentService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get payment by ID")
    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> findById(@PathVariable UUID id) {
        PaymentResponseDTO response = paymentService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get all payments")
    @GetMapping
    public ResponseEntity<List<PaymentResponseDTO>> findAll() {
        List<PaymentResponseDTO> payments = paymentService.findAll();
        return ResponseEntity.ok(payments);
    }

    @Operation(summary = "Update payment by ID")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDTO> update(
            @PathVariable UUID id,
            @RequestBody @Valid PaymentRequestDTO dto
    ) {
        PaymentResponseDTO updated = paymentService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Delete payment by ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
