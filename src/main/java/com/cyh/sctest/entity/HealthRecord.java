package com.cyh.sctest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "health_records")
public class HealthRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull(message = "记录日期不能为空")
    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;
    
    @DecimalMin(value = "20.0", message = "体重不能小于20kg")
    @DecimalMax(value = "300.0", message = "体重不能大于300kg")
    @Column(precision = 5, scale = 2)
    private BigDecimal weight;
    
    @Min(value = 50, message = "身高不能小于50cm")
    @Max(value = 250, message = "身高不能大于250cm")
    private Integer height;
    
    @Min(value = 50, message = "收缩压不能小于50")
    @Max(value = 200, message = "收缩压不能大于200")
    @Column(name = "systolic_pressure")
    private Integer systolicPressure;
    
    @Min(value = 30, message = "舒张压不能小于30")
    @Max(value = 150, message = "舒张压不能大于150")
    @Column(name = "diastolic_pressure")
    private Integer diastolicPressure;
    
    @Min(value = 40, message = "心率不能小于40")
    @Max(value = 200, message = "心率不能大于200")
    @Column(name = "heart_rate")
    private Integer heartRate;
    
    @Min(value = 0, message = "运动时长不能为负数")
    @Max(value = 24, message = "运动时长不能超过24小时")
    @Column(name = "exercise_duration")
    private Integer exerciseDuration;
    
    @Size(max = 100, message = "运动类型不能超过100个字符")
    @Column(name = "exercise_type")
    private String exerciseType;
    
    @Min(value = 0, message = "睡眠时长不能为负数")
    @Max(value = 24, message = "睡眠时长不能超过24小时")
    @Column(name = "sleep_duration")
    private Integer sleepDuration;
    
    @Size(max = 500, message = "备注不能超过500个字符")
    private String notes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 构造函数
    public HealthRecord() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    
    // Getter和Setter方法
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public LocalDateTime getRecordDate() {
        return recordDate;
    }
    
    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }
    
    public BigDecimal getWeight() {
        return weight;
    }
    
    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }
    
    public Integer getHeight() {
        return height;
    }
    
    public void setHeight(Integer height) {
        this.height = height;
    }
    
    public Integer getSystolicPressure() {
        return systolicPressure;
    }
    
    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }
    
    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }
    
    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }
    
    public Integer getHeartRate() {
        return heartRate;
    }
    
    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }
    
    public Integer getExerciseDuration() {
        return exerciseDuration;
    }
    
    public void setExerciseDuration(Integer exerciseDuration) {
        this.exerciseDuration = exerciseDuration;
    }
    
    public String getExerciseType() {
        return exerciseType;
    }
    
    public void setExerciseType(String exerciseType) {
        this.exerciseType = exerciseType;
    }
    
    public Integer getSleepDuration() {
        return sleepDuration;
    }
    
    public void setSleepDuration(Integer sleepDuration) {
        this.sleepDuration = sleepDuration;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 