package com.services;

import com.dtos.PaymentDto;

public interface PaymentService {
    /**
     * Simule le traitement d'un paiement
     * @param request les infos de la carte et le montant
     * @return true si le paiement est accepté, false sinon
     */
    boolean process(PaymentDto request);
}