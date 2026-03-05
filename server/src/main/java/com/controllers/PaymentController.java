package com.controllers;

import com.dtos.PaymentDto;
import com.dtos.MovieDto;
import com.services.PaymentService;
import com.services.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
// @CrossOrigin(origins = "http://localhost:5173")
public class PaymentController {

    private final PaymentService paymentService;
    private final MovieService movieService;

    public PaymentController(PaymentService paymentService, MovieService movieService) {
        this.paymentService = paymentService;
        this.movieService = movieService;
    }

    /**
     * <p>Method for verifying that payment is valid and updating movie status</p>
     * @return ResponseEntity<Boolean>
     */
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validatePayment(@RequestBody PaymentDto request) {
        boolean isValid = true;

        if (isValid && request.getReservationId() != null) {
            try {
                MovieDto movie = movieService.getMovieById(request.getReservationId());

                movie.setOpenForRent(false);

                movieService.updateMovie(request.getReservationId(), movie);

                return ResponseEntity.ok(true);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(false);
            }
        }
        return ResponseEntity.badRequest().body(false);
    }
}