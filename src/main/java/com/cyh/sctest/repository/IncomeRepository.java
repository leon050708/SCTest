package com.cyh.sctest.repository;

import com.cyh.sctest.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {
    
    // 根据来源查找收入
    List<Income> findBySource(String source);
    
    // 根据日期范围查找收入
    List<Income> findByIncomeDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // 根据金额范围查找收入
    List<Income> findByAmountBetween(BigDecimal minAmount, BigDecimal maxAmount);
    
    // 根据描述模糊查找
    List<Income> findByDescriptionContainingIgnoreCase(String description);
    
    // 根据来源和日期范围查找
    List<Income> findBySourceAndIncomeDateBetween(String source, LocalDateTime startDate, LocalDateTime endDate);
    
    // 计算指定日期范围内的总收入
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.incomeDate BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 计算指定来源的总收入
    @Query("SELECT SUM(i.amount) FROM Income i WHERE i.source = :source")
    BigDecimal sumAmountBySource(@Param("source") String source);
    
    // 按来源分组统计收入
    @Query("SELECT i.source, SUM(i.amount) FROM Income i GROUP BY i.source")
    List<Object[]> sumAmountBySourceGroup();
    
    // 按月份分组统计收入
    @Query("SELECT YEAR(i.incomeDate), MONTH(i.incomeDate), SUM(i.amount) FROM Income i GROUP BY YEAR(i.incomeDate), MONTH(i.incomeDate) ORDER BY YEAR(i.incomeDate) DESC, MONTH(i.incomeDate) DESC")
    List<Object[]> sumAmountByMonthGroup();
} 