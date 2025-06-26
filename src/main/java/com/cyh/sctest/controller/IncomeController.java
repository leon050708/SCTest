package com.cyh.sctest.controller;

import com.cyh.sctest.entity.Income;
import com.cyh.sctest.service.IncomeService;
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
@RequestMapping("/api/incomes")
@CrossOrigin(origins = "*")
public class IncomeController {
    
    @Autowired
    private IncomeService incomeService;
    
    // 1. 创建收入记录
    @PostMapping
    public ResponseEntity<Income> createIncome(@Valid @RequestBody Income income) {
        Income createdIncome = incomeService.createIncome(income);
        return new ResponseEntity<>(createdIncome, HttpStatus.CREATED);
    }
    
    // 2. 获取所有收入记录
    @GetMapping
    public ResponseEntity<List<Income>> getAllIncomes() {
        List<Income> incomes = incomeService.getAllIncomes();
        return ResponseEntity.ok(incomes);
    }
    
    // 3. 根据ID获取收入记录
    @GetMapping("/{id}")
    public ResponseEntity<Income> getIncomeById(@PathVariable Long id) {
        Optional<Income> income = incomeService.getIncomeById(id);
        return income.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 4. 更新收入记录
    @PutMapping("/{id}")
    public ResponseEntity<Income> updateIncome(@PathVariable Long id, @Valid @RequestBody Income incomeDetails) {
        Income updatedIncome = incomeService.updateIncome(id, incomeDetails);
        if (updatedIncome != null) {
            return ResponseEntity.ok(updatedIncome);
        }
        return ResponseEntity.notFound().build();
    }
    
    // 5. 删除收入记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncome(@PathVariable Long id) {
        boolean deleted = incomeService.deleteIncome(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // 6. 根据来源查找收入
    @GetMapping("/source/{source}")
    public ResponseEntity<List<Income>> getIncomesBySource(@PathVariable String source) {
        List<Income> incomes = incomeService.getIncomesBySource(source);
        return ResponseEntity.ok(incomes);
    }
    
    // 7. 根据日期范围查找收入
    @GetMapping("/date-range")
    public ResponseEntity<List<Income>> getIncomesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<Income> incomes = incomeService.getIncomesByDateRange(startDate, endDate);
        return ResponseEntity.ok(incomes);
    }
    
    // 8. 根据金额范围查找收入
    @GetMapping("/amount-range")
    public ResponseEntity<List<Income>> getIncomesByAmountRange(
            @RequestParam BigDecimal minAmount,
            @RequestParam BigDecimal maxAmount) {
        List<Income> incomes = incomeService.getIncomesByAmountRange(minAmount, maxAmount);
        return ResponseEntity.ok(incomes);
    }
    
    // 9. 根据描述模糊查找收入
    @GetMapping("/search")
    public ResponseEntity<List<Income>> searchIncomesByDescription(@RequestParam String description) {
        List<Income> incomes = incomeService.searchIncomesByDescription(description);
        return ResponseEntity.ok(incomes);
    }
    
    // 10. 获取指定日期范围内的总收入
    @GetMapping("/total/date-range")
    public ResponseEntity<BigDecimal> getTotalIncomeByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal total = incomeService.getTotalIncomeByDateRange(startDate, endDate);
        return ResponseEntity.ok(total);
    }
    
    // 11. 获取指定来源的总收入
    @GetMapping("/total/source/{source}")
    public ResponseEntity<BigDecimal> getTotalIncomeBySource(@PathVariable String source) {
        BigDecimal total = incomeService.getTotalIncomeBySource(source);
        return ResponseEntity.ok(total);
    }
    
    // 12. 获取按来源分组的收入统计
    @GetMapping("/statistics/source")
    public ResponseEntity<Map<String, BigDecimal>> getIncomeStatisticsBySource() {
        Map<String, BigDecimal> statistics = incomeService.getIncomeStatisticsBySource();
        return ResponseEntity.ok(statistics);
    }
    
    // 13. 获取按月份分组的收入统计
    @GetMapping("/statistics/month")
    public ResponseEntity<List<Object[]>> getIncomeStatisticsByMonth() {
        List<Object[]> statistics = incomeService.getIncomeStatisticsByMonth();
        return ResponseEntity.ok(statistics);
    }
    
    // 14. 获取本月总收入
    @GetMapping("/total/current-month")
    public ResponseEntity<BigDecimal> getCurrentMonthTotalIncome() {
        BigDecimal total = incomeService.getCurrentMonthTotalIncome();
        return ResponseEntity.ok(total);
    }
    
    // 15. 获取本周总收入
    @GetMapping("/total/current-week")
    public ResponseEntity<BigDecimal> getCurrentWeekTotalIncome() {
        BigDecimal total = incomeService.getCurrentWeekTotalIncome();
        return ResponseEntity.ok(total);
    }
} 