package com.services.impl;

import com.dtos.PaymentDto;
import com.entities.Payment;
import com.repositories.PaymentRepository;
import com.services.PaymentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("paymentService")
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public boolean process(PaymentDto request) {

        if (request.getCardNumber().startsWith("0")) {
            return false;
        }

        if (request.getCvv().length() != 3) {
            return false;
        }

        Payment paiement = new Payment();
        paiement.setMontant(request.getAmount());
        paiement.setType("CB");
        paiement.setStatut("VALIDÉ");

        // On doit attendre l'api "Location" pour lier la location_id reçue dans le DTO
        // paiement.setLocation(locationRepository.findById(request.getReservationId()).get());

        paymentRepository.save(paiement);

        return true;
    }
}