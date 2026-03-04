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

    /**
     * Builder with dependency injection
     * Injection by constructor is preferred over @Autowired because:
     * - It makes dependencies mandatory
     * - It facilitates unit testing
     * - It enables immutability
     */
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    /**
     * {@inheritDoc}
     * This method verifies the user's payment information and returns true if everything is correct.
     */
    @Override
    public boolean process(PaymentDto request) {

        if (request.getCardNumber().startsWith("0")) {
            return false;
        }

        if (request.getCvv().length() != 3) {
            return false;
        }

        Payment paiement = new Payment();
        paiement.setAmount(request.getAmount());
        paiement.setType("CB");
        paiement.setStatus("VALIDÉ");

        // We must wait for the “Location” API to link the location_id received in the DTO.
        // paiement.setLocation(locationRepository.findById(request.getReservationId()).get());

        paymentRepository.save(paiement);

        return true;
    }
}