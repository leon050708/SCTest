package com.cyh.sctest.service;

import com.cyh.sctest.entity.Expense;
import com.cyh.sctest.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ExpenseService {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    // 创建支出记录
    public Expense createExpense(Expense expense) {
        if (expense.getExpenseDate() == null) {
            expense.setExpenseDate(LocalDateTime.now());
        }
        return expenseRepository.save(expense);
    }
    
    // 获取所有支出记录
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }
    
    // 根据ID获取支出记录
    public Optional<Expense> getExpenseById(Long id) {
        return expenseRepository.findById(id);
    }
    
    // 更新支出记录
    public Expense updateExpense(Long id, Expense expenseDetails) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent()) {
            Expense expense = optionalExpense.get();
            expense.setDescription(expenseDetails.getDescription());
            expense.setAmount(expenseDetails.getAmount());
            expense.setCategory(expenseDetails.getCategory());
            expense.setExpenseDate(expenseDetails.getExpenseDate());
            expense.setNotes(expenseDetails.getNotes());
            return expenseRepository.save(expense);
        }
        return null;
    }
    
    // 删除支出记录
    public boolean deleteExpense(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // 根据类别查找支出
    public List<Expense> getExpensesByCategory(String category) {
        return expenseRepository.findByCategory(category);
    }
    
    // 根据日期范围查找支出
    public List<Expense> getExpensesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return expenseRepository.findByExpenseDateBetween(startDate, endDate);
    }
    
    // 根据金额范围查找支出
    public List<Expense> getExpensesByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return expenseRepository.findByAmountBetween(minAmount, maxAmount);
    }
    
    // 根据描述模糊查找支出
    public List<Expense> searchExpensesByDescription(String description) {
        return expenseRepository.findByDescriptionContainingIgnoreCase(description);
    }
    
    // 计算指定日期范围内的总支出
    public BigDecimal getTotalExpenseByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = expenseRepository.sumAmountByDateRange(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    // 计算指定类别的总支出
    public BigDecimal getTotalExpenseByCategory(String category) {
        BigDecimal total = expenseRepository.sumAmountByCategory(category);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    // 获取按类别分组的支出统计
    public Map<String, BigDecimal> getExpenseStatisticsByCategory() {
        List<Object[]> results = expenseRepository.sumAmountByCategoryGroup();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (BigDecimal) row[1]
                ));
    }
    
    // 获取按月份分组的支出统计
    public List<Object[]> getExpenseStatisticsByMonth() {
        return expenseRepository.sumAmountByMonthGroup();
    }
    
    // 获取本月总支出
    public BigDecimal getCurrentMonthTotalExpense() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return getTotalExpenseByDateRange(startOfMonth, endOfMonth);
    }
    
    // 获取本周总支出
    public BigDecimal getCurrentWeekTotalExpense() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.toLocalDate().atStartOfDay().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return getTotalExpenseByDateRange(startOfWeek, endOfWeek);
    }
} 