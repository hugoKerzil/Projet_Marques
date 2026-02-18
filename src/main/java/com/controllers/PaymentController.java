package com.controllers;

import com.dtos.PaymentDto;
import com.services.impl.PaymentServiceImpl;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePayment(@RequestBody PaymentDto request) {
        boolean isValid =  paymentService.process(request);

        if (isValid) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
