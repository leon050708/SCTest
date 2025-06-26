# 个人助手系统 API 接口测试文档

## 说明
本文件包含所有主要模块（收支、生活、健康、仪表盘）的接口说明、参数、返回示例和测试建议，便于开发和测试。

---

## 1. 支出管理 Expense

### 1.1 新增支出
- **POST** `/api/expenses`
- **Body**（JSON）：
```json
{
  "description": "午餐",
  "amount": 25.50,
  "category": "餐饮",
  "expenseDate": "2025-06-20T12:00:00",
  "notes": "公司附近餐厅"
}
```
- **返回**：201 + Expense对象
- **测试建议**：描述、金额、类别为必填，金额不能为负。

### 1.2 获取所有支出
- **GET** `/api/expenses`
- **返回**：200 + Expense数组

### 1.3 根据ID获取支出
- **GET** `/api/expenses/{id}`
- **返回**：200 + Expense对象 / 404

### 1.4 更新支出
- **PUT** `/api/expenses/{id}`
- **Body**：同新增
- **返回**：200 + Expense对象 / 404

### 1.5 删除支出
- **DELETE** `/api/expenses/{id}`
- **返回**：204 / 404

### 1.6 按类别查找
- **GET** `/api/expenses/category/{category}`
- **返回**：200 + Expense数组

### 1.7 按日期范围查找
- **GET** `/api/expenses/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`
- **返回**：200 + Expense数组

### 1.8 按金额范围查找
- **GET** `/api/expenses/amount-range?minAmount=10&maxAmount=100`
- **返回**：200 + Expense数组

### 1.9 描述模糊查找
- **GET** `/api/expenses/search?description=午餐`
- **返回**：200 + Expense数组

### 1.10 日期范围总支出
- **GET** `/api/expenses/total/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`
- **返回**：200 + 数字

### 1.11 类别总支出
- **GET** `/api/expenses/total/category/{category}`
- **返回**：200 + 数字

### 1.12 按类别分组统计
- **GET** `/api/expenses/statistics/category`
- **返回**：200 + {类别: 金额}

### 1.13 按月份分组统计
- **GET** `/api/expenses/statistics/month`
- **返回**：200 + 数组

### 1.14 本月总支出
- **GET** `/api/expenses/total/current-month`
- **返回**：200 + 数字

### 1.15 本周总支出
- **GET** `/api/expenses/total/current-week`
- **返回**：200 + 数字

---

## 2. 收入管理 Income

（接口与支出类似，路径为 `/api/incomes`，参数和返回结构一致）

---

## 3. 生活记录 LifeRecord

### 3.1 新增生活记录
- **POST** `/api/life-records`
- **Body**：
```json
{
  "title": "美好的一天",
  "content": "今天天气很好，心情愉快。",
  "mood": "开心",
  "tags": "朋友,公园",
  "recordDate": "2025-06-20T10:00:00",
  "weather": "晴天",
  "location": "城市公园"
}
```
- **返回**：201 + LifeRecord对象

### 3.2 获取所有生活记录
- **GET** `/api/life-records`

### 3.3 根据ID获取
- **GET** `/api/life-records/{id}`

### 3.4 更新
- **PUT** `/api/life-records/{id}`

### 3.5 删除
- **DELETE** `/api/life-records/{id}`

### 3.6 按心情查找
- **GET** `/api/life-records/mood/{mood}`

### 3.7 按日期范围查找
- **GET** `/api/life-records/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`

### 3.8 标题模糊查找
- **GET** `/api/life-records/search/title?title=美好`

### 3.9 内容模糊查找
- **GET** `/api/life-records/search/content?content=天气`

### 3.10 标签查找
- **GET** `/api/life-records/search/tags?tags=朋友`

### 3.11 天气查找
- **GET** `/api/life-records/weather/{weather}`

### 3.12 位置查找
- **GET** `/api/life-records/search/location?location=公园`

### 3.13 最近记录
- **GET** `/api/life-records/recent`

### 3.14 心情分组统计
- **GET** `/api/life-records/statistics/mood`

### 3.15 月份分组统计
- **GET** `/api/life-records/statistics/month`

### 3.16 本月记录数
- **GET** `/api/life-records/count/current-month`

### 3.17 本周记录数
- **GET** `/api/life-records/count/current-week`

---

## 4. 健康记录 HealthRecord

### 4.1 新增健康记录
- **POST** `/api/health-records`
- **Body**：
```json
{
  "recordDate": "2025-06-20T08:00:00",
  "weight": 65.5,
  "height": 170,
  "systolicPressure": 120,
  "diastolicPressure": 80,
  "heartRate": 75,
  "exerciseDuration": 8,
  "exerciseType": "跑步",
  "sleepDuration": 8,
  "notes": "今天感觉很好"
}
```
- **返回**：201 + HealthRecord对象

### 4.2 获取所有健康记录
- **GET** `/api/health-records`

### 4.3 根据ID获取
- **GET** `/api/health-records/{id}`

### 4.4 更新
- **PUT** `/api/health-records/{id}`

### 4.5 删除
- **DELETE** `/api/health-records/{id}`

### 4.6 按日期范围查找
- **GET** `/api/health-records/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`

### 4.7 按体重范围查找
- **GET** `/api/health-records/weight-range?minWeight=60&maxWeight=70`

### 4.8 按运动类型查找
- **GET** `/api/health-records/exercise-type/{exerciseType}`

### 4.9 按运动时长范围查找
- **GET** `/api/health-records/exercise-duration-range?minDuration=1&maxDuration=8`

### 4.10 按睡眠时长范围查找
- **GET** `/api/health-records/sleep-duration-range?minDuration=6&maxDuration=9`

### 4.11 最近健康记录
- **GET** `/api/health-records/recent`

### 4.12 平均体重（日期范围）
- **GET** `/api/health-records/average-weight/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`

### 4.13 平均心率（日期范围）
- **GET** `/api/health-records/average-heart-rate/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`

### 4.14 平均运动时长（日期范围）
- **GET** `/api/health-records/average-exercise-duration/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`

### 4.15 平均睡眠时长（日期范围）
- **GET** `/api/health-records/average-sleep-duration/date-range?startDate=2025-06-01T00:00:00&endDate=2025-06-30T23:59:59`

### 4.16 按月份分组平均体重
- **GET** `/api/health-records/statistics/weight-by-month`

### 4.17 按运动类型分组统计
- **GET** `/api/health-records/statistics/exercise-type`

### 4.18 体重变化趋势
- **GET** `/api/health-records/weight-trend?startDate=2025-05-20T00:00:00`

### 4.19 本月平均体重
- **GET** `/api/health-records/average-weight/current-month`

### 4.20 本周平均运动时长
- **GET** `/api/health-records/average-exercise-duration/current-week`

---

## 5. 仪表盘 Dashboard

### 5.1 概览
- **GET** `/api/dashboard/overview`

### 5.2 财务统计
- **GET** `/api/dashboard/finance`

### 5.3 生活统计
- **GET** `/api/dashboard/life`

### 5.4 健康统计
- **GET** `/api/dashboard/health`

### 5.5 综合报告
- **GET** `/api/dashboard/report`

---

## 6. 通用说明
- 所有时间参数均为ISO格式，如：`2025-06-20T10:00:00`
- 所有POST/PUT接口均需Content-Type: application/json
- 返回值如有疑问可参考README.md中的数据模型
- 建议用Postman或curl批量测试 