package com.cyh.sctest.service;

import com.cyh.sctest.entity.HealthRecord;
import com.cyh.sctest.repository.HealthRecordRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class HealthRecordService {
    
    @Autowired
    private HealthRecordRepository healthRecordRepository;
    
    // 创建健康记录
    public HealthRecord createHealthRecord(@Valid HealthRecord healthRecord) {
        if (healthRecord.getRecordDate() == null) {
            healthRecord.setRecordDate(LocalDateTime.now());
        }
        return healthRecordRepository.save(healthRecord);
    }
    
    // 获取所有健康记录
    public List<HealthRecord> getAllHealthRecords() {
        return healthRecordRepository.findAll();
    }
    
    // 根据ID获取健康记录
    public Optional<HealthRecord> getHealthRecordById(Long id) {
        return healthRecordRepository.findById(id);
    }
    
    // 更新健康记录
    public HealthRecord updateHealthRecord(Long id, HealthRecord healthRecordDetails) {
        Optional<HealthRecord> optionalHealthRecord = healthRecordRepository.findById(id);
        if (optionalHealthRecord.isPresent()) {
            HealthRecord healthRecord = optionalHealthRecord.get();
            healthRecord.setRecordDate(healthRecordDetails.getRecordDate());
            healthRecord.setWeight(healthRecordDetails.getWeight());
            healthRecord.setHeight(healthRecordDetails.getHeight());
            healthRecord.setSystolicPressure(healthRecordDetails.getSystolicPressure());
            healthRecord.setDiastolicPressure(healthRecordDetails.getDiastolicPressure());
            healthRecord.setHeartRate(healthRecordDetails.getHeartRate());
            healthRecord.setExerciseDuration(healthRecordDetails.getExerciseDuration());
            healthRecord.setExerciseType(healthRecordDetails.getExerciseType());
            healthRecord.setSleepDuration(healthRecordDetails.getSleepDuration());
            healthRecord.setNotes(healthRecordDetails.getNotes());
            return healthRecordRepository.save(healthRecord);
        }
        return null;
    }
    
    // 删除健康记录
    public boolean deleteHealthRecord(Long id) {
        if (healthRecordRepository.existsById(id)) {
            healthRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // 根据日期范围查找记录
    public List<HealthRecord> getHealthRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return healthRecordRepository.findByRecordDateBetween(startDate, endDate);
    }
    
    // 根据体重范围查找
    public List<HealthRecord> getHealthRecordsByWeightRange(BigDecimal minWeight, BigDecimal maxWeight) {
        return healthRecordRepository.findByWeightBetween(minWeight, maxWeight);
    }
    
    // 根据运动类型查找
    public List<HealthRecord> getHealthRecordsByExerciseType(String exerciseType) {
        return healthRecordRepository.findByExerciseType(exerciseType);
    }
    
    // 根据运动时长范围查找
    public List<HealthRecord> getHealthRecordsByExerciseDurationRange(Integer minDuration, Integer maxDuration) {
        return healthRecordRepository.findByExerciseDurationBetween(minDuration, maxDuration);
    }
    
    // 根据睡眠时长范围查找
    public List<HealthRecord> getHealthRecordsBySleepDurationRange(Integer minDuration, Integer maxDuration) {
        return healthRecordRepository.findBySleepDurationBetween(minDuration, maxDuration);
    }
    
    // 获取最近的记录
    public List<HealthRecord> getRecentHealthRecords() {
        return healthRecordRepository.findTop10ByOrderByRecordDateDesc();
    }
    
    // 计算指定日期范围内的平均体重
    public BigDecimal getAverageWeightByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        BigDecimal average = healthRecordRepository.averageWeightByDateRange(startDate, endDate);
        return average != null ? average : BigDecimal.ZERO;
    }
    
    // 计算指定日期范围内的平均心率
    public Double getAverageHeartRateByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Double average = healthRecordRepository.averageHeartRateByDateRange(startDate, endDate);
        return average != null ? average : 0.0;
    }
    
    // 计算指定日期范围内的平均运动时长
    public Double getAverageExerciseDurationByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Double average = healthRecordRepository.averageExerciseDurationByDateRange(startDate, endDate);
        return average != null ? average : 0.0;
    }
    
    // 计算指定日期范围内的平均睡眠时长
    public Double getAverageSleepDurationByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        Double average = healthRecordRepository.averageSleepDurationByDateRange(startDate, endDate);
        return average != null ? average : 0.0;
    }
    
    // 获取按月份分组的平均体重统计
    public List<Object[]> getAverageWeightByMonthGroup() {
        return healthRecordRepository.averageWeightByMonthGroup();
    }
    
    // 获取按运动类型分组的统计
    public List<Object[]> getStatisticsByExerciseType() {
        return healthRecordRepository.statisticsByExerciseType();
    }
    
    // 获取体重变化趋势
    public List<Object[]> getWeightTrend(LocalDateTime startDate) {
        return healthRecordRepository.weightTrend(startDate);
    }
    
    // 获取本月平均体重
    public BigDecimal getCurrentMonthAverageWeight() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return getAverageWeightByDateRange(startOfMonth, endOfMonth);
    }
    
    // 获取本周平均运动时长
    public Double getCurrentWeekAverageExerciseDuration() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.toLocalDate().atStartOfDay().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return getAverageExerciseDurationByDateRange(startOfWeek, endOfWeek);
    }
} 