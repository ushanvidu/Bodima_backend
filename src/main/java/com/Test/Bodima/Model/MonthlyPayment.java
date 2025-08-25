package com.Test.Bodima.Model;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class MonthlyPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int paymentId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"payments", "hibernateLazyInitializer", "handler"})
    private User user;

    private Date month;

    private BigDecimal totalAmount;

    @Column(precision = 10, scale = 2)
    private BigDecimal monthlyCharge;

    @Column(precision = 10, scale = 2)
    private BigDecimal paidAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status")
    private PaymentStatus status = PaymentStatus.PENDING;

    // Payment Status Enum
    public enum PaymentStatus {
        PENDING("Pending"),
        PAID("Paid"),
        OVERDUE("Overdue"),
        PARTIAL("Partial");

        private final String displayName;

        PaymentStatus(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

    // Constructors, Getters, Setters
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(int paymentId) {
        this.paymentId = paymentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }

    public BigDecimal getMonthlyCharge() {
        return monthlyCharge;
    }

    public void setMonthlyCharge(BigDecimal monthlyCharge) {
        this.monthlyCharge = monthlyCharge;
    }

    public BigDecimal getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(BigDecimal paidAmount) {
        this.paidAmount = paidAmount;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    // Helper method to calculate and update status based on amounts
    public void updateStatus() {
        if (paidAmount == null) {
            paidAmount = BigDecimal.ZERO;
        }
        if (totalAmount == null) {
            totalAmount = monthlyCharge != null ? monthlyCharge : BigDecimal.ZERO;
        }

        int comparison = paidAmount.compareTo(totalAmount);
        if (comparison >= 0) {
            this.status = PaymentStatus.PAID;
        } else if (paidAmount.compareTo(BigDecimal.ZERO) > 0) {
            this.status = PaymentStatus.PARTIAL;
        } else {
            this.status = PaymentStatus.PENDING;
        }
    }
}