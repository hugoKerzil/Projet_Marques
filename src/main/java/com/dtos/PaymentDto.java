package com.dtos;

import lombok.Data;

@Data
public class PaymentDto {
    private String cardNumber;
    private String expiryDate;
    private String cvv;
    private double amount;
    private Integer reservationId;
}
