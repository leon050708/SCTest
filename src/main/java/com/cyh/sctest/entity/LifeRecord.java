package com.cyh.sctest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "life_records")
public class LifeRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "标题不能为空")
    @Size(max = 100, message = "标题不能超过100个字符")
    @Column(nullable = false)
    private String title;
    
    @NotBlank(message = "内容不能为空")
    @Size(max = 2000, message = "内容不能超过2000个字符")
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    
    @Size(max = 20, message = "心情不能超过20个字符")
    private String mood;
    
    @Size(max = 100, message = "标签不能超过100个字符")
    private String tags;
    
    @Column(name = "record_date", nullable = false)
    private LocalDateTime recordDate;
    
    @Column(name = "weather")
    private String weather;
    
    @Column(name = "location")
    private String location;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 构造函数
    public LifeRecord() {
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
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public String getMood() {
        return mood;
    }
    
    public void setMood(String mood) {
        this.mood = mood;
    }
    
    public String getTags() {
        return tags;
    }
    
    public void setTags(String tags) {
        this.tags = tags;
    }
    
    public LocalDateTime getRecordDate() {
        return recordDate;
    }
    
    public void setRecordDate(LocalDateTime recordDate) {
        this.recordDate = recordDate;
    }
    
    public String getWeather() {
        return weather;
    }
    
    public void setWeather(String weather) {
        this.weather = weather;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
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