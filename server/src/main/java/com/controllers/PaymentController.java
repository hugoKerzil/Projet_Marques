package com.controllers;

import com.dtos.PaymentDto;
import com.services.PaymentService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
//@CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    /**
     * <p>Method for verifying that payment is valid</p>
     * @return ResponseEntity<Boolean>
     */
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePayment(@RequestBody PaymentDto request) {
        // classic Payment
        //boolean isValid =  paymentService.process(request);

        //Payment only true
        boolean isValid = true;

        if (isValid) {
            return ResponseEntity.ok(true);
        }
        return ResponseEntity.badRequest().body(false);
    }
}
