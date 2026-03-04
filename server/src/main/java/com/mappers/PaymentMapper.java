package com.mappers;

import com.dtos.PaymentDto;
import com.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    /**
     * <p>Method for converting a payment entity into a DTO</p>
     * @return PaymentDto
     */
    public PaymentDto toDto(Payment paiement) {
        if (paiement == null) return null;

        PaymentDto dto = new PaymentDto();
        dto.setAmount(paiement.getMontant());

        return dto;
    }

    /**
     * <p>Method for converting a DTO object into a payment entity</p>
     * @return Payment
     */
    public Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment paiement = new Payment();
        paiement.setMontant(dto.getAmount());
        paiement.setType("CB");
        paiement.setStatut("EN_ATTENTE");

        return paiement;
    }

}