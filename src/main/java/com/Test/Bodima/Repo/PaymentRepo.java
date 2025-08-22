package com.Test.Bodima.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Test.Bodima.Model.MonthlyPayment;

@Repository
public interface PaymentRepo extends JpaRepository<MonthlyPayment, Integer> {
    List<MonthlyPayment> findByUserUserId(int userId);
    List<MonthlyPayment> findByStatus(MonthlyPayment.PaymentStatus status);
    List<MonthlyPayment> findByUserUserIdAndStatus(int userId, MonthlyPayment.PaymentStatus status);
}
