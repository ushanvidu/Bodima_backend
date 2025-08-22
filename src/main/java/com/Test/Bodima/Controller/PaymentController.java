package com.Test.Bodima.Controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Test.Bodima.Model.MonthlyPayment;
import com.Test.Bodima.Service.MonthlyPaymentService;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "*")
public class PaymentController {

    private final MonthlyPaymentService paymentService;

    public PaymentController(MonthlyPaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<MonthlyPayment> createPayment(@RequestBody MonthlyPayment payment) {
        MonthlyPayment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.ok(createdPayment);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyPayment> getPaymentById(@PathVariable Integer id) {
        return paymentService.getPaymentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<MonthlyPayment>> getAllPayments() {
        List<MonthlyPayment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MonthlyPayment>> getPaymentsByUser(@PathVariable Integer userId) {
        List<MonthlyPayment> payments = paymentService.getPaymentsByUserId(userId);
        return ResponseEntity.ok(payments);
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<MonthlyPayment>> getPaymentsByStatus(@PathVariable String status) {
        try {
            MonthlyPayment.PaymentStatus paymentStatus = MonthlyPayment.PaymentStatus.valueOf(status.toUpperCase());
            List<MonthlyPayment> payments = paymentService.getPaymentsByStatus(paymentStatus);
            return ResponseEntity.ok(payments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}/status/{status}")
    public ResponseEntity<List<MonthlyPayment>> getPaymentsByUserAndStatus(
            @PathVariable Integer userId, 
            @PathVariable String status) {
        try {
            MonthlyPayment.PaymentStatus paymentStatus = MonthlyPayment.PaymentStatus.valueOf(status.toUpperCase());
            List<MonthlyPayment> payments = paymentService.getPaymentsByUserAndStatus(userId, paymentStatus);
            return ResponseEntity.ok(payments);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/user/{userId}/summary")
    public ResponseEntity<MonthlyPaymentService.PaymentSummary> getPaymentSummary(@PathVariable Integer userId) {
        MonthlyPaymentService.PaymentSummary summary = paymentService.getPaymentSummary(userId);
        return ResponseEntity.ok(summary);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<MonthlyPayment> updatePaymentStatus(
            @PathVariable Integer id, 
            @RequestParam String status) {
        try {
            MonthlyPayment.PaymentStatus paymentStatus = MonthlyPayment.PaymentStatus.valueOf(status.toUpperCase());
            MonthlyPayment updatedPayment = paymentService.updatePaymentStatus(id, paymentStatus);
            return ResponseEntity.ok(updatedPayment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyPayment> updatePayment(@PathVariable Integer id, @RequestBody MonthlyPayment paymentDetails) {
        try {
            MonthlyPayment updatedPayment = paymentService.updatePayment(id, paymentDetails);
            return ResponseEntity.ok(updatedPayment);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Integer id) {
        paymentService.deletePayment(id);
        return ResponseEntity.ok().build();
    }
}
