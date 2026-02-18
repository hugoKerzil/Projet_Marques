package com.mappers;

import com.dtos.PaymentDto;
import com.entities.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    /**
     * Transforme le DTO reçu du Front en Entité pour la base SQL
     */
    public Payment toEntity(PaymentDto dto) {
        if (dto == null) return null;

        Payment paiement = new Payment();
        paiement.setMontant(dto.getAmount());
        paiement.setType("CB");
        paiement.setStatut("EN_ATTENTE");

        return paiement;
    }

    /**
     * Transforme l'Entité SQL en DTO pour répondre au Front
     */
    public PaymentDto toDto(Payment paiement) {
        if (paiement == null) return null;

        PaymentDto dto = new PaymentDto();
        dto.setAmount(paiement.getMontant());

        return dto;
    }
}