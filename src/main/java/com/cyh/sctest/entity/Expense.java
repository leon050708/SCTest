package com.cyh.sctest.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "expenses")
public class Expense {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "支出描述不能为空")
    @Size(max = 200, message = "支出描述不能超过200个字符")
    @Column(nullable = false)
    private String description;
    
    @NotNull(message = "支出金额不能为空")
    @DecimalMin(value = "0.01", message = "支出金额必须大于0")
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @NotBlank(message = "支出类别不能为空")
    @Size(max = 50, message = "支出类别不能超过50个字符")
    @Column(nullable = false)
    private String category;
    
    @Column(name = "expense_date", nullable = false)
    private LocalDateTime expenseDate;
    
    @Size(max = 500, message = "备注不能超过500个字符")
    private String notes;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // 构造函数
    public Expense() {
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
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public LocalDateTime getExpenseDate() {
        return expenseDate;
    }
    
    public void setExpenseDate(LocalDateTime expenseDate) {
        this.expenseDate = expenseDate;
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