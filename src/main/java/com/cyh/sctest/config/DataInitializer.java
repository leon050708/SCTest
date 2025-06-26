package com.cyh.sctest.config;

import com.cyh.sctest.entity.Expense;
import com.cyh.sctest.entity.HealthRecord;
import com.cyh.sctest.entity.Income;
import com.cyh.sctest.entity.LifeRecord;
import com.cyh.sctest.repository.ExpenseRepository;
import com.cyh.sctest.repository.HealthRecordRepository;
import com.cyh.sctest.repository.IncomeRepository;
import com.cyh.sctest.repository.LifeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {
    
    @Autowired
    private ExpenseRepository expenseRepository;
    
    @Autowired
    private IncomeRepository incomeRepository;
    
    @Autowired
    private LifeRecordRepository lifeRecordRepository;
    
    @Autowired
    private HealthRecordRepository healthRecordRepository;
    
    @Override
    public void run(String... args) throws Exception {
        // 初始化示例数据
        initializeSampleData();
    }
    
    private void initializeSampleData() {
        // 检查是否已有数据
        if (expenseRepository.count() > 0) {
            return; // 如果已有数据，不重复初始化
        }
        
        // 创建示例支出数据
        createSampleExpenses();
        
        // 创建示例收入数据
        createSampleIncomes();
        
        // 创建示例生活记录
        createSampleLifeRecords();
        
        // 创建示例健康记录
        createSampleHealthRecords();
        
        System.out.println("示例数据初始化完成！");
    }
    
    private void createSampleExpenses() {
        LocalDateTime now = LocalDateTime.now();
        
        Expense expense1 = new Expense();
        expense1.setDescription("午餐");
        expense1.setAmount(new BigDecimal("25.50"));
        expense1.setCategory("餐饮");
        expense1.setExpenseDate(now.minusDays(1));
        expense1.setNotes("公司附近餐厅");
        expenseRepository.save(expense1);
        
        Expense expense2 = new Expense();
        expense2.setDescription("交通费");
        expense2.setAmount(new BigDecimal("15.00"));
        expense2.setCategory("交通");
        expense2.setExpenseDate(now.minusDays(2));
        expense2.setNotes("地铁和公交");
        expenseRepository.save(expense2);
        
        Expense expense3 = new Expense();
        expense3.setDescription("购物");
        expense3.setAmount(new BigDecimal("120.00"));
        expense3.setCategory("购物");
        expense3.setExpenseDate(now.minusDays(3));
        expense3.setNotes("超市购物");
        expenseRepository.save(expense3);
    }
    
    private void createSampleIncomes() {
        LocalDateTime now = LocalDateTime.now();
        
        Income income1 = new Income();
        income1.setDescription("工资");
        income1.setAmount(new BigDecimal("8000.00"));
        income1.setSource("公司");
        income1.setIncomeDate(now.minusDays(5));
        income1.setNotes("本月工资");
        incomeRepository.save(income1);
        
        Income income2 = new Income();
        income2.setDescription("兼职收入");
        income2.setAmount(new BigDecimal("500.00"));
        income2.setSource("兼职");
        income2.setIncomeDate(now.minusDays(10));
        income2.setNotes("周末兼职");
        incomeRepository.save(income2);
    }
    
    private void createSampleLifeRecords() {
        LocalDateTime now = LocalDateTime.now();
        
        LifeRecord record1 = new LifeRecord();
        record1.setTitle("美好的一天");
        record1.setContent("今天天气很好，心情愉快。和朋友一起去了公园，聊了很多有趣的话题。");
        record1.setMood("开心");
        record1.setTags("朋友,公园,聊天");
        record1.setRecordDate(now.minusDays(1));
        record1.setWeather("晴天");
        record1.setLocation("城市公园");
        lifeRecordRepository.save(record1);
        
        LifeRecord record2 = new LifeRecord();
        record2.setTitle("工作进展");
        record2.setContent("今天完成了重要的项目，虽然很累但是很有成就感。");
        record2.setMood("满足");
        record2.setTags("工作,项目,成就");
        record2.setRecordDate(now.minusDays(2));
        record2.setWeather("多云");
        record2.setLocation("公司");
        lifeRecordRepository.save(record2);
        
        LifeRecord record3 = new LifeRecord();
        record3.setTitle("学习笔记");
        record3.setContent("今天学习了新的技术，感觉收获很大。需要继续深入学习。");
        record3.setMood("充实");
        record3.setTags("学习,技术,进步");
        record3.setRecordDate(now.minusDays(3));
        record3.setWeather("阴天");
        record3.setLocation("家里");
        lifeRecordRepository.save(record3);
    }
    
    private void createSampleHealthRecords() {
        LocalDateTime now = LocalDateTime.now();
        
        HealthRecord record1 = new HealthRecord();
        record1.setRecordDate(now.minusDays(1));
        record1.setWeight(new BigDecimal("65.5"));
        record1.setHeight(170);
        record1.setSystolicPressure(120);
        record1.setDiastolicPressure(80);
        record1.setHeartRate(75);
        record1.setExerciseDuration(8);
        record1.setExerciseType("跑步");
        record1.setSleepDuration(8);
        record1.setNotes("今天感觉很好");
        healthRecordRepository.save(record1);
        
        HealthRecord record2 = new HealthRecord();
        record2.setRecordDate(now.minusDays(2));
        record2.setWeight(new BigDecimal("65.8"));
        record2.setHeight(170);
        record2.setSystolicPressure(118);
        record2.setDiastolicPressure(78);
        record2.setHeartRate(72);
        record2.setExerciseDuration(2);
        record2.setExerciseType("游泳");
        record2.setSleepDuration(7);
        record2.setNotes("游泳很舒服");
        healthRecordRepository.save(record2);
        
        HealthRecord record3 = new HealthRecord();
        record3.setRecordDate(now.minusDays(3));
        record3.setWeight(new BigDecimal("66.0"));
        record3.setHeight(170);
        record3.setSystolicPressure(125);
        record3.setDiastolicPressure(82);
        record3.setHeartRate(78);
        record3.setExerciseDuration(0);
        record3.setExerciseType("休息");
        record3.setSleepDuration(6);
        record3.setNotes("今天比较累");
        healthRecordRepository.save(record3);
    }
} 