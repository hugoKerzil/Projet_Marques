package com.services;

import com.dtos.PaymentDto;

public interface PaymentService {
    /**
     * Simulates payment processing
     * @param request card details and amount
     * @return true if payment is accepted, false otherwise
     */
    boolean process(PaymentDto request);
}