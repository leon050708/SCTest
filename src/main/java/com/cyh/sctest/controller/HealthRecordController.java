package com.cyh.sctest.controller;

import com.cyh.sctest.entity.HealthRecord;
import com.cyh.sctest.service.HealthRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/health-records")
@CrossOrigin(origins = "*")
public class HealthRecordController {
    
    @Autowired
    private HealthRecordService healthRecordService;
    
    // 1. 创建健康记录
    @PostMapping
    public ResponseEntity<HealthRecord> createHealthRecord(@Valid @RequestBody HealthRecord healthRecord) {
        HealthRecord createdHealthRecord = healthRecordService.createHealthRecord(healthRecord);
        return new ResponseEntity<>(createdHealthRecord, HttpStatus.CREATED);
    }
    
    // 2. 获取所有健康记录
    @GetMapping
    public ResponseEntity<List<HealthRecord>> getAllHealthRecords() {
        List<HealthRecord> healthRecords = healthRecordService.getAllHealthRecords();
        return ResponseEntity.ok(healthRecords);
    }
    
    // 3. 根据ID获取健康记录
    @GetMapping("/{id}")
    public ResponseEntity<HealthRecord> getHealthRecordById(@PathVariable Long id) {
        Optional<HealthRecord> healthRecord = healthRecordService.getHealthRecordById(id);
        return healthRecord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 4. 更新健康记录
    @PutMapping("/{id}")
    public ResponseEntity<HealthRecord> updateHealthRecord(@PathVariable Long id, @Valid @RequestBody HealthRecord healthRecordDetails) {
        HealthRecord updatedHealthRecord = healthRecordService.updateHealthRecord(id, healthRecordDetails);
        if (updatedHealthRecord != null) {
            return ResponseEntity.ok(updatedHealthRecord);
        }
        return ResponseEntity.notFound().build();
    }
    
    // 5. 删除健康记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteHealthRecord(@PathVariable Long id) {
        boolean deleted = healthRecordService.deleteHealthRecord(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // 6. 根据日期范围查找记录
    @GetMapping("/date-range")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<HealthRecord> healthRecords = healthRecordService.getHealthRecordsByDateRange(startDate, endDate);
        return ResponseEntity.ok(healthRecords);
    }
    
    // 7. 根据体重范围查找
    @GetMapping("/weight-range")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByWeightRange(
            @RequestParam BigDecimal minWeight,
            @RequestParam BigDecimal maxWeight) {
        List<HealthRecord> healthRecords = healthRecordService.getHealthRecordsByWeightRange(minWeight, maxWeight);
        return ResponseEntity.ok(healthRecords);
    }
    
    // 8. 根据运动类型查找
    @GetMapping("/exercise-type/{exerciseType}")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByExerciseType(@PathVariable String exerciseType) {
        List<HealthRecord> healthRecords = healthRecordService.getHealthRecordsByExerciseType(exerciseType);
        return ResponseEntity.ok(healthRecords);
    }
    
    // 9. 根据运动时长范围查找
    @GetMapping("/exercise-duration-range")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsByExerciseDurationRange(
            @RequestParam Integer minDuration,
            @RequestParam Integer maxDuration) {
        List<HealthRecord> healthRecords = healthRecordService.getHealthRecordsByExerciseDurationRange(minDuration, maxDuration);
        return ResponseEntity.ok(healthRecords);
    }
    
    // 10. 根据睡眠时长范围查找
    @GetMapping("/sleep-duration-range")
    public ResponseEntity<List<HealthRecord>> getHealthRecordsBySleepDurationRange(
            @RequestParam Integer minDuration,
            @RequestParam Integer maxDuration) {
        List<HealthRecord> healthRecords = healthRecordService.getHealthRecordsBySleepDurationRange(minDuration, maxDuration);
        return ResponseEntity.ok(healthRecords);
    }
    
    // 11. 获取最近的记录
    @GetMapping("/recent")
    public ResponseEntity<List<HealthRecord>> getRecentHealthRecords() {
        List<HealthRecord> healthRecords = healthRecordService.getRecentHealthRecords();
        return ResponseEntity.ok(healthRecords);
    }
    
    // 12. 获取指定日期范围内的平均体重
    @GetMapping("/average-weight/date-range")
    public ResponseEntity<BigDecimal> getAverageWeightByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        BigDecimal averageWeight = healthRecordService.getAverageWeightByDateRange(startDate, endDate);
        return ResponseEntity.ok(averageWeight);
    }
    
    // 13. 获取指定日期范围内的平均心率
    @GetMapping("/average-heart-rate/date-range")
    public ResponseEntity<Double> getAverageHeartRateByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Double averageHeartRate = healthRecordService.getAverageHeartRateByDateRange(startDate, endDate);
        return ResponseEntity.ok(averageHeartRate);
    }
    
    // 14. 获取指定日期范围内的平均运动时长
    @GetMapping("/average-exercise-duration/date-range")
    public ResponseEntity<Double> getAverageExerciseDurationByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Double averageExerciseDuration = healthRecordService.getAverageExerciseDurationByDateRange(startDate, endDate);
        return ResponseEntity.ok(averageExerciseDuration);
    }
    
    // 15. 获取指定日期范围内的平均睡眠时长
    @GetMapping("/average-sleep-duration/date-range")
    public ResponseEntity<Double> getAverageSleepDurationByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        Double averageSleepDuration = healthRecordService.getAverageSleepDurationByDateRange(startDate, endDate);
        return ResponseEntity.ok(averageSleepDuration);
    }
    
    // 16. 获取按月份分组的平均体重统计
    @GetMapping("/statistics/weight-by-month")
    public ResponseEntity<List<Object[]>> getAverageWeightByMonthGroup() {
        List<Object[]> statistics = healthRecordService.getAverageWeightByMonthGroup();
        return ResponseEntity.ok(statistics);
    }
    
    // 17. 获取按运动类型分组的统计
    @GetMapping("/statistics/exercise-type")
    public ResponseEntity<List<Object[]>> getStatisticsByExerciseType() {
        List<Object[]> statistics = healthRecordService.getStatisticsByExerciseType();
        return ResponseEntity.ok(statistics);
    }
    
    // 18. 获取体重变化趋势
    @GetMapping("/weight-trend")
    public ResponseEntity<List<Object[]>> getWeightTrend(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate) {
        List<Object[]> weightTrend = healthRecordService.getWeightTrend(startDate);
        return ResponseEntity.ok(weightTrend);
    }
    
    // 19. 获取本月平均体重
    @GetMapping("/average-weight/current-month")
    public ResponseEntity<BigDecimal> getCurrentMonthAverageWeight() {
        BigDecimal averageWeight = healthRecordService.getCurrentMonthAverageWeight();
        return ResponseEntity.ok(averageWeight);
    }
    
    // 20. 获取本周平均运动时长
    @GetMapping("/average-exercise-duration/current-week")
    public ResponseEntity<Double> getCurrentWeekAverageExerciseDuration() {
        Double averageExerciseDuration = healthRecordService.getCurrentWeekAverageExerciseDuration();
        return ResponseEntity.ok(averageExerciseDuration);
    }
} 