package com.Test.Bodima.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Test.Bodima.Model.MonthlyPayment;
import com.Test.Bodima.Repo.PaymentRepo;

@Service
public class MonthlyPaymentService {
    @Autowired
    private PaymentRepo paymentRepository;

    public MonthlyPayment createPayment(MonthlyPayment payment) {
        // Update status before saving
        payment.updateStatus();
        return paymentRepository.save(payment);
    }

    public Optional<MonthlyPayment> getPaymentById(Integer id) {
        return paymentRepository.findById(id);
    }

    public List<MonthlyPayment> getPaymentsByUserId(Integer userId) {
        return paymentRepository.findByUserUserId(userId);
    }

    public List<MonthlyPayment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public MonthlyPayment updatePayment(Integer id, MonthlyPayment paymentDetails) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setMonth(paymentDetails.getMonth());
                    payment.setMonthlyCharge(paymentDetails.getMonthlyCharge());
                    payment.setPaidAmount(paymentDetails.getPaidAmount());
                    payment.setTotalAmount(paymentDetails.getTotalAmount());
                    payment.setPaymentId(paymentDetails.getPaymentId());
                    
                    // Update status based on new amounts
                    payment.updateStatus();
                    
                    return paymentRepository.save(payment);
                })
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    public void deletePayment(Integer id) {
        paymentRepository.deleteById(id);
    }

    // Get payments by status
    public List<MonthlyPayment> getPaymentsByStatus(MonthlyPayment.PaymentStatus status) {
        return paymentRepository.findByStatus(status);
    }

    // Get payments by user and status
    public List<MonthlyPayment> getPaymentsByUserAndStatus(Integer userId, MonthlyPayment.PaymentStatus status) {
        return paymentRepository.findByUserUserIdAndStatus(userId, status);
    }

    // Update payment status
    public MonthlyPayment updatePaymentStatus(Integer id, MonthlyPayment.PaymentStatus status) {
        return paymentRepository.findById(id)
                .map(payment -> {
                    payment.setStatus(status);
                    return paymentRepository.save(payment);
                })
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + id));
    }

    // Get payment summary for a user
    public PaymentSummary getPaymentSummary(Integer userId) {
        List<MonthlyPayment> payments = getPaymentsByUserId(userId);
        
        long pendingCount = payments.stream()
                .filter(p -> p.getStatus() == MonthlyPayment.PaymentStatus.PENDING)
                .count();
        
        long paidCount = payments.stream()
                .filter(p -> p.getStatus() == MonthlyPayment.PaymentStatus.PAID)
                .count();
        
        long overdueCount = payments.stream()
                .filter(p -> p.getStatus() == MonthlyPayment.PaymentStatus.OVERDUE)
                .count();
        
        long partialCount = payments.stream()
                .filter(p -> p.getStatus() == MonthlyPayment.PaymentStatus.PARTIAL)
                .count();

        return new PaymentSummary(pendingCount, paidCount, overdueCount, partialCount);
    }

    // Payment Summary class
    public static class PaymentSummary {
        private final long pendingCount;
        private final long paidCount;
        private final long overdueCount;
        private final long partialCount;

        public PaymentSummary(long pendingCount, long paidCount, long overdueCount, long partialCount) {
            this.pendingCount = pendingCount;
            this.paidCount = paidCount;
            this.overdueCount = overdueCount;
            this.partialCount = partialCount;
        }

        public long getPendingCount() { return pendingCount; }
        public long getPaidCount() { return paidCount; }
        public long getOverdueCount() { return overdueCount; }
        public long getPartialCount() { return partialCount; }
    }
}

