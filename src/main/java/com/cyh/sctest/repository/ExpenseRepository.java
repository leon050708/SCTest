package com.cyh.sctest.repository;

import com.cyh.sctest.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    
    // 根据类别查找支出
    List<Expense> findByCategory(String category);
    
    // 根据日期范围查找支出
    List<Expense> findByExpenseDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // 根据金额范围查找支出
    List<Expense> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    // 根据描述模糊查找
    List<Expense> findByDescriptionContainingIgnoreCase(String description);
    
    // 根据类别和日期范围查找
    List<Expense> findByCategoryAndExpenseDateBetween(String category, LocalDateTime startDate, LocalDateTime endDate);
    
    // 计算指定日期范围内的总支出
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 计算指定类别的总支出
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.category = :category")
    BigDecimal sumAmountByCategory(@Param("category") String category);
    
    // 按类别分组统计支出
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e GROUP BY e.category")
    List<Object[]> sumAmountByCategoryGroup();
    
    // 按月份分组统计支出
    @Query("SELECT YEAR(e.expenseDate), MONTH(e.expenseDate), SUM(e.amount) FROM Expense e GROUP BY YEAR(e.expenseDate), MONTH(e.expenseDate) ORDER BY YEAR(e.expenseDate) DESC, MONTH(e.expenseDate) DESC")
    List<Object[]> sumAmountByMonthGroup();
} 