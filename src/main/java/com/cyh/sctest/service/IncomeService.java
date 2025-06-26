package com.cyh.sctest.service;

import com.cyh.sctest.entity.Income;
import com.cyh.sctest.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class IncomeService {
    
    @Autowired
    private IncomeRepository incomeRepository;
    
    // 创建收入记录
    public Income createIncome(Income income) {
        if (income.getIncomeDate() == null) {
            income.setIncomeDate(LocalDateTime.now());
        }
        return incomeRepository.save(income);
    }
    
    // 获取所有收入记录
    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }
    
    // 根据ID获取收入记录
    public Optional<Income> getIncomeById(Long id) {
        return incomeRepository.findById(id);
    }
    
    // 更新收入记录
    public Income updateIncome(Long id, Income incomeDetails) {
        Optional<Income> optionalIncome = incomeRepository.findById(id);
        if (optionalIncome.isPresent()) {
            Income income = optionalIncome.get();
            income.setDescription(incomeDetails.getDescription());
            income.setAmount(incomeDetails.getAmount());
            income.setSource(incomeDetails.getSource());
            income.setIncomeDate(incomeDetails.getIncomeDate());
            income.setNotes(incomeDetails.getNotes());
            return incomeRepository.save(income);
        }
        return null;
    }
    
    // 删除收入记录
    public boolean deleteIncome(Long id) {
        if (incomeRepository.existsById(id)) {
            incomeRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // 根据来源查找收入
    public List<Income> getIncomesBySource(String source) {
        return incomeRepository.findBySource(source);
    }
    
    // 根据日期范围查找收入
    public List<Income> getIncomesByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return incomeRepository.findByIncomeDateBetween(startDate, endDate);
    }
    
    // 根据金额范围查找收入
    public List<Income> getIncomesByAmountRange(BigDecimal minAmount, BigDecimal maxAmount) {
        return incomeRepository.findByAmountBetween(minAmount, maxAmount);
    }
    
    // 根据描述模糊查找收入
    public List<Income> searchIncomesByDescription(String description) {
        return incomeRepository.findByDescriptionContainingIgnoreCase(description);
    }
    
    // 计算指定日期范围内的总收入
    public BigDecimal getTotalIncomeByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal total = incomeRepository.sumAmountByDateRange(startDate, endDate);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    // 计算指定来源的总收入
    public BigDecimal getTotalIncomeBySource(String source) {
        BigDecimal total = incomeRepository.sumAmountBySource(source);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    // 获取按来源分组的收入统计
    public Map<String, BigDecimal> getIncomeStatisticsBySource() {
        List<Object[]> results = incomeRepository.sumAmountBySourceGroup();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (BigDecimal) row[1]
                ));
    }
    
    // 获取按月份分组的收入统计
    public List<Object[]> getIncomeStatisticsByMonth() {
        return incomeRepository.sumAmountByMonthGroup();
    }
    
    // 获取本月总收入
    public BigDecimal getCurrentMonthTotalIncome() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return getTotalIncomeByDateRange(startOfMonth, endOfMonth);
    }
    
    // 获取本周总收入
    public BigDecimal getCurrentWeekTotalIncome() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.toLocalDate().atStartOfDay().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return getTotalIncomeByDateRange(startOfWeek, endOfWeek);
    }
} 