package com.cyh.sctest.service;

import com.cyh.sctest.entity.LifeRecord;
import com.cyh.sctest.repository.LifeRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LifeRecordService {
    
    @Autowired
    private LifeRecordRepository lifeRecordRepository;
    
    // 创建生活记录
    public LifeRecord createLifeRecord(LifeRecord lifeRecord) {
        if (lifeRecord.getRecordDate() == null) {
            lifeRecord.setRecordDate(LocalDateTime.now());
        }
        return lifeRecordRepository.save(lifeRecord);
    }
    
    // 获取所有生活记录
    public List<LifeRecord> getAllLifeRecords() {
        return lifeRecordRepository.findAll();
    }
    
    // 根据ID获取生活记录
    public Optional<LifeRecord> getLifeRecordById(Long id) {
        return lifeRecordRepository.findById(id);
    }
    
    // 更新生活记录
    public LifeRecord updateLifeRecord(Long id, LifeRecord lifeRecordDetails) {
        Optional<LifeRecord> optionalLifeRecord = lifeRecordRepository.findById(id);
        if (optionalLifeRecord.isPresent()) {
            LifeRecord lifeRecord = optionalLifeRecord.get();
            lifeRecord.setTitle(lifeRecordDetails.getTitle());
            lifeRecord.setContent(lifeRecordDetails.getContent());
            lifeRecord.setMood(lifeRecordDetails.getMood());
            lifeRecord.setTags(lifeRecordDetails.getTags());
            lifeRecord.setRecordDate(lifeRecordDetails.getRecordDate());
            lifeRecord.setWeather(lifeRecordDetails.getWeather());
            lifeRecord.setLocation(lifeRecordDetails.getLocation());
            return lifeRecordRepository.save(lifeRecord);
        }
        return null;
    }
    
    // 删除生活记录
    public boolean deleteLifeRecord(Long id) {
        if (lifeRecordRepository.existsById(id)) {
            lifeRecordRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
    // 根据心情查找记录
    public List<LifeRecord> getLifeRecordsByMood(String mood) {
        return lifeRecordRepository.findByMood(mood);
    }
    
    // 根据日期范围查找记录
    public List<LifeRecord> getLifeRecordsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return lifeRecordRepository.findByRecordDateBetween(startDate, endDate);
    }
    
    // 根据标题模糊查找
    public List<LifeRecord> searchLifeRecordsByTitle(String title) {
        return lifeRecordRepository.findByTitleContainingIgnoreCase(title);
    }
    
    // 根据内容模糊查找
    public List<LifeRecord> searchLifeRecordsByContent(String content) {
        return lifeRecordRepository.findByContentContainingIgnoreCase(content);
    }
    
    // 根据标签查找
    public List<LifeRecord> searchLifeRecordsByTags(String tags) {
        return lifeRecordRepository.findByTagsContainingIgnoreCase(tags);
    }
    
    // 根据天气查找记录
    public List<LifeRecord> getLifeRecordsByWeather(String weather) {
        return lifeRecordRepository.findByWeather(weather);
    }
    
    // 根据位置查找记录
    public List<LifeRecord> searchLifeRecordsByLocation(String location) {
        return lifeRecordRepository.findByLocationContainingIgnoreCase(location);
    }
    
    // 获取最近的记录
    public List<LifeRecord> getRecentLifeRecords() {
        return lifeRecordRepository.findTop10ByOrderByRecordDateDesc();
    }
    
    // 获取按心情分组的统计
    public Map<String, Long> getLifeRecordStatisticsByMood() {
        List<Object[]> results = lifeRecordRepository.countByMoodGroup();
        return results.stream()
                .collect(Collectors.toMap(
                        row -> (String) row[0],
                        row -> (Long) row[1]
                ));
    }
    
    // 获取按月份分组的统计
    public List<Object[]> getLifeRecordStatisticsByMonth() {
        return lifeRecordRepository.countByMonthGroup();
    }
    
    // 获取本月记录数量
    public long getCurrentMonthRecordCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfMonth = now.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfMonth = now.withDayOfMonth(now.toLocalDate().lengthOfMonth()).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        List<LifeRecord> records = getLifeRecordsByDateRange(startOfMonth, endOfMonth);
        return records.size();
    }
    
    // 获取本周记录数量
    public long getCurrentWeekRecordCount() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime startOfWeek = now.toLocalDate().atStartOfDay().with(java.time.DayOfWeek.MONDAY);
        LocalDateTime endOfWeek = startOfWeek.plusDays(6).withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        List<LifeRecord> records = getLifeRecordsByDateRange(startOfWeek, endOfWeek);
        return records.size();
    }
} 