package com.cyh.sctest.repository;

import com.cyh.sctest.entity.LifeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LifeRecordRepository extends JpaRepository<LifeRecord, Long> {
    
    // 根据心情查找记录
    List<LifeRecord> findByMood(String mood);
    
    // 根据日期范围查找记录
    List<LifeRecord> findByRecordDateBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    // 根据标题模糊查找
    List<LifeRecord> findByTitleContainingIgnoreCase(String title);
    
    // 根据内容模糊查找
    List<LifeRecord> findByContentContainingIgnoreCase(String content);
    
    // 根据标签查找
    List<LifeRecord> findByTagsContainingIgnoreCase(String tags);
    
    // 根据心情和日期范围查找
    List<LifeRecord> findByMoodAndRecordDateBetween(String mood, LocalDateTime startDate, LocalDateTime endDate);
    
    // 按心情分组统计
    @Query("SELECT l.mood, COUNT(l) FROM LifeRecord l WHERE l.mood IS NOT NULL GROUP BY l.mood")
    List<Object[]> countByMoodGroup();
    
    // 按月份分组统计
    @Query("SELECT YEAR(l.recordDate), MONTH(l.recordDate), COUNT(l) FROM LifeRecord l GROUP BY YEAR(l.recordDate), MONTH(l.recordDate) ORDER BY YEAR(l.recordDate) DESC, MONTH(l.recordDate) DESC")
    List<Object[]> countByMonthGroup();
    
    // 查找最近的记录
    List<LifeRecord> findTop10ByOrderByRecordDateDesc();
    
    // 根据天气查找记录
    List<LifeRecord> findByWeather(String weather);
    
    // 根据位置查找记录
    List<LifeRecord> findByLocationContainingIgnoreCase(String location);
} 