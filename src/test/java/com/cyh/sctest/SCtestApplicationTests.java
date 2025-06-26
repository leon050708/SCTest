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
import org.springframework.dao.DataIntegrityViolationException;
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
    
    @Test
    void testCreateAndGetExpense() {
        Expense expense = new Expense();
        expense.setDescription("测试支出");
        expense.setAmount(new BigDecimal("10.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("单元测试");
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("测试支出", saved.getDescription());
        List<Expense> all = expenseService.getAllExpenses();
        Assertions.assertTrue(all.size() > 0);
    }

    @Test
    void testCreateExpenseWithLargeAmount() {
        Expense expense = new Expense();
        expense.setDescription("大额支出");
        expense.setAmount(new BigDecimal("999999.99"));
        expense.setCategory("大额消费");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("大额支出测试");
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertEquals(new BigDecimal("999999.99"), saved.getAmount());
    }

    @Test
    void testCreateExpenseWithSmallAmount() {
        Expense expense = new Expense();
        expense.setDescription("小额支出");
        expense.setAmount(new BigDecimal("0.01"));
        expense.setCategory("小额消费");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("小额支出测试");
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertEquals(new BigDecimal("0.01"), saved.getAmount());
    }

    @Test
    void testCreateExpenseWithSpecialCharacters() {
        Expense expense = new Expense();
        expense.setDescription("特殊字符支出@#$%");
        expense.setAmount(new BigDecimal("100.00"));
        expense.setCategory("特殊测试");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("包含特殊字符的测试");
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertEquals("特殊字符支出@#$%", saved.getDescription());
    }

    @Test
    void testCreateExpenseWithLongDescription() {
        Expense expense = new Expense();
        expense.setDescription("这是一个非常长的支出描述，用来测试系统对长文本的处理能力，确保系统能够正确处理超长文本输入，这个描述应该足够长来测试系统的处理能力");
        expense.setAmount(new BigDecimal("50.00"));
        expense.setCategory("长文本测试");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("长描述测试");
        Expense saved = expenseService.createExpense(expense);
        Assertions.assertTrue(saved.getDescription().length() > 50);
    }

    @Test
    void testGetExpenseById() {
        Expense expense = new Expense();
        expense.setDescription("测试支出");
        expense.setAmount(new BigDecimal("10.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        Expense saved = expenseService.createExpense(expense);
        
        Optional<Expense> found = expenseService.getExpenseById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void testGetExpenseByIdNotFound() {
        Optional<Expense> found = expenseService.getExpenseById(99999L);
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testUpdateExpense() {
        Expense expense = new Expense();
        expense.setDescription("原始支出");
        expense.setAmount(new BigDecimal("10.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        Expense saved = expenseService.createExpense(expense);
        
        saved.setDescription("更新后的支出");
        saved.setAmount(new BigDecimal("20.00"));
        Expense updated = expenseService.updateExpense(saved.getId(), saved);
        Assertions.assertEquals("更新后的支出", updated.getDescription());
        Assertions.assertEquals(new BigDecimal("20.00"), updated.getAmount());
    }

    @Test
    void testUpdateExpenseWithAllFields() {
        Expense expense = new Expense();
        expense.setDescription("原始支出");
        expense.setAmount(new BigDecimal("10.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        expense.setNotes("原始备注");
        Expense saved = expenseService.createExpense(expense);
        
        saved.setDescription("全面更新支出");
        saved.setAmount(new BigDecimal("30.00"));
        saved.setCategory("更新类别");
        saved.setNotes("更新后的备注");
        Expense updated = expenseService.updateExpense(saved.getId(), saved);
        Assertions.assertEquals("全面更新支出", updated.getDescription());
        Assertions.assertEquals(new BigDecimal("30.00"), updated.getAmount());
        Assertions.assertEquals("更新类别", updated.getCategory());
        Assertions.assertEquals("更新后的备注", updated.getNotes());
    }

    @Test
    void testDeleteExpense() {
        Expense expense = new Expense();
        expense.setDescription("待删除支出");
        expense.setAmount(new BigDecimal("10.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        Expense saved = expenseService.createExpense(expense);
        
        expenseService.deleteExpense(saved.getId());
        Optional<Expense> found = expenseService.getExpenseById(saved.getId());
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testGetExpensesByCategory() {
        Expense expense = new Expense();
        expense.setDescription("餐饮支出");
        expense.setAmount(new BigDecimal("50.00"));
        expense.setCategory("餐饮");
        expense.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense);
        
        List<Expense> expenses = expenseService.getExpensesByCategory("餐饮");
        Assertions.assertTrue(expenses.size() > 0);
        expenses.forEach(e -> Assertions.assertEquals("餐饮", e.getCategory()));
    }

    @Test
    void testGetExpensesByCategoryNotFound() {
        List<Expense> expenses = expenseService.getExpensesByCategory("不存在的类别");
        Assertions.assertEquals(0, expenses.size());
    }

    @Test
    void testGetExpensesByMultipleCategories() {
        // 创建不同类别的支出
        Expense expense1 = new Expense();
        expense1.setDescription("餐饮支出1");
        expense1.setAmount(new BigDecimal("30.00"));
        expense1.setCategory("餐饮");
        expense1.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense1);

        Expense expense2 = new Expense();
        expense2.setDescription("交通支出1");
        expense2.setAmount(new BigDecimal("20.00"));
        expense2.setCategory("交通");
        expense2.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense2);

        Expense expense3 = new Expense();
        expense3.setDescription("餐饮支出2");
        expense3.setAmount(new BigDecimal("40.00"));
        expense3.setCategory("餐饮");
        expense3.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense3);

        List<Expense> foodExpenses = expenseService.getExpensesByCategory("餐饮");
        List<Expense> transportExpenses = expenseService.getExpensesByCategory("交通");
        
        Assertions.assertTrue(foodExpenses.size() >= 2);
        Assertions.assertTrue(transportExpenses.size() >= 1);
    }

    @Test
    void testGetExpensesByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        List<Expense> expenses = expenseService.getExpensesByDateRange(start, end);
        Assertions.assertNotNull(expenses);
    }

    @Test
    void testGetExpensesByDateRangeWithSpecificDates() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime tomorrow = now.plusDays(1);
        
        // 创建昨天的支出
        Expense pastExpense = new Expense();
        pastExpense.setDescription("昨天的支出");
        pastExpense.setAmount(new BigDecimal("25.00"));
        pastExpense.setCategory("测试");
        pastExpense.setExpenseDate(yesterday);
        expenseService.createExpense(pastExpense);
        
        // 创建明天的支出
        Expense futureExpense = new Expense();
        futureExpense.setDescription("明天的支出");
        futureExpense.setAmount(new BigDecimal("35.00"));
        futureExpense.setCategory("测试");
        futureExpense.setExpenseDate(tomorrow);
        expenseService.createExpense(futureExpense);
        
        List<Expense> expenses = expenseService.getExpensesByDateRange(yesterday, tomorrow);
        Assertions.assertTrue(expenses.size() >= 2);
    }

    @Test
    void testGetExpensesByDateRangeEmpty() {
        LocalDateTime farPast = LocalDateTime.now().minusYears(10);
        LocalDateTime farFuture = LocalDateTime.now().plusYears(10);
        List<Expense> expenses = expenseService.getExpensesByDateRange(farPast, farFuture);
        Assertions.assertNotNull(expenses);
    }

    @Test
    void testExpenseStatistics() {
        BigDecimal monthTotal = expenseService.getCurrentMonthTotalExpense();
        Assertions.assertNotNull(monthTotal);
        
        BigDecimal weekTotal = expenseService.getCurrentWeekTotalExpense();
        Assertions.assertNotNull(weekTotal);
        
        Map<String, BigDecimal> categoryStats = expenseService.getExpenseStatisticsByCategory();
        Assertions.assertNotNull(categoryStats);
    }

    @Test
    void testExpenseStatisticsWithMultipleCategories() {
        // 创建不同类别的支出
        Expense expense1 = new Expense();
        expense1.setDescription("餐饮支出");
        expense1.setAmount(new BigDecimal("100.00"));
        expense1.setCategory("餐饮");
        expense1.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense1);

        Expense expense2 = new Expense();
        expense2.setDescription("交通支出");
        expense2.setAmount(new BigDecimal("50.00"));
        expense2.setCategory("交通");
        expense2.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense2);

        Expense expense3 = new Expense();
        expense3.setDescription("购物支出");
        expense3.setAmount(new BigDecimal("200.00"));
        expense3.setCategory("购物");
        expense3.setExpenseDate(LocalDateTime.now());
        expenseService.createExpense(expense3);

        Map<String, BigDecimal> categoryStats = expenseService.getExpenseStatisticsByCategory();
        Assertions.assertTrue(categoryStats.containsKey("餐饮"));
        Assertions.assertTrue(categoryStats.containsKey("交通"));
        Assertions.assertTrue(categoryStats.containsKey("购物"));
    }

    // ==================== 收入模块测试 ====================
    
    @Test
    void testCreateAndGetIncome() {
        Income income = new Income();
        income.setDescription("测试收入");
        income.setAmount(new BigDecimal("100.00"));
        income.setSource("测试");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("单元测试");
        Income saved = incomeService.createIncome(income);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("测试收入", saved.getDescription());
        List<Income> all = incomeService.getAllIncomes();
        Assertions.assertTrue(all.size() > 0);
    }

    @Test
    void testCreateIncomeWithLargeAmount() {
        Income income = new Income();
        income.setDescription("大额收入");
        income.setAmount(new BigDecimal("999999.99"));
        income.setSource("大额收入");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("大额收入测试");
        Income saved = incomeService.createIncome(income);
        Assertions.assertEquals(new BigDecimal("999999.99"), saved.getAmount());
    }

    @Test
    void testCreateIncomeWithSmallAmount() {
        Income income = new Income();
        income.setDescription("小额收入");
        income.setAmount(new BigDecimal("0.01"));
        income.setSource("小额收入");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("小额收入测试");
        Income saved = incomeService.createIncome(income);
        Assertions.assertEquals(new BigDecimal("0.01"), saved.getAmount());
    }

    @Test
    void testCreateIncomeWithDifferentSources() {
        // 测试工资收入
        Income salaryIncome = new Income();
        salaryIncome.setDescription("月薪");
        salaryIncome.setAmount(new BigDecimal("8000.00"));
        salaryIncome.setSource("工资");
        salaryIncome.setIncomeDate(LocalDateTime.now());
        salaryIncome.setNotes("固定工资");
        Income savedSalary = incomeService.createIncome(salaryIncome);
        Assertions.assertEquals("工资", savedSalary.getSource());

        // 测试兼职收入
        Income partTimeIncome = new Income();
        partTimeIncome.setDescription("兼职收入");
        partTimeIncome.setAmount(new BigDecimal("500.00"));
        partTimeIncome.setSource("兼职");
        partTimeIncome.setIncomeDate(LocalDateTime.now());
        partTimeIncome.setNotes("周末兼职");
        Income savedPartTime = incomeService.createIncome(partTimeIncome);
        Assertions.assertEquals("兼职", savedPartTime.getSource());

        // 测试投资收入
        Income investmentIncome = new Income();
        investmentIncome.setDescription("股票收益");
        investmentIncome.setAmount(new BigDecimal("1200.00"));
        investmentIncome.setSource("投资");
        investmentIncome.setIncomeDate(LocalDateTime.now());
        investmentIncome.setNotes("股票分红");
        Income savedInvestment = incomeService.createIncome(investmentIncome);
        Assertions.assertEquals("投资", savedInvestment.getSource());
    }

    @Test
    void testCreateIncomeWithSpecialCharacters() {
        Income income = new Income();
        income.setDescription("特殊字符收入@#$%");
        income.setAmount(new BigDecimal("1000.00"));
        income.setSource("特殊测试");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("包含特殊字符的测试");
        Income saved = incomeService.createIncome(income);
        Assertions.assertEquals("特殊字符收入@#$%", saved.getDescription());
    }

    @Test
    void testGetIncomeById() {
        Income income = new Income();
        income.setDescription("测试收入");
        income.setAmount(new BigDecimal("100.00"));
        income.setSource("测试");
        income.setIncomeDate(LocalDateTime.now());
        Income saved = incomeService.createIncome(income);
        
        Optional<Income> found = incomeService.getIncomeById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void testGetIncomeByIdNotFound() {
        Optional<Income> found = incomeService.getIncomeById(99999L);
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testUpdateIncome() {
        Income income = new Income();
        income.setDescription("原始收入");
        income.setAmount(new BigDecimal("100.00"));
        income.setSource("测试");
        income.setIncomeDate(LocalDateTime.now());
        Income saved = incomeService.createIncome(income);
        
        saved.setDescription("更新后的收入");
        saved.setAmount(new BigDecimal("200.00"));
        Income updated = incomeService.updateIncome(saved.getId(), saved);
        Assertions.assertEquals("更新后的收入", updated.getDescription());
        Assertions.assertEquals(new BigDecimal("200.00"), updated.getAmount());
    }

    @Test
    void testUpdateIncomeWithAllFields() {
        Income income = new Income();
        income.setDescription("原始收入");
        income.setAmount(new BigDecimal("100.00"));
        income.setSource("测试");
        income.setIncomeDate(LocalDateTime.now());
        income.setNotes("原始备注");
        Income saved = incomeService.createIncome(income);
        
        saved.setDescription("全面更新收入");
        saved.setAmount(new BigDecimal("300.00"));
        saved.setSource("更新来源");
        saved.setNotes("更新后的备注");
        Income updated = incomeService.updateIncome(saved.getId(), saved);
        Assertions.assertEquals("全面更新收入", updated.getDescription());
        Assertions.assertEquals(new BigDecimal("300.00"), updated.getAmount());
        Assertions.assertEquals("更新来源", updated.getSource());
        Assertions.assertEquals("更新后的备注", updated.getNotes());
    }

    @Test
    void testDeleteIncome() {
        Income income = new Income();
        income.setDescription("待删除收入");
        income.setAmount(new BigDecimal("100.00"));
        income.setSource("测试");
        income.setIncomeDate(LocalDateTime.now());
        Income saved = incomeService.createIncome(income);
        
        incomeService.deleteIncome(saved.getId());
        Optional<Income> found = incomeService.getIncomeById(saved.getId());
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testGetIncomesBySource() {
        Income income = new Income();
        income.setDescription("工资收入");
        income.setAmount(new BigDecimal("5000.00"));
        income.setSource("工资");
        income.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income);
        
        List<Income> incomes = incomeService.getIncomesBySource("工资");
        Assertions.assertTrue(incomes.size() > 0);
        incomes.forEach(i -> Assertions.assertEquals("工资", i.getSource()));
    }

    @Test
    void testGetIncomesBySourceNotFound() {
        List<Income> incomes = incomeService.getIncomesBySource("不存在的来源");
        Assertions.assertEquals(0, incomes.size());
    }

    @Test
    void testGetIncomesByMultipleSources() {
        // 创建不同来源的收入
        Income income1 = new Income();
        income1.setDescription("工资收入1");
        income1.setAmount(new BigDecimal("5000.00"));
        income1.setSource("工资");
        income1.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income1);

        Income income2 = new Income();
        income2.setDescription("兼职收入1");
        income2.setAmount(new BigDecimal("1000.00"));
        income2.setSource("兼职");
        income2.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income2);

        Income income3 = new Income();
        income3.setDescription("工资收入2");
        income3.setAmount(new BigDecimal("6000.00"));
        income3.setSource("工资");
        income3.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income3);

        List<Income> salaryIncomes = incomeService.getIncomesBySource("工资");
        List<Income> partTimeIncomes = incomeService.getIncomesBySource("兼职");
        
        Assertions.assertTrue(salaryIncomes.size() >= 2);
        Assertions.assertTrue(partTimeIncomes.size() >= 1);
    }

    @Test
    void testGetIncomesByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        List<Income> incomes = incomeService.getIncomesByDateRange(start, end);
        Assertions.assertNotNull(incomes);
    }

    @Test
    void testGetIncomesByDateRangeWithSpecificDates() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime tomorrow = now.plusDays(1);
        
        // 创建昨天的收入
        Income pastIncome = new Income();
        pastIncome.setDescription("昨天的收入");
        pastIncome.setAmount(new BigDecimal("1000.00"));
        pastIncome.setSource("测试");
        pastIncome.setIncomeDate(yesterday);
        incomeService.createIncome(pastIncome);
        
        // 创建明天的收入
        Income futureIncome = new Income();
        futureIncome.setDescription("明天的收入");
        futureIncome.setAmount(new BigDecimal("2000.00"));
        futureIncome.setSource("测试");
        futureIncome.setIncomeDate(tomorrow);
        incomeService.createIncome(futureIncome);
        
        List<Income> incomes = incomeService.getIncomesByDateRange(yesterday, tomorrow);
        Assertions.assertTrue(incomes.size() >= 2);
    }

    @Test
    void testIncomeStatistics() {
        BigDecimal monthTotal = incomeService.getCurrentMonthTotalIncome();
        Assertions.assertNotNull(monthTotal);
        
        BigDecimal weekTotal = incomeService.getCurrentWeekTotalIncome();
        Assertions.assertNotNull(weekTotal);
        
        Map<String, BigDecimal> sourceStats = incomeService.getIncomeStatisticsBySource();
        Assertions.assertNotNull(sourceStats);
    }

    @Test
    void testIncomeStatisticsWithMultipleSources() {
        // 创建不同来源的收入
        Income income1 = new Income();
        income1.setDescription("工资收入");
        income1.setAmount(new BigDecimal("8000.00"));
        income1.setSource("工资");
        income1.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income1);

        Income income2 = new Income();
        income2.setDescription("兼职收入");
        income2.setAmount(new BigDecimal("1000.00"));
        income2.setSource("兼职");
        income2.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income2);

        Income income3 = new Income();
        income3.setDescription("投资收入");
        income3.setAmount(new BigDecimal("2000.00"));
        income3.setSource("投资");
        income3.setIncomeDate(LocalDateTime.now());
        incomeService.createIncome(income3);

        Map<String, BigDecimal> sourceStats = incomeService.getIncomeStatisticsBySource();
        Assertions.assertTrue(sourceStats.containsKey("工资"));
        Assertions.assertTrue(sourceStats.containsKey("兼职"));
        Assertions.assertTrue(sourceStats.containsKey("投资"));
    }

    // ==================== 生活记录模块测试 ====================
    
    @Test
    void testCreateAndGetLifeRecord() {
        LifeRecord record = new LifeRecord();
        record.setTitle("测试生活记录");
        record.setContent("今天很开心");
        record.setMood("开心");
        record.setTags("测试,生活");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals("测试生活记录", saved.getTitle());
        List<LifeRecord> all = lifeRecordService.getAllLifeRecords();
        Assertions.assertTrue(all.size() > 0);
    }

    @Test
    void testCreateLifeRecordWithDifferentMoods() {
        // 测试开心心情
        LifeRecord happyRecord = new LifeRecord();
        happyRecord.setTitle("开心的一天");
        happyRecord.setContent("今天心情很好");
        happyRecord.setMood("开心");
        happyRecord.setTags("开心,美好");
        happyRecord.setRecordDate(LocalDateTime.now());
        LifeRecord savedHappy = lifeRecordService.createLifeRecord(happyRecord);
        Assertions.assertEquals("开心", savedHappy.getMood());

        // 测试平静心情
        LifeRecord calmRecord = new LifeRecord();
        calmRecord.setTitle("平静的一天");
        calmRecord.setContent("今天很平静");
        calmRecord.setMood("平静");
        calmRecord.setTags("平静,放松");
        calmRecord.setRecordDate(LocalDateTime.now());
        LifeRecord savedCalm = lifeRecordService.createLifeRecord(calmRecord);
        Assertions.assertEquals("平静", savedCalm.getMood());

        // 测试难过心情
        LifeRecord sadRecord = new LifeRecord();
        sadRecord.setTitle("难过的一天");
        sadRecord.setContent("今天心情不好");
        sadRecord.setMood("难过");
        sadRecord.setTags("难过,需要安慰");
        sadRecord.setRecordDate(LocalDateTime.now());
        LifeRecord savedSad = lifeRecordService.createLifeRecord(sadRecord);
        Assertions.assertEquals("难过", savedSad.getMood());
    }

    @Test
    void testCreateLifeRecordWithLongContent() {
        LifeRecord record = new LifeRecord();
        record.setTitle("长内容记录");
        record.setContent("这是一个非常长的生活记录内容，用来测试系统对长文本的处理能力。" +
                "今天发生了很多事情，包括早上起床、吃早餐、上班、开会、吃午餐、继续工作、" +
                "下班回家、吃晚餐、看电视、洗澡、睡觉等。这一天过得很充实，虽然有些累，" +
                "但是很有成就感。希望明天也能保持这样的状态。");
        record.setMood("充实");
        record.setTags("充实,忙碌,有成就感");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        Assertions.assertTrue(saved.getContent().length() > 100);
    }

    @Test
    void testCreateLifeRecordWithSpecialCharacters() {
        LifeRecord record = new LifeRecord();
        record.setTitle("特殊字符记录@#$%");
        record.setContent("包含特殊字符的内容@#$%^&*()");
        record.setMood("特殊");
        record.setTags("特殊,测试,@#$%");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        Assertions.assertEquals("特殊字符记录@#$%", saved.getTitle());
        Assertions.assertEquals("包含特殊字符的内容@#$%^&*()", saved.getContent());
    }

    @Test
    void testCreateLifeRecordWithMultipleTags() {
        LifeRecord record = new LifeRecord();
        record.setTitle("多标签记录");
        record.setContent("这是一个包含多个标签的记录");
        record.setMood("复杂");
        record.setTags("工作,生活,学习,娱乐,运动,美食,旅行,朋友,家庭,健康");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        Assertions.assertTrue(saved.getTags().split(",").length >= 10);
    }

    @Test
    void testGetLifeRecordById() {
        LifeRecord record = new LifeRecord();
        record.setTitle("测试生活记录");
        record.setContent("今天很开心");
        record.setMood("开心");
        record.setTags("测试,生活");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        
        Optional<LifeRecord> found = lifeRecordService.getLifeRecordById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void testGetLifeRecordByIdNotFound() {
        Optional<LifeRecord> found = lifeRecordService.getLifeRecordById(99999L);
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testUpdateLifeRecord() {
        LifeRecord record = new LifeRecord();
        record.setTitle("原始记录");
        record.setContent("原始内容");
        record.setMood("原始");
        record.setTags("原始,测试");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        
        saved.setTitle("更新后的记录");
        saved.setContent("更新后的内容");
        LifeRecord updated = lifeRecordService.updateLifeRecord(saved.getId(), saved);
        Assertions.assertEquals("更新后的记录", updated.getTitle());
        Assertions.assertEquals("更新后的内容", updated.getContent());
    }

    @Test
    void testUpdateLifeRecordWithAllFields() {
        LifeRecord record = new LifeRecord();
        record.setTitle("原始记录");
        record.setContent("原始内容");
        record.setMood("原始");
        record.setTags("原始,测试");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        
        saved.setTitle("全面更新记录");
        saved.setContent("全面更新内容");
        saved.setMood("更新心情");
        saved.setTags("更新,标签,测试");
        LifeRecord updated = lifeRecordService.updateLifeRecord(saved.getId(), saved);
        Assertions.assertEquals("全面更新记录", updated.getTitle());
        Assertions.assertEquals("全面更新内容", updated.getContent());
        Assertions.assertEquals("更新心情", updated.getMood());
        Assertions.assertEquals("更新,标签,测试", updated.getTags());
    }

    @Test
    void testDeleteLifeRecord() {
        LifeRecord record = new LifeRecord();
        record.setTitle("待删除记录");
        record.setContent("待删除内容");
        record.setMood("待删除");
        record.setTags("待删除,测试");
        record.setRecordDate(LocalDateTime.now());
        LifeRecord saved = lifeRecordService.createLifeRecord(record);
        
        lifeRecordService.deleteLifeRecord(saved.getId());
        Optional<LifeRecord> found = lifeRecordService.getLifeRecordById(saved.getId());
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testGetLifeRecordsByMood() {
        LifeRecord record = new LifeRecord();
        record.setTitle("开心记录");
        record.setContent("今天很开心");
        record.setMood("开心");
        record.setTags("开心,美好");
        record.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(record);
        
        List<LifeRecord> records = lifeRecordService.getLifeRecordsByMood("开心");
        Assertions.assertTrue(records.size() > 0);
        records.forEach(r -> Assertions.assertEquals("开心", r.getMood()));
    }

    @Test
    void testGetLifeRecordsByMoodNotFound() {
        List<LifeRecord> records = lifeRecordService.getLifeRecordsByMood("不存在的心情");
        Assertions.assertEquals(0, records.size());
    }

    @Test
    void testGetLifeRecordsByMultipleMoods() {
        // 创建不同心情的记录
        LifeRecord happyRecord = new LifeRecord();
        happyRecord.setTitle("开心记录1");
        happyRecord.setContent("今天很开心");
        happyRecord.setMood("开心");
        happyRecord.setTags("开心,美好");
        happyRecord.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(happyRecord);

        LifeRecord sadRecord = new LifeRecord();
        sadRecord.setTitle("难过记录1");
        sadRecord.setContent("今天心情不好");
        sadRecord.setMood("难过");
        sadRecord.setTags("难过,需要安慰");
        sadRecord.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(sadRecord);

        LifeRecord happyRecord2 = new LifeRecord();
        happyRecord2.setTitle("开心记录2");
        happyRecord2.setContent("今天又很开心");
        happyRecord2.setMood("开心");
        happyRecord2.setTags("开心,美好");
        happyRecord2.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(happyRecord2);

        List<LifeRecord> happyRecords = lifeRecordService.getLifeRecordsByMood("开心");
        List<LifeRecord> sadRecords = lifeRecordService.getLifeRecordsByMood("难过");
        
        Assertions.assertTrue(happyRecords.size() >= 2);
        Assertions.assertTrue(sadRecords.size() >= 1);
    }

    @Test
    void testGetLifeRecordsByTags() {
        LifeRecord record = new LifeRecord();
        record.setTitle("工作记录");
        record.setContent("今天工作很忙");
        record.setMood("忙碌");
        record.setTags("工作,忙碌,加班");
        record.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(record);
        
        List<LifeRecord> records = lifeRecordService.searchLifeRecordsByTags("工作");
        Assertions.assertTrue(records.size() > 0);
        records.forEach(r -> Assertions.assertTrue(r.getTags().contains("工作")));
    }

    @Test
    void testGetLifeRecordsByTagsNotFound() {
        List<LifeRecord> records = lifeRecordService.searchLifeRecordsByTags("不存在的标签");
        Assertions.assertEquals(0, records.size());
    }

    @Test
    void testGetLifeRecordsByMultipleTags() {
        // 创建不同标签的记录
        LifeRecord workRecord = new LifeRecord();
        workRecord.setTitle("工作记录1");
        workRecord.setContent("今天工作很忙");
        workRecord.setMood("忙碌");
        workRecord.setTags("工作,忙碌,加班");
        workRecord.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(workRecord);

        LifeRecord lifeRecord = new LifeRecord();
        lifeRecord.setTitle("生活记录1");
        lifeRecord.setContent("今天生活很悠闲");
        lifeRecord.setMood("悠闲");
        lifeRecord.setTags("生活,悠闲,放松");
        lifeRecord.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(lifeRecord);

        LifeRecord workRecord2 = new LifeRecord();
        workRecord2.setTitle("工作记录2");
        workRecord2.setContent("今天又工作很忙");
        workRecord2.setMood("忙碌");
        workRecord2.setTags("工作,忙碌,项目");
        workRecord2.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(workRecord2);

        List<LifeRecord> workRecords = lifeRecordService.searchLifeRecordsByTags("工作");
        List<LifeRecord> lifeRecords = lifeRecordService.searchLifeRecordsByTags("生活");
        
        Assertions.assertTrue(workRecords.size() >= 2);
        Assertions.assertTrue(lifeRecords.size() >= 1);
    }

    @Test
    void testGetLifeRecordsByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        List<LifeRecord> records = lifeRecordService.getLifeRecordsByDateRange(start, end);
        Assertions.assertNotNull(records);
    }

    @Test
    void testGetLifeRecordsByDateRangeWithSpecificDates() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime tomorrow = now.plusDays(1);
        
        // 创建昨天的记录
        LifeRecord pastRecord = new LifeRecord();
        pastRecord.setTitle("昨天的记录");
        pastRecord.setContent("昨天发生的事情");
        pastRecord.setMood("回忆");
        pastRecord.setTags("回忆,昨天");
        pastRecord.setRecordDate(yesterday);
        lifeRecordService.createLifeRecord(pastRecord);
        
        // 创建明天的记录
        LifeRecord futureRecord = new LifeRecord();
        futureRecord.setTitle("明天的记录");
        futureRecord.setContent("明天要发生的事情");
        futureRecord.setMood("期待");
        futureRecord.setTags("期待,明天");
        futureRecord.setRecordDate(tomorrow);
        lifeRecordService.createLifeRecord(futureRecord);
        
        List<LifeRecord> records = lifeRecordService.getLifeRecordsByDateRange(yesterday, tomorrow);
        Assertions.assertTrue(records.size() >= 2);
    }

    @Test
    void testLifeRecordStatistics() {
        Map<String, Long> moodStats = lifeRecordService.getLifeRecordStatisticsByMood();
        Assertions.assertNotNull(moodStats);
    }

    @Test
    void testLifeRecordStatisticsWithMultipleRecords() {
        // 创建不同心情的记录
        LifeRecord happyRecord = new LifeRecord();
        happyRecord.setTitle("开心记录");
        happyRecord.setContent("今天很开心");
        happyRecord.setMood("开心");
        happyRecord.setTags("开心,美好");
        happyRecord.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(happyRecord);

        LifeRecord sadRecord = new LifeRecord();
        sadRecord.setTitle("难过记录");
        sadRecord.setContent("今天心情不好");
        sadRecord.setMood("难过");
        sadRecord.setTags("难过,需要安慰");
        sadRecord.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(sadRecord);

        LifeRecord calmRecord = new LifeRecord();
        calmRecord.setTitle("平静记录");
        calmRecord.setContent("今天很平静");
        calmRecord.setMood("平静");
        calmRecord.setTags("平静,放松");
        calmRecord.setRecordDate(LocalDateTime.now());
        lifeRecordService.createLifeRecord(calmRecord);

        Map<String, Long> moodStats = lifeRecordService.getLifeRecordStatisticsByMood();
        Assertions.assertTrue(moodStats.containsKey("开心"));
        Assertions.assertTrue(moodStats.containsKey("难过"));
        Assertions.assertTrue(moodStats.containsKey("平静"));
    }

    // ==================== 健康记录模块测试 ====================
    
    @Test
    void testCreateAndGetHealthRecord() {
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
        record.setNotes("单元测试");
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertNotNull(saved.getId());
        Assertions.assertEquals(new BigDecimal("70.5"), saved.getWeight());
        List<HealthRecord> all = healthRecordService.getAllHealthRecords();
        Assertions.assertTrue(all.size() > 0);
    }

    @Test
    void testCreateHealthRecordWithDifferentExerciseTypes() {
        // 测试跑步记录
        HealthRecord runningRecord = new HealthRecord();
        runningRecord.setWeight(new BigDecimal("70.0"));
        runningRecord.setHeight(175);
        runningRecord.setSystolicPressure(120);
        runningRecord.setDiastolicPressure(80);
        runningRecord.setHeartRate(80);
        runningRecord.setExerciseType("跑步");
        runningRecord.setExerciseDuration(1); // 1小时
        runningRecord.setSleepDuration(7);
        runningRecord.setRecordDate(LocalDateTime.now());
        runningRecord.setNotes("晨跑");
        HealthRecord savedRunning = healthRecordService.createHealthRecord(runningRecord);
        Assertions.assertEquals("跑步", savedRunning.getExerciseType());

        // 测试游泳记录
        HealthRecord swimmingRecord = new HealthRecord();
        swimmingRecord.setWeight(new BigDecimal("69.5"));
        swimmingRecord.setHeight(175);
        swimmingRecord.setSystolicPressure(118);
        swimmingRecord.setDiastolicPressure(78);
        swimmingRecord.setHeartRate(70);
        swimmingRecord.setExerciseType("游泳");
        swimmingRecord.setExerciseDuration(2); // 2小时
        swimmingRecord.setSleepDuration(8);
        swimmingRecord.setRecordDate(LocalDateTime.now());
        swimmingRecord.setNotes("游泳训练");
        HealthRecord savedSwimming = healthRecordService.createHealthRecord(swimmingRecord);
        Assertions.assertEquals("游泳", savedSwimming.getExerciseType());

        // 测试健身记录
        HealthRecord gymRecord = new HealthRecord();
        gymRecord.setWeight(new BigDecimal("71.0"));
        gymRecord.setHeight(175);
        gymRecord.setSystolicPressure(125);
        gymRecord.setDiastolicPressure(85);
        gymRecord.setHeartRate(85);
        gymRecord.setExerciseType("健身");
        gymRecord.setExerciseDuration(3); // 3小时
        gymRecord.setSleepDuration(6);
        gymRecord.setRecordDate(LocalDateTime.now());
        gymRecord.setNotes("力量训练");
        HealthRecord savedGym = healthRecordService.createHealthRecord(gymRecord);
        Assertions.assertEquals("健身", savedGym.getExerciseType());
    }

    @Test
    void testCreateHealthRecordWithExtremeValues() {
        // 测试极低体重
        HealthRecord lowWeightRecord = new HealthRecord();
        lowWeightRecord.setWeight(new BigDecimal("40.0"));
        lowWeightRecord.setHeight(160);
        lowWeightRecord.setSystolicPressure(110);
        lowWeightRecord.setDiastolicPressure(70);
        lowWeightRecord.setHeartRate(65);
        lowWeightRecord.setExerciseType("散步");
        lowWeightRecord.setExerciseDuration(1); // 1小时
        lowWeightRecord.setSleepDuration(9);
        lowWeightRecord.setRecordDate(LocalDateTime.now());
        lowWeightRecord.setNotes("极低体重测试");
        HealthRecord savedLowWeight = healthRecordService.createHealthRecord(lowWeightRecord);
        Assertions.assertEquals(new BigDecimal("40.0"), savedLowWeight.getWeight());

        // 测试极高体重
        HealthRecord highWeightRecord = new HealthRecord();
        highWeightRecord.setWeight(new BigDecimal("120.0"));
        highWeightRecord.setHeight(180);
        highWeightRecord.setSystolicPressure(140);
        highWeightRecord.setDiastolicPressure(90);
        highWeightRecord.setHeartRate(90);
        highWeightRecord.setExerciseType("慢走");
        highWeightRecord.setExerciseDuration(1); // 1小时
        highWeightRecord.setSleepDuration(6);
        highWeightRecord.setRecordDate(LocalDateTime.now());
        highWeightRecord.setNotes("极高体重测试");
        HealthRecord savedHighWeight = healthRecordService.createHealthRecord(highWeightRecord);
        Assertions.assertEquals(new BigDecimal("120.0"), savedHighWeight.getWeight());
    }

    @Test
    void testCreateHealthRecordWithSpecialCharacters() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.0"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("特殊运动@#$%");
        record.setExerciseDuration(1); // 1小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        record.setNotes("包含特殊字符的测试@#$%^&*()");
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertEquals("特殊运动@#$%", saved.getExerciseType());
        Assertions.assertEquals("包含特殊字符的测试@#$%^&*()", saved.getNotes());
    }

    @Test
    void testCreateHealthRecordWithLongNotes() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.0"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("综合训练");
        record.setExerciseDuration(2); // 2小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        record.setNotes("这是一个非常长的健康记录备注，用来测试系统对长文本的处理能力。" +
                "今天进行了综合性的健康训练，包括有氧运动、力量训练、柔韧性训练等。" +
                "训练过程中感觉身体状态良好，没有出现不适症状。训练后进行了充分的拉伸，" +
                "并补充了足够的水分和营养。希望明天能继续保持这样的状态。");
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertTrue(saved.getNotes().length() > 100);
    }

    @Test
    void testGetHealthRecordById() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.0"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("跑步");
        record.setExerciseDuration(1); // 1小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        
        Optional<HealthRecord> found = healthRecordService.getHealthRecordById(saved.getId());
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals(saved.getId(), found.get().getId());
    }

    @Test
    void testGetHealthRecordByIdNotFound() {
        Optional<HealthRecord> found = healthRecordService.getHealthRecordById(99999L);
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testUpdateHealthRecord() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.0"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("跑步");
        record.setExerciseDuration(1); // 1小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        
        saved.setWeight(new BigDecimal("71.0"));
        saved.setExerciseType("游泳");
        HealthRecord updated = healthRecordService.updateHealthRecord(saved.getId(), saved);
        Assertions.assertEquals(new BigDecimal("71.0"), updated.getWeight());
        Assertions.assertEquals("游泳", updated.getExerciseType());
    }

    @Test
    void testUpdateHealthRecordWithAllFields() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.0"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("跑步");
        record.setExerciseDuration(1); // 1小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        record.setNotes("原始备注");
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        
        saved.setWeight(new BigDecimal("72.0"));
        saved.setHeight(176);
        saved.setSystolicPressure(125);
        saved.setDiastolicPressure(85);
        saved.setHeartRate(80);
        saved.setExerciseType("健身");
        saved.setExerciseDuration(2); // 2小时
        saved.setSleepDuration(7);
        saved.setNotes("全面更新备注");
        HealthRecord updated = healthRecordService.updateHealthRecord(saved.getId(), saved);
        Assertions.assertEquals(new BigDecimal("72.0"), updated.getWeight());
        Assertions.assertEquals(176, updated.getHeight());
        Assertions.assertEquals(125, updated.getSystolicPressure());
        Assertions.assertEquals(85, updated.getDiastolicPressure());
        Assertions.assertEquals(80, updated.getHeartRate());
        Assertions.assertEquals("健身", updated.getExerciseType());
        Assertions.assertEquals(2, updated.getExerciseDuration());
        Assertions.assertEquals(7, updated.getSleepDuration());
        Assertions.assertEquals("全面更新备注", updated.getNotes());
    }

    @Test
    void testDeleteHealthRecord() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.0"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("跑步");
        record.setExerciseDuration(1); // 1小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        
        healthRecordService.deleteHealthRecord(saved.getId());
        Optional<HealthRecord> found = healthRecordService.getHealthRecordById(saved.getId());
        Assertions.assertFalse(found.isPresent());
    }

    @Test
    void testGetHealthRecordsByDateRange() {
        LocalDateTime start = LocalDateTime.now().minusDays(7);
        LocalDateTime end = LocalDateTime.now();
        List<HealthRecord> records = healthRecordService.getHealthRecordsByDateRange(start, end);
        Assertions.assertNotNull(records);
    }

    @Test
    void testGetHealthRecordsByDateRangeWithSpecificDates() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime yesterday = now.minusDays(1);
        LocalDateTime tomorrow = now.plusDays(1);
        
        // 创建昨天的记录
        HealthRecord pastRecord = new HealthRecord();
        pastRecord.setWeight(new BigDecimal("70.0"));
        pastRecord.setHeight(175);
        pastRecord.setSystolicPressure(120);
        pastRecord.setDiastolicPressure(80);
        pastRecord.setHeartRate(75);
        pastRecord.setExerciseType("跑步");
        pastRecord.setExerciseDuration(1); // 1小时
        pastRecord.setSleepDuration(8);
        pastRecord.setRecordDate(yesterday);
        healthRecordService.createHealthRecord(pastRecord);
        
        // 创建明天的记录
        HealthRecord futureRecord = new HealthRecord();
        futureRecord.setWeight(new BigDecimal("71.0"));
        futureRecord.setHeight(175);
        futureRecord.setSystolicPressure(122);
        futureRecord.setDiastolicPressure(82);
        futureRecord.setHeartRate(76);
        futureRecord.setExerciseType("游泳");
        futureRecord.setExerciseDuration(2); // 2小时
        futureRecord.setSleepDuration(7);
        futureRecord.setRecordDate(tomorrow);
        healthRecordService.createHealthRecord(futureRecord);
        
        List<HealthRecord> records = healthRecordService.getHealthRecordsByDateRange(yesterday, tomorrow);
        Assertions.assertTrue(records.size() >= 2);
    }

    @Test
    void testGetHealthRecordsByExerciseType() {
        HealthRecord record = new HealthRecord();
        record.setWeight(new BigDecimal("70.0"));
        record.setHeight(175);
        record.setSystolicPressure(120);
        record.setDiastolicPressure(80);
        record.setHeartRate(75);
        record.setExerciseType("跑步");
        record.setExerciseDuration(1); // 1小时
        record.setSleepDuration(8);
        record.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(record);
        
        List<HealthRecord> records = healthRecordService.getHealthRecordsByExerciseType("跑步");
        Assertions.assertTrue(records.size() > 0);
        records.forEach(r -> Assertions.assertEquals("跑步", r.getExerciseType()));
    }

    @Test
    void testGetHealthRecordsByExerciseTypeNotFound() {
        List<HealthRecord> records = healthRecordService.getHealthRecordsByExerciseType("不存在的运动");
        Assertions.assertEquals(0, records.size());
    }

    @Test
    void testGetHealthRecordsByMultipleExerciseTypes() {
        // 创建不同运动类型的记录
        HealthRecord runningRecord = new HealthRecord();
        runningRecord.setWeight(new BigDecimal("70.0"));
        runningRecord.setHeight(175);
        runningRecord.setSystolicPressure(120);
        runningRecord.setDiastolicPressure(80);
        runningRecord.setHeartRate(75);
        runningRecord.setExerciseType("跑步");
        runningRecord.setExerciseDuration(1); // 1小时
        runningRecord.setSleepDuration(8);
        runningRecord.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(runningRecord);

        HealthRecord swimmingRecord = new HealthRecord();
        swimmingRecord.setWeight(new BigDecimal("69.5"));
        swimmingRecord.setHeight(175);
        swimmingRecord.setSystolicPressure(118);
        swimmingRecord.setDiastolicPressure(78);
        swimmingRecord.setHeartRate(70);
        swimmingRecord.setExerciseType("游泳");
        swimmingRecord.setExerciseDuration(2); // 2小时
        swimmingRecord.setSleepDuration(8);
        swimmingRecord.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(swimmingRecord);

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
        List<HealthRecord> swimmingRecords = healthRecordService.getHealthRecordsByExerciseType("游泳");
        
        Assertions.assertTrue(runningRecords.size() >= 2);
        Assertions.assertTrue(swimmingRecords.size() >= 1);
    }

    @Test
    void testHealthRecordStatistics() {
        BigDecimal avgWeight = healthRecordService.getCurrentMonthAverageWeight();
        Assertions.assertNotNull(avgWeight);
        
        Double avgExerciseDuration = healthRecordService.getCurrentWeekAverageExerciseDuration();
        Assertions.assertNotNull(avgExerciseDuration);
        
        List<Object[]> exerciseStats = healthRecordService.getStatisticsByExerciseType();
        Assertions.assertNotNull(exerciseStats);
    }

    @Test
    void testHealthRecordStatisticsWithMultipleRecords() {
        // 创建不同运动类型的记录
        HealthRecord runningRecord = new HealthRecord();
        runningRecord.setWeight(new BigDecimal("70.0"));
        runningRecord.setHeight(175);
        runningRecord.setSystolicPressure(120);
        runningRecord.setDiastolicPressure(80);
        runningRecord.setHeartRate(75);
        runningRecord.setExerciseType("跑步");
        runningRecord.setExerciseDuration(1); // 1小时
        runningRecord.setSleepDuration(8);
        runningRecord.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(runningRecord);

        HealthRecord swimmingRecord = new HealthRecord();
        swimmingRecord.setWeight(new BigDecimal("69.5"));
        swimmingRecord.setHeight(175);
        swimmingRecord.setSystolicPressure(118);
        swimmingRecord.setDiastolicPressure(78);
        swimmingRecord.setHeartRate(70);
        swimmingRecord.setExerciseType("游泳");
        swimmingRecord.setExerciseDuration(2); // 2小时
        swimmingRecord.setSleepDuration(8);
        swimmingRecord.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(swimmingRecord);

        HealthRecord gymRecord = new HealthRecord();
        gymRecord.setWeight(new BigDecimal("71.0"));
        gymRecord.setHeight(175);
        gymRecord.setSystolicPressure(125);
        gymRecord.setDiastolicPressure(85);
        gymRecord.setHeartRate(85);
        gymRecord.setExerciseType("健身");
        gymRecord.setExerciseDuration(3); // 3小时
        gymRecord.setSleepDuration(6);
        gymRecord.setRecordDate(LocalDateTime.now());
        healthRecordService.createHealthRecord(gymRecord);

        BigDecimal avgWeight = healthRecordService.getCurrentMonthAverageWeight();
        Assertions.assertNotNull(avgWeight);
        
        Double avgExerciseDuration = healthRecordService.getCurrentWeekAverageExerciseDuration();
        Assertions.assertNotNull(avgExerciseDuration);
        
        List<Object[]> exerciseStats = healthRecordService.getStatisticsByExerciseType();
        Assertions.assertNotNull(exerciseStats);
    }

    // ==================== 异常处理测试 ====================
    
    @Test
    void testExpenseValidation() {
        Expense expense = new Expense();
        // 不设置必填字段，应该抛出异常
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            expenseService.createExpense(expense);
        });
    }

    @Test
    void testIncomeValidation() {
        Income income = new Income();
        // 不设置必填字段，应该抛出异常
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            incomeService.createIncome(income);
        });
    }

    @Test
    void testLifeRecordValidation() {
        LifeRecord record = new LifeRecord();
        // 不设置必填字段，应该抛出异常
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            lifeRecordService.createLifeRecord(record);
        });
    }

    @Test
    void testHealthRecordValidation() {
        HealthRecord record = new HealthRecord();
        // 设置无效的体重值来触发验证异常
        record.setWeight(new BigDecimal("500.0")); // 超过最大值300
        record.setRecordDate(LocalDateTime.now());
        
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            healthRecordService.createHealthRecord(record);
        });
    }

    @Test
    void testUpdateNonExistentExpense() {
        Expense expense = new Expense();
        expense.setDescription("测试");
        expense.setAmount(new BigDecimal("10.00"));
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        
        Expense result = expenseService.updateExpense(999L, expense);
        Assertions.assertNull(result); // 应该返回null而不是抛出异常
    }

    @Test
    void testDeleteNonExistentIncome() {
        boolean result = incomeService.deleteIncome(999L);
        Assertions.assertFalse(result); // 应该返回false而不是抛出异常
    }

    // ==================== 边界值测试 ====================
    
    @Test
    void testExpenseWithZeroAmount() {
        Expense expense = new Expense();
        expense.setDescription("零金额支出");
        expense.setAmount(BigDecimal.ZERO);
        expense.setCategory("测试");
        expense.setExpenseDate(LocalDateTime.now());
        
        // 零金额应该抛出验证异常
        Assertions.assertThrows(jakarta.validation.ConstraintViolationException.class, () -> {
            expenseService.createExpense(expense);
        });
    }

    @Test
    void testHealthRecordWithMaxValues() {
        HealthRecord record = new HealthRecord();
        record.setRecordDate(LocalDateTime.now());
        record.setWeight(new BigDecimal("200.0"));
        record.setHeight(250);
        record.setSystolicPressure(200);
        record.setDiastolicPressure(120);
        record.setHeartRate(200);
        record.setExerciseDuration(24);
        record.setExerciseType("马拉松");
        record.setSleepDuration(24);
        
        HealthRecord saved = healthRecordService.createHealthRecord(record);
        Assertions.assertEquals(250, saved.getHeight());
        Assertions.assertEquals(24, saved.getExerciseDuration());
    }

    // ==================== 集成测试 ====================
    
    @Test
    void testCompleteWorkflow() {
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
        lifeRecord.setWeather("晴");
        lifeRecord.setLocation("家");
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
}
