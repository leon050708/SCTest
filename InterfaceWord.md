以下是**个人助手系统 API 文档**的 Markdown 版本，适合直接复制到文档或接口平台：

---

# 个人助手系统 API 文档

## 1. 收入模块 Income

| 方法 | 路径 | 描述 | 请求体/参数 | 返回 |
|------|------|------|-------------|------|
| GET  | `/api/incomes` | 查询所有收入 | 无 | List<Income> |
| GET  | `/api/incomes/{id}` | 根据ID查询收入 | 路径参数id | Income |
| POST | `/api/incomes` | 新增收入 | Income对象 | Income |
| PUT  | `/api/incomes/{id}` | 更新收入 | 路径参数id, Income对象 | Income |
| DELETE | `/api/incomes/{id}` | 删除收入 | 路径参数id | void |
| GET  | `/api/incomes/source/{source}` | 按来源查询 | 路径参数source | List<Income> |
| GET  | `/api/incomes/date?start=yyyy-MM-ddTHH:mm:ss&end=yyyy-MM-ddTHH:mm:ss` | 按日期区间查询 | 查询参数 | List<Income> |
| GET  | `/api/incomes/statistics/month` | 按月统计 | 无 | List<Object[]> |
| GET  | `/api/incomes/statistics/source` | 按来源统计 | 无 | Map<String, BigDecimal> |
| GET  | `/api/incomes/total/month` | 本月总收入 | 无 | BigDecimal |
| GET  | `/api/incomes/total/week` | 本周总收入 | 无 | BigDecimal |

---

## 2. 支出模块 Expense

| 方法 | 路径 | 描述 | 请求体/参数 | 返回 |
|------|------|------|-------------|------|
| GET  | `/api/expenses` | 查询所有支出 | 无 | List<Expense> |
| GET  | `/api/expenses/{id}` | 根据ID查询支出 | 路径参数id | Expense |
| POST | `/api/expenses` | 新增支出 | Expense对象 | Expense |
| PUT  | `/api/expenses/{id}` | 更新支出 | 路径参数id, Expense对象 | Expense |
| DELETE | `/api/expenses/{id}` | 删除支出 | 路径参数id | void |
| GET  | `/api/expenses/category/{category}` | 按类别查询 | 路径参数category | List<Expense> |
| GET  | `/api/expenses/date?start=yyyy-MM-ddTHH:mm:ss&end=yyyy-MM-ddTHH:mm:ss` | 按日期区间查询 | 查询参数 | List<Expense> |
| GET  | `/api/expenses/statistics/month` | 按月统计 | 无 | List<Object[]> |
| GET  | `/api/expenses/statistics/category` | 按类别统计 | 无 | Map<String, BigDecimal> |
| GET  | `/api/expenses/total/month` | 本月总支出 | 无 | BigDecimal |
| GET  | `/api/expenses/total/week` | 本周总支出 | 无 | BigDecimal |

---

## 3. 生活记录模块 LifeRecord

| 方法 | 路径 | 描述 | 请求体/参数 | 返回 |
|------|------|------|-------------|------|
| GET  | `/api/life-records` | 查询所有生活记录 | 无 | List<LifeRecord> |
| GET  | `/api/life-records/{id}` | 根据ID查询 | 路径参数id | LifeRecord |
| POST | `/api/life-records` | 新增生活记录 | LifeRecord对象 | LifeRecord |
| PUT  | `/api/life-records/{id}` | 更新生活记录 | 路径参数id, LifeRecord对象 | LifeRecord |
| DELETE | `/api/life-records/{id}` | 删除生活记录 | 路径参数id | void |
| GET  | `/api/life-records/mood/{mood}` | 按心情查询 | 路径参数mood | List<LifeRecord> |
| GET  | `/api/life-records/date?start=yyyy-MM-ddTHH:mm:ss&end=yyyy-MM-ddTHH:mm:ss` | 按日期区间查询 | 查询参数 | List<LifeRecord> |
| GET  | `/api/life-records/tags/{tag}` | 按标签模糊查询 | 路径参数tag | List<LifeRecord> |
| GET  | `/api/life-records/statistics/mood` | 按心情统计 | 无 | Map<String, Long> |
| GET  | `/api/life-records/statistics/month` | 按月统计 | 无 | List<Object[]> |
| GET  | `/api/life-records/count/month` | 本月记录数 | 无 | long |
| GET  | `/api/life-records/count/week` | 本周记录数 | 无 | long |

---

## 4. 健康记录模块 HealthRecord

| 方法 | 路径 | 描述 | 请求体/参数 | 返回 |
|------|------|------|-------------|------|
| GET  | `/api/health-records` | 查询所有健康记录 | 无 | List<HealthRecord> |
| GET  | `/api/health-records/{id}` | 根据ID查询 | 路径参数id | HealthRecord |
| POST | `/api/health-records` | 新增健康记录 | HealthRecord对象 | HealthRecord |
| PUT  | `/api/health-records/{id}` | 更新健康记录 | 路径参数id, HealthRecord对象 | HealthRecord |
| DELETE | `/api/health-records/{id}` | 删除健康记录 | 路径参数id | void |
| GET  | `/api/health-records/date?start=yyyy-MM-ddTHH:mm:ss&end=yyyy-MM-ddTHH:mm:ss` | 按日期区间查询 | 查询参数 | List<HealthRecord> |
| GET  | `/api/health-records/exercise-type/{type}` | 按运动类型查询 | 路径参数type | List<HealthRecord> |
| GET  | `/api/health-records/statistics/exercise-type` | 按运动类型统计 | 无 | List<Object[]> |
| GET  | `/api/health-records/average/weight/month` | 本月平均体重 | 无 | BigDecimal |
| GET  | `/api/health-records/average/exercise-duration/week` | 本周平均运动时长 | 无 | Double |

---

## 5. 综合统计 Dashboard

| 方法 | 路径 | 描述 | 请求体/参数 | 返回 |
|------|------|------|-------------|------|
| GET  | `/api/dashboard/summary` | 综合统计（本月收支、生活、健康等） | 无 | Map<String, Object> |
| GET  | `/api/dashboard/trend/weight?start=yyyy-MM-ddTHH:mm:ss` | 体重趋势 | 查询参数start | List<Object[]> |
| GET  | `/api/dashboard/trend/expense?start=yyyy-MM-ddTHH:mm:ss` | 支出趋势 | 查询参数start | List<Object[]> |
| GET  | `/api/dashboard/trend/income?start=yyyy-MM-ddTHH:mm:ss` | 收入趋势 | 查询参数start | List<Object[]> |

---

## 6. 全局异常处理

- 所有接口均支持参数校验，未通过时返回400，错误信息在响应体中。
- 业务异常返回自定义错误码和信息。

---

如需接口示例请求/响应体、参数说明、或导出为Postman集合，请随时告知！