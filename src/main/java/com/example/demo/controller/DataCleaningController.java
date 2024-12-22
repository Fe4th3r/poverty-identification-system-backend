package com.example.demo.controller;

import com.example.demo.service.DataCleaningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/data-cleaning")
public class DataCleaningController {

    @Autowired
    private DataCleaningService dataCleaningService;

    @PostMapping("/student")
    public ResponseEntity<Map<String, Object>> cleanStudentData() {
        Map<String, Object> result = dataCleaningService.cleanStudentData();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/family-info")
    public ResponseEntity<Map<String, Object>> cleanFamilyInfoData() {
        Map<String, Object> result = dataCleaningService.cleanFamilyInfoData();
        return ResponseEntity.ok(result);
    }

    @PostMapping("/special-group")
    public ResponseEntity<Map<String, Object>> cleanSpecialGroupData() {
        Map<String, Object> result = dataCleaningService.cleanSpecialGroupData();
        return ResponseEntity.ok(result);
    }

    // ... 其他实体的数据清洗接口
}