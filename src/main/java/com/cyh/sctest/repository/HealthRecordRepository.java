package com.cyh.sctest.repository;

import com.cyh.sctest.entity.HealthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface HealthRecordRepository extends JpaRepository<HealthRecord, Long> {
    
    // 根据日期范围查找记录
    List<HealthRecord> findByRecordDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // 根据体重范围查找
    List<HealthRecord> findByWeightBetween(BigDecimal minWeight, BigDecimal maxWeight);
    
    // 根据运动类型查找
    List<HealthRecord> findByExerciseType(String exerciseType);
    
    // 根据运动时长范围查找
    List<HealthRecord> findByExerciseDurationBetween(Integer minDuration, Integer maxDuration);
    
    // 根据睡眠时长范围查找
    List<HealthRecord> findBySleepDurationBetween(Integer minDuration, Integer maxDuration);
    
    // 查找最近的记录
    List<HealthRecord> findTop10ByOrderByRecordDateDesc();
    
    // 计算指定日期范围内的平均体重
    @Query("SELECT AVG(h.weight) FROM HealthRecord h WHERE h.recordDate BETWEEN :startDate AND :endDate AND h.weight IS NOT NULL")
    BigDecimal averageWeightByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 计算指定日期范围内的平均心率
    @Query("SELECT AVG(h.heartRate) FROM HealthRecord h WHERE h.recordDate BETWEEN :startDate AND :endDate AND h.heartRate IS NOT NULL")
    Double averageHeartRateByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 计算指定日期范围内的平均运动时长
    @Query("SELECT AVG(h.exerciseDuration) FROM HealthRecord h WHERE h.recordDate BETWEEN :startDate AND :endDate AND h.exerciseDuration IS NOT NULL")
    Double averageExerciseDurationByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 计算指定日期范围内的平均睡眠时长
    @Query("SELECT AVG(h.sleepDuration) FROM HealthRecord h WHERE h.recordDate BETWEEN :startDate AND :endDate AND h.sleepDuration IS NOT NULL")
    Double averageSleepDurationByDateRange(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    // 按月份分组统计平均体重
    @Query("SELECT YEAR(h.recordDate), MONTH(h.recordDate), AVG(h.weight) FROM HealthRecord h WHERE h.weight IS NOT NULL GROUP BY YEAR(h.recordDate), MONTH(h.recordDate) ORDER BY YEAR(h.recordDate) DESC, MONTH(h.recordDate) DESC")
    List<Object[]> averageWeightByMonthGroup();
    
    // 按运动类型分组统计
    @Query("SELECT h.exerciseType, COUNT(h), AVG(h.exerciseDuration) FROM HealthRecord h WHERE h.exerciseType IS NOT NULL GROUP BY h.exerciseType")
    List<Object[]> statisticsByExerciseType();
    
    // 查找体重变化趋势（最近30天）
    @Query("SELECT h.recordDate, h.weight FROM HealthRecord h WHERE h.weight IS NOT NULL AND h.recordDate >= :startDate ORDER BY h.recordDate")
    List<Object[]> weightTrend(@Param("startDate") LocalDateTime startDate);
} 