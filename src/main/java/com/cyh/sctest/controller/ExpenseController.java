package com.cyh.sctest.controller;

import com.cyh.sctest.entity.Expense;
import com.cyh.sctest.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "*")
public class ExpenseController {
    
    @Autowired
    private ExpenseService expenseService;
    
    // 1. 创建支出记录
    @PostMapping
    public ResponseEntity<Expense> createExpense(@Valid @RequestBody Expense expense) {
        Expense createdExpense = expenseService.createExpense(expense);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }
    
    // 2. 获取所有支出记录
    @GetMapping
    public ResponseEntity<List<Expense>> getAllExpenses() {
        List<Expense> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }
    
    // 3. 根据ID获取支出记录
    @GetMapping("/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        Optional<Expense> expense = expenseService.getExpenseById(id);
        return expense.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 4. 更新支出记录
    @PutMapping("/{id}")
    public ResponseEntity<Expense> updateExpense(@PathVariable Long id, @Valid @RequestBody Expense expenseDetails) {
        Expense updatedExpense = expenseService.updateExpense(id, expenseDetails);
        if (updatedExpense != null) {
            return ResponseEntity.ok(updatedExpense);
        }
        return ResponseEntity.notFound().build();
    }
    
    // 5. 删除支出记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        boolean deleted = expenseService.deleteExpense(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // 6. 根据类别查找支出
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Expense>> getExpensesByCategory(@PathVariable String category) {
        List<Expense> expenses = expenseService.getExpensesByCategory(category);
        return ResponseEntity.ok(expenses);
    }
    
    // 7. 根据日期范围查找支出
    @GetMapping("/date-range")
    public ResponseEntity<List<Expense>> getExpensesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Expense> expenses = expenseService.getExpensesByDateRange(startDate, endDate);
        return ResponseEntity.ok(expenses);
    }
    
    // 8. 根据金额范围查找支出
    @GetMapping("/amount-range")
    public ResponseEntity<List<Expense>> getExpensesByAmountRange(
            @RequestParam BigDecimal minAmount,
            @RequestParam BigDecimal maxAmount) {
        List<Expense> expenses = expenseService.getExpensesByAmountRange(minAmount, maxAmount);
        return ResponseEntity.ok(expenses);
    }
    
    // 9. 根据描述模糊查找支出
    @GetMapping("/search")
    public ResponseEntity<List<Expense>> searchExpensesByDescription(@RequestParam String description) {
        List<Expense> expenses = expenseService.searchExpensesByDescription(description);
        return ResponseEntity.ok(expenses);
    }
    
    // 10. 获取指定日期范围内的总支出
    @GetMapping("/total/date-range")
    public ResponseEntity<BigDecimal> getTotalExpenseByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal total = expenseService.getTotalExpenseByDateRange(startDate, endDate);
        return ResponseEntity.ok(total);
    }
    
    // 11. 获取指定类别的总支出
    @GetMapping("/total/category/{category}")
    public ResponseEntity<BigDecimal> getTotalExpenseByCategory(@PathVariable String category) {
        BigDecimal total = expenseService.getTotalExpenseByCategory(category);
        return ResponseEntity.ok(total);
    }
    
    // 12. 获取按类别分组的支出统计
    @GetMapping("/statistics/category")
    public ResponseEntity<Map<String, BigDecimal>> getExpenseStatisticsByCategory() {
        Map<String, BigDecimal> statistics = expenseService.getExpenseStatisticsByCategory();
        return ResponseEntity.ok(statistics);
    }
    
    // 13. 获取按月份分组的支出统计
    @GetMapping("/statistics/month")
    public ResponseEntity<List<Object[]>> getExpenseStatisticsByMonth() {
        List<Object[]> statistics = expenseService.getExpenseStatisticsByMonth();
        return ResponseEntity.ok(statistics);
    }
    
    // 14. 获取本月总支出
    @GetMapping("/total/current-month")
    public ResponseEntity<BigDecimal> getCurrentMonthTotalExpense() {
        BigDecimal total = expenseService.getCurrentMonthTotalExpense();
        return ResponseEntity.ok(total);
    }
    
    // 15. 获取本周总支出
    @GetMapping("/total/current-week")
    public ResponseEntity<BigDecimal> getCurrentWeekTotalExpense() {
        BigDecimal total = expenseService.getCurrentWeekTotalExpense();
        return ResponseEntity.ok(total);
    }
} 