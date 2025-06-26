package com.cyh.sctest.controller;

import com.cyh.sctest.service.ExpenseService;
import com.cyh.sctest.service.HealthRecordService;
import com.cyh.sctest.service.IncomeService;
import com.cyh.sctest.service.LifeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {
    
    @Autowired
    private ExpenseService expenseService;
    
    @Autowired
    private IncomeService incomeService;
    
    @Autowired
    private LifeRecordService lifeRecordService;
    
    @Autowired
    private HealthRecordService healthRecordService;
    
    // 1. 获取仪表板概览数据
    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> getDashboardOverview() {
        Map<String, Object> overview = new HashMap<>();
        
        // 本月收支情况
        BigDecimal currentMonthIncome = incomeService.getCurrentMonthTotalIncome();
        BigDecimal currentMonthExpense = expenseService.getCurrentMonthTotalExpense();
        BigDecimal currentMonthBalance = currentMonthIncome.subtract(currentMonthExpense);
        
        // 本周收支情况
        BigDecimal currentWeekIncome = incomeService.getCurrentWeekTotalIncome();
        BigDecimal currentWeekExpense = expenseService.getCurrentWeekTotalExpense();
        BigDecimal currentWeekBalance = currentWeekIncome.subtract(currentWeekExpense);
        
        // 生活记录统计
        long currentMonthLifeRecords = lifeRecordService.getCurrentMonthRecordCount();
        long currentWeekLifeRecords = lifeRecordService.getCurrentWeekRecordCount();
        
        // 健康记录统计
        BigDecimal currentMonthAverageWeight = healthRecordService.getCurrentMonthAverageWeight();
        Double currentWeekAverageExerciseDuration = healthRecordService.getCurrentWeekAverageExerciseDuration();
        
        overview.put("currentMonthIncome", currentMonthIncome);
        overview.put("currentMonthExpense", currentMonthExpense);
        overview.put("currentMonthBalance", currentMonthBalance);
        overview.put("currentWeekIncome", currentWeekIncome);
        overview.put("currentWeekExpense", currentWeekExpense);
        overview.put("currentWeekBalance", currentWeekBalance);
        overview.put("currentMonthLifeRecords", currentMonthLifeRecords);
        overview.put("currentWeekLifeRecords", currentWeekLifeRecords);
        overview.put("currentMonthAverageWeight", currentMonthAverageWeight);
        overview.put("currentWeekAverageExerciseDuration", currentWeekAverageExerciseDuration);
        
        return ResponseEntity.ok(overview);
    }
    
    // 2. 获取财务统计
    @GetMapping("/finance")
    public ResponseEntity<Map<String, Object>> getFinanceStatistics() {
        Map<String, Object> finance = new HashMap<>();
        
        // 按类别分组的支出统计
        Map<String, BigDecimal> expenseByCategory = expenseService.getExpenseStatisticsByCategory();
        
        // 按来源分组的收入统计
        Map<String, BigDecimal> incomeBySource = incomeService.getIncomeStatisticsBySource();
        
        // 按月份分组的收支统计
        List<Object[]> expenseByMonth = expenseService.getExpenseStatisticsByMonth();
        List<Object[]> incomeByMonth = incomeService.getIncomeStatisticsByMonth();
        
        finance.put("expenseByCategory", expenseByCategory);
        finance.put("incomeBySource", incomeBySource);
        finance.put("expenseByMonth", expenseByMonth);
        finance.put("incomeByMonth", incomeByMonth);
        
        return ResponseEntity.ok(finance);
    }
    
    // 3. 获取生活记录统计
    @GetMapping("/life")
    public ResponseEntity<Map<String, Object>> getLifeStatistics() {
        Map<String, Object> life = new HashMap<>();
        
        // 按心情分组的统计
        Map<String, Long> moodStatistics = lifeRecordService.getLifeRecordStatisticsByMood();
        
        // 按月份分组的统计
        List<Object[]> monthlyStatistics = lifeRecordService.getLifeRecordStatisticsByMonth();
        
        // 最近的记录
        List<Object> recentRecords = new ArrayList<>(lifeRecordService.getRecentLifeRecords());
        
        life.put("moodStatistics", moodStatistics);
        life.put("monthlyStatistics", monthlyStatistics);
        life.put("recentRecords", recentRecords);
        
        return ResponseEntity.ok(life);
    }
    
    // 4. 获取健康统计
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> getHealthStatistics() {
        Map<String, Object> health = new HashMap<>();
        
        // 按月份分组的平均体重统计
        List<Object[]> weightByMonth = healthRecordService.getAverageWeightByMonthGroup();
        
        // 按运动类型分组的统计
        List<Object[]> exerciseByType = healthRecordService.getStatisticsByExerciseType();
        
        // 体重变化趋势（最近30天）
        LocalDateTime thirtyDaysAgo = LocalDateTime.now().minusDays(30);
        List<Object[]> weightTrend = healthRecordService.getWeightTrend(thirtyDaysAgo);
        
        // 最近的健康记录
        List<Object> recentHealthRecords = new ArrayList<>(healthRecordService.getRecentHealthRecords());
        
        health.put("weightByMonth", weightByMonth);
        health.put("exerciseByType", exerciseByType);
        health.put("weightTrend", weightTrend);
        health.put("recentHealthRecords", recentHealthRecords);
        
        return ResponseEntity.ok(health);
    }
    
    // 5. 获取综合报告
    @GetMapping("/report")
    public ResponseEntity<Map<String, Object>> getComprehensiveReport() {
        Map<String, Object> report = new HashMap<>();
        
        // 财务报告
        Map<String, Object> financeReport = new HashMap<>();
        financeReport.put("totalIncome", incomeService.getAllIncomes().size());
        financeReport.put("totalExpense", expenseService.getAllExpenses().size());
        financeReport.put("monthlyIncome", incomeService.getCurrentMonthTotalIncome());
        financeReport.put("monthlyExpense", expenseService.getCurrentMonthTotalExpense());
        
        // 生活报告
        Map<String, Object> lifeReport = new HashMap<>();
        lifeReport.put("totalRecords", lifeRecordService.getAllLifeRecords().size());
        lifeReport.put("monthlyRecords", lifeRecordService.getCurrentMonthRecordCount());
        lifeReport.put("moodDistribution", lifeRecordService.getLifeRecordStatisticsByMood());
        
        // 健康报告
        Map<String, Object> healthReport = new HashMap<>();
        healthReport.put("totalRecords", healthRecordService.getAllHealthRecords().size());
        healthReport.put("averageWeight", healthRecordService.getCurrentMonthAverageWeight());
        healthReport.put("averageExerciseDuration", healthRecordService.getCurrentWeekAverageExerciseDuration());
        
        report.put("finance", financeReport);
        report.put("life", lifeReport);
        report.put("health", healthReport);
        report.put("generatedAt", LocalDateTime.now());
        
        return ResponseEntity.ok(report);
    }
} 