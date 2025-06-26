package com.cyh.sctest;

import com.cyh.sctest.entity.Expense;
import com.cyh.sctest.entity.Income;
import com.cyh.sctest.entity.LifeRecord;
import com.cyh.sctest.entity.HealthRecord;
import com.cyh.sctest.service.ExpenseService;
import com.cyh.sctest.service.IncomeService;
import com.cyh.sctest.service.LifeRecordService;
import com.cyh.sctest.service.HealthRecordService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@Transactional
class SCtestApplicationTests {
    @Autowired
    ExpenseService expenseService;
    @Autowired
    IncomeService incomeService;
    @Autowired
    LifeRecordService lifeRecordService;
    @Autowired
    HealthRecordService healthRecordService;

    // ==================== 支出模块测试 ====================
    
    /**
     * 测试创建支出记录 - 餐饮类支出
     * 测试数据：描述="午餐支出", 金额=25.50, 类别="餐饮", 备注="公司午餐"
     */
    @Test
    void testCreateExpense1() {
        Expense expense = new Expense();
        expense.setDescription("午餐支出");
        expense.setAmount(new BigDecimal("25.50"));
        expense.setCategory("餐饮");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("公司午餐");
        
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("午餐支出", saved.getDescription());
        Assertions.assertEquals(new BigDecimal("25.50"), saved.getAmount());
    }

    /**
     * 测试创建支出记录 - 交通类支出
     * 测试数据：描述="打车费", 金额=15.00, 类别="交通", 备注="上班打车"
     */
    @Test
    void testCreateExpense2() {
        Expense expense = new Expense();
        expense.setDescription("打车费");
        expense.setAmount(new BigDecimal("15.00"));
        expense.setCategory("交通");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("上班打车");
        
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("打车费", saved.getDescription());
        Assertions.assertEquals(new BigDecimal("15.00"), saved.getAmount());
    }

    /**
     * 测试创建支出记录 - 购物类支出
     * 测试数据：描述="超市购物", 金额=120.80, 类别="购物", 备注="日常用品"
     */
    @Test
    void testCreateExpense3() {
        Expense expense = new Expense();
        expense.setDescription("超市购物");
        expense.setAmount(new BigDecimal("120.80"));
        expense.setCategory("购物");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("日常用品");
        
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("超市购物", saved.getDescription());
        Assertions.assertEquals(new BigDecimal("120.80"), saved.getAmount());
    }

    /**
     * 测试根据ID查询支出记录 - 正常ID
     * 测试数据：先创建一个支出记录，然后通过ID查询
     */
    @Test
    void testGetExpenseById1() {
        Expense expense = new Expense();
        expense.setDescription("测试支出");
        expense.setAmount(new BigDecimal("100.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        Expense saved = expenseService.createExpense(expense);
        
        Optional<Expense> found = expenseService.getExpenseById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(saved.getId(), found.get().getId());
    }

    /**
     * 测试根据ID查询支出记录 - 不存在的ID
     * 测试数据：使用一个不存在的ID (99999L)
     */
    @Test
    void testGetExpenseById2() {
        Optional<Expense> found = expenseService.getExpenseById(99999L);
        Assertions.assertFalse(found.isPresent());
    }

    /**
     * 测试更新支出记录 - 更新金额和描述
     * 测试数据：原始记录(50.00) -> 更新后(75.00)
     */
    @Test
    void testUpdateExpense1() {
        Expense expense = new Expense();
        expense.setDescription("原始支出");
        expense.setAmount(new BigDecimal("50.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        Expense saved = expenseService.createExpense(expense);
        
        saved.setDescription("更新支出");
        saved.setAmount(new BigDecimal("75.00"));
        Expense updated = expenseService.updateExpense(saved.getId(), saved);
        
        Assertions.assertEquals("更新支出", updated.getDescription());
        Assertions.assertEquals(new BigDecimal("75.00"), updated.getAmount());
    }

    /**
     * 测试更新支出记录 - 更新类别和备注
     * 测试数据：原始记录(餐饮) -> 更新后(娱乐)
     */
    @Test
    void testUpdateExpense2() {
        Expense expense = new Expense();
        expense.setDescription("餐饮支出");
        expense.setAmount(new BigDecimal("30.00"));
        expense.setCategory("餐饮");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("原始备注");
        Expense saved = expenseService.createExpense(expense);
        
        saved.setCategory("娱乐");
        saved.setNotes("更新后的备注");
        Expense updated = expenseService.updateExpense(saved.getId(), saved);
        
        Assertions.assertEquals("娱乐", updated.getCategory());
        Assertions.assertEquals("更新后的备注", updated.getNotes());
    }

    /**
     * 测试删除支出记录 - 正常删除
     * 测试数据：创建一个支出记录，然后删除它
     */
    @Test
    void testDeleteExpense1() {
        Expense expense = new Expense();
        expense.setDescription("待删除支出");
        expense.setAmount(new BigDecimal("30.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        Expense saved = expenseService.createExpense(expense);
        
        expenseService.deleteExpense(saved.getId());
        
        Optional<Expense> found = expenseService.getExpenseById(saved.getId());
        Assertions.assertFalse(found.isPresent());
    }

    /**
     * 测试根据类别查询支出记录 - 餐饮类别
     * 测试数据：创建多个餐饮类别的支出记录
     */
    @Test
    void testGetExpensesByCategory1() {
        Expense expense1 = new Expense();
        expense1.setDescription("午餐");
        expense1.setAmount(new BigDecimal("25.00"));
        expense1.setCategory("餐饮");
        expense1.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense1);

        Expense expense2 = new Expense();
        expense2.setDescription("晚餐");
        expense2.setAmount(new BigDecimal("35.00"));
        expense2.setCategory("餐饮");
        expense2.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense2);
        
        List<Expense> foodExpenses = expenseService.getExpensesByCategory("餐饮");
        Assertions.assertTrue(foodExpenses.size() >= 2);
        foodExpenses.forEach(e -> Assertions.assertEquals("餐饮", e.getCategory()));
    }

    /**
     * 测试根据类别查询支出记录 - 交通类别
     * 测试数据：创建多个交通类别的支出记录
     */
    @Test
    void testGetExpensesByCategory2() {
        Expense expense1 = new Expense();
        expense1.setDescription("公交费");
        expense1.setAmount(new BigDecimal("2.00"));
        expense1.setCategory("交通");
        expense1.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense1);

        Expense expense2 = new Expense();
        expense2.setDescription("地铁费");
        expense2.setAmount(new BigDecimal("3.00"));
        expense2.setCategory("交通");
        expense2.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense2);
        
        List<Expense> transportExpenses = expenseService.getExpensesByCategory("交通");
        Assertions.assertTrue(transportExpenses.size() >= 2);
        transportExpenses.forEach(e -> Assertions.assertEquals("交通", e.getCategory()));
    }

    // ==================== 收入模块测试 ====================
    
    /**
     * 测试创建收入记录 - 工资收入
     * 测试数据：描述="月薪", 金额=8000.00, 来源="工资", 备注="固定工资"
     */
    @Test
    void testCreateIncome1() {
        Income income = new Income();
        income.setDescription("月薪");
        income.setAmount(new BigDecimal("8000.00"));
        income.setSource("工资");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("固定工资");
        
        Income saved = incomeService.createIncome(income);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("月薪", saved.getDescription());
        Assertions.assertEquals(new BigDecimal("8000.00"), saved.getAmount());
    }

    /**
     * 测试创建收入记录 - 兼职收入
     * 测试数据：描述="兼职收入", 金额=1000.00, 来源="兼职", 备注="周末兼职"
     */
    @Test
    void testCreateIncome2() {
        Income income = new Income();
        income.setDescription("兼职收入");
        income.setAmount(new BigDecimal("1000.00"));
        income.setSource("兼职");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("周末兼职");
        
        Income saved = incomeService.createIncome(income);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("兼职收入", saved.getDescription());
        Assertions.assertEquals(new BigDecimal("1000.00"), saved.getAmount());
    }

    /**
     * 测试创建收入记录 - 投资收入
     * 测试数据：描述="股票收益", 金额=2000.00, 来源="投资", 备注="股票分红"
     */
    @Test
    void testCreateIncome3() {
        Income income = new Income();
        income.setDescription("股票收益");
        income.setAmount(new BigDecimal("2000.00"));
        income.setSource("投资");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("股票分红");
        
        Income saved = incomeService.createIncome(income);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("股票收益", saved.getDescription());
        Assertions.assertEquals(new BigDecimal("2000.00"), saved.getAmount());
    }

    /**
     * 测试根据来源查询收入记录 - 工资来源
     * 测试数据：创建多个工资来源的收入记录
     */
    @Test
    void testGetIncomesBySource1() {
        Income income1 = new Income();
        income1.setDescription("工资收入1");
        income1.setAmount(new BigDecimal("5000.00"));
        income1.setSource("工资");
        income1.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income1);

        Income income2 = new Income();
        income2.setDescription("工资收入2");
        income2.setAmount(new BigDecimal("6000.00"));
        income2.setSource("工资");
        income2.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income2);

        List<Income> salaryIncomes = incomeService.getIncomesBySource("工资");
        Assertions.assertTrue(salaryIncomes.size() >= 2);
        salaryIncomes.forEach(i -> Assertions.assertEquals("工资", i.getSource()));
    }

    /**
     * 测试根据来源查询收入记录 - 兼职来源
     * 测试数据：创建多个兼职来源的收入记录
     */
    @Test
    void testGetIncomesBySource2() {
        Income income1 = new Income();
        income1.setDescription("兼职收入1");
        income1.setAmount(new BigDecimal("800.00"));
        income1.setSource("兼职");
        income1.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income1);

        Income income2 = new Income();
        income2.setDescription("兼职收入2");
        income2.setAmount(new BigDecimal("1200.00"));
        income2.setSource("兼职");
        income2.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income2);

        List<Income> partTimeIncomes = incomeService.getIncomesBySource("兼职");
        Assertions.assertTrue(partTimeIncomes.size() >= 2);
        partTimeIncomes.forEach(i -> Assertions.assertEquals("兼职", i.getSource()));
    }

    // ==================== 生活记录模块测试 ====================
    
    /**
     * 测试创建生活记录 - 开心心情
     * 测试数据：标题="美好的一天", 内容="今天很开心", 心情="开心", 标签="美好,生活"
     */
    @Test
    void testCreateLifeRecord1() {
        LifeRecord record = new LifeRecord();
        record.setTitle("美好的一天");
        record.setContent("今天很开心");
        record.setMood("开心");
        record.setTags("美好,生活");
        record.setRecordDate(LocalDateTime.now());
        
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("美好的一天", saved.getTitle());
        Assertions.assertEquals("开心", saved.getMood());
    }

    /**
     * 测试创建生活记录 - 平静心情
     * 测试数据：标题="平静的一天", 内容="今天很平静", 心情="平静", 标签="平静,放松"
     */
    @Test
    void testCreateLifeRecord2() {
        LifeRecord record = new LifeRecord();
        record.setTitle("平静的一天");
        record.setContent("今天很平静");
        record.setMood("平静");
        record.setTags("平静,放松");
        record.setRecordDate(LocalDateTime.now());
        
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("平静的一天", saved.getTitle());
        Assertions.assertEquals("平静", saved.getMood());
    }

    /**
     * 测试创建生活记录 - 难过心情
     * 测试数据：标题="难过的一天", 内容="今天心情不好", 心情="难过", 标签="难过,需要安慰"
     */
    @Test
    void testCreateLifeRecord3() {
        LifeRecord record = new LifeRecord();
        record.setTitle("难过的一天");
        record.setContent("今天心情不好");
        record.setMood("难过");
        record.setTags("难过,需要安慰");
        record.setRecordDate(LocalDateTime.now());
        
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("难过的一天", saved.getTitle());
        Assertions.assertEquals("难过", saved.getMood());
    }

    /**
     * 测试根据心情查询生活记录 - 开心心情
     * 测试数据：创建多个开心心情的生活记录
     */
    @Test
    void testGetLifeRecordsByMood1() {
        LifeRecord happyRecord1 = new LifeRecord();
        happyRecord1.setTitle("开心记录1");
        happyRecord1.setContent("今天很开心");
        happyRecord1.setMood("开心");
        happyRecord1.setTags("开心,美好");
        happyRecord1.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(happyRecord1);

        LifeRecord happyRecord2 = new LifeRecord();
        happyRecord2.setTitle("开心记录2");
        happyRecord2.setContent("今天又很开心");
        happyRecord2.setMood("开心");
        happyRecord2.setTags("开心,美好");
        happyRecord2.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(happyRecord2);

        List<LifeRecord> happyRecords = lifeRecordService.getLifeRecordsByMood("开心");
        Assertions.assertTrue(happyRecords.size() >= 2);
        happyRecords.forEach(r -> Assertions.assertEquals("开心", r.getMood()));
    }

    /**
     * 测试根据心情查询生活记录 - 难过心情
     * 测试数据：创建多个难过心情的生活记录
     */
    @Test
    void testGetLifeRecordsByMood2() {
        LifeRecord sadRecord1 = new LifeRecord();
        sadRecord1.setTitle("难过记录1");
        sadRecord1.setContent("今天心情不好");
        sadRecord1.setMood("难过");
        sadRecord1.setTags("难过,需要安慰");
        sadRecord1.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(sadRecord1);

        LifeRecord sadRecord2 = new LifeRecord();
        sadRecord2.setTitle("难过记录2");
        sadRecord2.setContent("今天又心情不好");
        sadRecord2.setMood("难过");
        sadRecord2.setTags("难过,需要安慰");
        sadRecord2.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(sadRecord2);

        List<LifeRecord> sadRecords = lifeRecordService.getLifeRecordsByMood("难过");
        Assertions.assertTrue(sadRecords.size() >= 2);
        sadRecords.forEach(r -> Assertions.assertEquals("难过", r.getMood()));
    }

    /**
     * 测试根据标签搜索生活记录 - 工作标签
     * 测试数据：创建多个包含工作标签的生活记录
     */
    @Test
    void testSearchLifeRecordsByTags1() {
        LifeRecord workRecord1 = new LifeRecord();
        workRecord1.setTitle("工作记录1");
        workRecord1.setContent("今天工作很忙");
        workRecord1.setMood("忙碌");
        workRecord1.setTags("工作,忙碌,加班");
        workRecord1.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(workRecord1);

        LifeRecord workRecord2 = new LifeRecord();
        workRecord2.setTitle("工作记录2");
        workRecord2.setContent("今天又工作很忙");
        workRecord2.setMood("忙碌");
        workRecord2.setTags("工作,忙碌,项目");
        workRecord2.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(workRecord2);

        List<LifeRecord> workRecords = lifeRecordService.searchLifeRecordsByTags("工作");
        Assertions.assertTrue(workRecords.size() >= 2);
        workRecords.forEach(r -> Assertions.assertTrue(r.getTags().contains("工作")));
    }

    /**
     * 测试根据标签搜索生活记录 - 生活标签
     * 测试数据：创建多个包含生活标签的生活记录
     */
    @Test
    void testSearchLifeRecordsByTags2() {
        LifeRecord lifeRecord1 = new LifeRecord();
        lifeRecord1.setTitle("生活记录1");
        lifeRecord1.setContent("今天生活很悠闲");
        lifeRecord1.setMood("悠闲");
        lifeRecord1.setTags("生活,悠闲,放松");
        lifeRecord1.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(lifeRecord1);

        LifeRecord lifeRecord2 = new LifeRecord();
        lifeRecord2.setTitle("生活记录2");
        lifeRecord2.setContent("今天又生活很悠闲");
        lifeRecord2.setMood("悠闲");
        lifeRecord2.setTags("生活,悠闲,享受");
        lifeRecord2.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(lifeRecord2);

        List<LifeRecord> lifeRecords = lifeRecordService.searchLifeRecordsByTags("生活");
        Assertions.assertTrue(lifeRecords.size() >= 2);
        lifeRecords.forEach(r -> Assertions.assertTrue(r.getTags().contains("生活")));
    }

    // ==================== 健康记录模块测试 ====================
    
    /**
     * 测试创建健康记录 - 跑步运动
     * 测试数据：体重=70.5kg, 身高=175cm, 运动="跑步", 时长=1小时
     */
    @Test
    void testCreateHealthRecord1() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.5"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("跑步");
        record.setExerciseDuration(1); // 1小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        record.setNotes("晨跑记录");
        
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(new BigDecimal("70.5"), saved.getWeight());
        Assertions.assertEquals("跑步", saved.getExerciseType());
    }

    /**
     * 测试创建健康记录 - 游泳运动
     * 测试数据：体重=69.5kg, 身高=175cm, 运动="游泳", 时长=2小时
     */
    @Test
    void testCreateHealthRecord2() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("69.5"));
        record.setHeight(175);
        record.setSystolicPressure(118);
        record.setDiastolicPressure(78);
        record.setHeartRate(70);
        record.setExerciseType("游泳");
        record.setExerciseDuration(2); // 2小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        record.setNotes("游泳训练");
        
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(new BigDecimal("69.5"), saved.getWeight());
        Assertions.assertEquals("游泳", saved.getExerciseType());
    }

    /**
     * 测试创建健康记录 - 健身运动
     * 测试数据：体重=71.0kg, 身高=175cm, 运动="健身", 时长=3小时
     */
    @Test
    void testCreateHealthRecord3() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("71.0"));
        record.setHeight(175);
        record.setSystolicPressure(125);
        record.setDiastolicPressure(85);
        record.setHeartRate(85);
        record.setExerciseType("健身");
        record.setExerciseDuration(3); // 3小时
        record.setSleepDuration(6);
        record.setRecordDate(LocalDateTime.now());
        record.setNotes("力量训练");
        
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(new BigDecimal("71.0"), saved.getWeight());
        Assertions.assertEquals("健身", saved.getExerciseType());
    }

    /**
     * 测试根据运动类型查询健康记录 - 跑步类型
     * 测试数据：创建多个跑步类型的健康记录
     */
    @Test
    void testGetHealthRecordsByExerciseType1() {
        HealthRecord runningRecord1 = new HealthRecord();
        runningRecord1.setWeight(new BigDecimal("70.0"));
        runningRecord1.setHeight(175);
        runningRecord1.setSystolicPressure(120);
        runningRecord1.setDiastolicPressure(80);
        runningRecord1.setHeartRate(75);
        runningRecord1.setExerciseType("跑步");
        runningRecord1.setExerciseDuration(1); // 1小时
        runningRecord1.setSleepDuration(8);
        runningRecord1.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(runningRecord1);

        HealthRecord runningRecord2 = new HealthRecord();
        runningRecord2.setWeight(new BigDecimal("70.5"));
        runningRecord2.setHeight(175);
        runningRecord2.setSystolicPressure(122);
        runningRecord2.setDiastolicPressure(82);
        runningRecord2.setHeartRate(78);
        runningRecord2.setExerciseType("跑步");
        runningRecord2.setExerciseDuration(2); // 2小时
        runningRecord2.setSleepDuration(7);
        runningRecord2.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(runningRecord2);

        List<HealthRecord> runningRecords = healthRecordService.getHealthRecordsByExerciseType("跑步");
        Assertions.assertTrue(runningRecords.size() >= 2);
        runningRecords.forEach(r -> Assertions.assertEquals("跑步", r.getExerciseType()));
    }

    /**
     * 测试根据运动类型查询健康记录 - 游泳类型
     * 测试数据：创建多个游泳类型的健康记录
     */
    @Test
    void testGetHealthRecordsByExerciseType2() {
        HealthRecord swimmingRecord1 = new HealthRecord();
        swimmingRecord1.setWeight(new BigDecimal("69.5"));
        swimmingRecord1.setHeight(175);
        swimmingRecord1.setSystolicPressure(118);
        swimmingRecord1.setDiastolicPressure(78);
        swimmingRecord1.setHeartRate(70);
        swimmingRecord1.setExerciseType("游泳");
        swimmingRecord1.setExerciseDuration(2); // 2小时
        swimmingRecord1.setSleepDuration(8);
        swimmingRecord1.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(swimmingRecord1);

        HealthRecord swimmingRecord2 = new HealthRecord();
        swimmingRecord2.setWeight(new BigDecimal("69.0"));
        swimmingRecord2.setHeight(175);
        swimmingRecord2.setSystolicPressure(116);
        swimmingRecord2.setDiastolicPressure(76);
        swimmingRecord2.setHeartRate(68);
        swimmingRecord2.setExerciseType("游泳");
        swimmingRecord2.setExerciseDuration(3); // 3小时
        swimmingRecord2.setSleepDuration(8);
        swimmingRecord2.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(swimmingRecord2);

        List<HealthRecord> swimmingRecords = healthRecordService.getHealthRecordsByExerciseType("游泳");
        Assertions.assertTrue(swimmingRecords.size() >= 2);
        swimmingRecords.forEach(r -> Assertions.assertEquals("游泳", r.getExerciseType()));
    }

    // ==================== 异常处理测试 ====================
    
    /**
     * 测试支出记录数据验证异常 - 缺少必填字段
     * 测试数据：不设置任何必填字段
     */
    @Test
    void testExpenseValidationException1() {
        Expense expense = new Expense();
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            expenseService.createExpense(expense);
        });
    }

    /**
     * 测试收入记录数据验证异常 - 缺少必填字段
     * 测试数据：不设置任何必填字段
     */
    @Test
    void testIncomeValidationException1() {
        Income income = new Income();
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            incomeService.createIncome(income);
        });
    }

    /**
     * 测试生活记录数据验证异常 - 缺少必填字段
     * 测试数据：不设置任何必填字段
     */
    @Test
    void testLifeRecordValidationException1() {
        LifeRecord record = new LifeRecord();
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            lifeRecordService.createLifeRecord(record);
        });
    }

    /**
     * 测试健康记录数据验证异常 - 无效体重值
     * 测试数据：设置超过最大值的体重(500.0kg)
     */
    @Test
    void testHealthRecordValidationException1() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("500.0")); // 超过最大值300
        record.setRecordDate(LocalDateTime.now());
        
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            healthRecordService.createHealthRecord(record);
        });
    }

    // ==================== 边界值测试 ====================
    
    /**
     * 测试支出金额边界值 - 零金额
     * 测试数据：金额=0.00
     */
    @Test
    void testExpenseAmountBoundary1() {
        Expense expense = new Expense();
        expense.setDescription("零金额支出");
        expense.setAmount(BigDecimal.ZERO);
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            expenseService.createExpense(expense);
        });
    }

    /**
     * 测试支出金额边界值 - 最大金额
     * 测试数据：金额=999999.99
     */
    @Test
    void testExpenseAmountBoundary2() {
        Expense expense = new Expense();
        expense.setDescription("最大金额支出");
        expense.setAmount(new BigDecimal("999999.99"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertEquals(new BigDecimal("999999.99"), saved.getAmount());
    }

    /**
     * 测试健康记录边界值 - 最大体重
     * 测试数据：体重=200.0kg, 身高=250cm, 运动时长=24小时
     */
    @Test
    void testHealthRecordBoundary1() {
        HealthRecord record = new HealthRecord();
        record.setRecordDate(LocalDateTime.now());
        record.setWeight(new BigDecimal("200.0")); // 最大体重
        record.setHeight(250); // 最大身高
        record.setSystolicPressure(200); // 最大收缩压
        record.setDiastolicPressure(120); // 最大舒张压
        record.setHeartRate(200); // 最大心率
        record.setExerciseDuration(24); // 最大运动时长
        record.setExerciseType("马拉松");
        record.setSleepDuration(24); // 最大睡眠时长
        
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertEquals(250, saved.getHeight());
        Assertions.assertEquals(24, saved.getExerciseDuration());
        Assertions.assertEquals(new BigDecimal("200.0"), saved.getWeight());
    }

    /**
     * 测试健康记录边界值 - 最小体重
     * 测试数据：体重=30.0kg, 身高=100cm, 运动时长=0小时
     */
    @Test
    void testHealthRecordBoundary2() {
        HealthRecord record = new HealthRecord();
        record.setRecordDate(LocalDateTime.now());
        record.setWeight(new BigDecimal("30.0")); // 最小体重
        record.setHeight(100); // 最小身高
        record.setSystolicPressure(50); // 最小收缩压
        record.setDiastolicPressure(30); // 最小舒张压
        record.setHeartRate(40); // 最小心率
        record.setExerciseDuration(0); // 最小运动时长
        record.setExerciseType("休息");
        record.setSleepDuration(0); // 最小睡眠时长
        
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertEquals(100, saved.getHeight());
        Assertions.assertEquals(0, saved.getExerciseDuration());
        Assertions.assertEquals(new BigDecimal("30.0"), saved.getWeight());
    }

    // ==================== 集成测试 ====================
    
    /**
     * 测试完整的业务流程 - 正常流程
     * 测试数据：创建收入、支出、生活记录、健康记录各一条
     */
    @Test
    void testCompleteWorkflow1() {
        // 1. 创建收入
        Income income = new Income();
        income.setDescription("工资");
        income.setAmount(new BigDecimal("5000.00"));
        income.setSource("公司");
        income.setIncomeDate(LocalDateTime.now());
        Income savedIncome = incomeService.createIncome(income);
        
        // 2. 创建支出
        Expense expense = new Expense();
        expense.setDescription("购物");
        expense.setAmount(new BigDecimal("100.00"));
        expense.setCategory("日常");
        expense.setExpenseDate(LocalDateTime.now());
        Expense savedExpense = expenseService.createExpense(expense);
        
        // 3. 创建生活记录
        LifeRecord lifeRecord = new LifeRecord();
        lifeRecord.setTitle("美好的一天");
        lifeRecord.setContent("今天很开心");
        lifeRecord.setMood("开心");
        lifeRecord.setTags("美好,生活");
        lifeRecord.setRecordDate(LocalDateTime.now());
        LifeRecord savedLifeRecord = lifeRecordService.createLifeRecord(lifeRecord);
        
        // 4. 创建健康记录
        HealthRecord healthRecord = new HealthRecord();
        healthRecord.setRecordDate(LocalDateTime.now());
        healthRecord.setWeight(new BigDecimal("65.0"));
        healthRecord.setHeight(170);
        healthRecord.setSystolicPressure(120);
        healthRecord.setDiastolicPressure(80);
        healthRecord.setHeartRate(70);
        healthRecord.setExerciseDuration(1);
        healthRecord.setExerciseType("散步");
        healthRecord.setSleepDuration(8);
        HealthRecord savedHealthRecord = healthRecordService.createHealthRecord(healthRecord);
        
        // 5. 验证所有记录都已创建
        Assertions.assertNotNull(savedIncome.getId());
        Assertions.assertNotNull(savedExpense.getId());
        Assertions.assertNotNull(savedLifeRecord.getId());
        Assertions.assertNotNull(savedHealthRecord.getId());
        
        // 6. 验证统计数据
        Assertions.assertTrue(incomeService.getCurrentMonthTotalIncome().compareTo(BigDecimal.ZERO) > 0);
        Assertions.assertTrue(expenseService.getCurrentMonthTotalExpense().compareTo(BigDecimal.ZERO) > 0);
        Assertions.assertTrue(lifeRecordService.getCurrentMonthRecordCount() > 0);
        Assertions.assertNotNull(healthRecordService.getCurrentMonthAverageWeight());
    }

    /**
     * 测试完整的业务流程 - 大数据量
     * 测试数据：创建多条不同类型的记录
     */
    @Test
    void testCompleteWorkflow2() {
        // 创建多条收入记录
        for (int i = 1; i <= 3; i++) {
            Income income = new Income();
            income.setDescription("收入" + i);
            income.setAmount(new BigDecimal(1000 * i));
            income.setSource("来源" + i);
            income.setIncomeDate(LocalDateTime.now());
            incomeService.createIncome(income);
        }
        
        // 创建多条支出记录
        for (int i = 1; i <= 3; i++) {
            Expense expense = new Expense();
            expense.setDescription("支出" + i);
            expense.setAmount(new BigDecimal(100 * i));
            expense.setCategory("类别" + i);
            expense.setExpenseDate(LocalDateTime.now());
            expenseService.createExpense(expense);
        }
        
        // 创建多条生活记录
        for (int i = 1; i <= 3; i++) {
            LifeRecord record = new LifeRecord();
            record.setTitle("记录" + i);
            record.setContent("内容" + i);
            record.setMood("心情" + i);
            record.setTags("标签" + i);
            record.setRecordDate(LocalDateTime.now());
            lifeRecordService.createLifeRecord(record);
        }
        
        // 创建多条健康记录
        for (int i = 1; i <= 3; i++) {
            HealthRecord record = new HealthRecord();
            record.setRecordDate(LocalDateTime.now());
            record.setWeight(new BigDecimal(60 + i));
            record.setHeight(170 + i);
            record.setSystolicPressure(120 + i);
            record.setDiastolicPressure(80 + i);
            record.setHeartRate(70 + i);
            record.setExerciseDuration(i);
            record.setExerciseType("运动" + i);
            record.setSleepDuration(7 + i);
            healthRecordService.createHealthRecord(record);
        }
        
        // 验证统计数据
        Assertions.assertTrue(incomeService.getCurrentMonthTotalIncome().compareTo(new BigDecimal("6000")) >= 0);
        Assertions.assertTrue(expenseService.getCurrentMonthTotalExpense().compareTo(new BigDecimal("600")) >= 0);
        Assertions.assertTrue(lifeRecordService.getCurrentMonthRecordCount() >= 3);
        Assertions.assertNotNull(healthRecordService.getCurrentMonthAverageWeight());
    }
}
