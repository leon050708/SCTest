package com.cyh.sctest.controller;

import com.cyh.sctest.entity.LifeRecord;
import com.cyh.sctest.service.LifeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/life-records")
@CrossOrigin(origins = "*")
public class LifeRecordController {
    
    @Autowired
    private LifeRecordService lifeRecordService;
    
    // 1. 创建生活记录
    @PostMapping
    public ResponseEntity<LifeRecord> createLifeRecord(@Valid @RequestBody LifeRecord lifeRecord) {
        LifeRecord createdLifeRecord = lifeRecordService.createLifeRecord(lifeRecord);
        return new ResponseEntity<>(createdLifeRecord, HttpStatus.CREATED);
    }
    
    // 2. 获取所有生活记录
    @GetMapping
    public ResponseEntity<List<LifeRecord>> getAllLifeRecords() {
        List<LifeRecord> lifeRecords = lifeRecordService.getAllLifeRecords();
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 3. 根据ID获取生活记录
    @GetMapping("/{id}")
    public ResponseEntity<LifeRecord> getLifeRecordById(@PathVariable Long id) {
        Optional<LifeRecord> lifeRecord = lifeRecordService.getLifeRecordById(id);
        return lifeRecord.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    // 4. 更新生活记录
    @PutMapping("/{id}")
    public ResponseEntity<LifeRecord> updateLifeRecord(@PathVariable Long id, @Valid @RequestBody LifeRecord lifeRecordDetails) {
        LifeRecord updatedLifeRecord = lifeRecordService.updateLifeRecord(id, lifeRecordDetails);
        if (updatedLifeRecord != null) {
            return ResponseEntity.ok(updatedLifeRecord);
        }
        return ResponseEntity.notFound().build();
    }
    
    // 5. 删除生活记录
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLifeRecord(@PathVariable Long id) {
        boolean deleted = lifeRecordService.deleteLifeRecord(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // 6. 根据心情查找记录
    @GetMapping("/mood/{mood}")
    public ResponseEntity<List<LifeRecord>> getLifeRecordsByMood(@PathVariable String mood) {
        List<LifeRecord> lifeRecords = lifeRecordService.getLifeRecordsByMood(mood);
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 7. 根据日期范围查找记录
    @GetMapping("/date-range")
    public ResponseEntity<List<LifeRecord>> getLifeRecordsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<LifeRecord> lifeRecords = lifeRecordService.getLifeRecordsByDateRange(startDate, endDate);
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 8. 根据标题模糊查找
    @GetMapping("/search/title")
    public ResponseEntity<List<LifeRecord>> searchLifeRecordsByTitle(@RequestParam String title) {
        List<LifeRecord> lifeRecords = lifeRecordService.searchLifeRecordsByTitle(title);
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 9. 根据内容模糊查找
    @GetMapping("/search/content")
    public ResponseEntity<List<LifeRecord>> searchLifeRecordsByContent(@RequestParam String content) {
        List<LifeRecord> lifeRecords = lifeRecordService.searchLifeRecordsByContent(content);
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 10. 根据标签查找
    @GetMapping("/search/tags")
    public ResponseEntity<List<LifeRecord>> searchLifeRecordsByTags(@RequestParam String tags) {
        List<LifeRecord> lifeRecords = lifeRecordService.searchLifeRecordsByTags(tags);
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 11. 根据天气查找记录
    @GetMapping("/weather/{weather}")
    public ResponseEntity<List<LifeRecord>> getLifeRecordsByWeather(@PathVariable String weather) {
        List<LifeRecord> lifeRecords = lifeRecordService.getLifeRecordsByWeather(weather);
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 12. 根据位置查找记录
    @GetMapping("/search/location")
    public ResponseEntity<List<LifeRecord>> searchLifeRecordsByLocation(@RequestParam String location) {
        List<LifeRecord> lifeRecords = lifeRecordService.searchLifeRecordsByLocation(location);
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 13. 获取最近的记录
    @GetMapping("/recent")
    public ResponseEntity<List<LifeRecord>> getRecentLifeRecords() {
        List<LifeRecord> lifeRecords = lifeRecordService.getRecentLifeRecords();
        return ResponseEntity.ok(lifeRecords);
    }
    
    // 14. 获取按心情分组的统计
    @GetMapping("/statistics/mood")
    public ResponseEntity<Map<String, Long>> getLifeRecordStatisticsByMood() {
        Map<String, Long> statistics = lifeRecordService.getLifeRecordStatisticsByMood();
        return ResponseEntity.ok(statistics);
    }
    
    // 15. 获取按月份分组的统计
    @GetMapping("/statistics/month")
    public ResponseEntity<List<Object[]>> getLifeRecordStatisticsByMonth() {
        List<Object[]> statistics = lifeRecordService.getLifeRecordStatisticsByMonth();
        return ResponseEntity.ok(statistics);
    }
    
    // 16. 获取本月记录数量
    @GetMapping("/count/current-month")
    public ResponseEntity<Long> getCurrentMonthRecordCount() {
        long count = lifeRecordService.getCurrentMonthRecordCount();
        return ResponseEntity.ok(count);
    }
    
    // 17. 获取本周记录数量
    @GetMapping("/count/current-week")
    public ResponseEntity<Long> getCurrentWeekRecordCount() {
        long count = lifeRecordService.getCurrentWeekRecordCount();
        return ResponseEntity.ok(count);
    }
} 