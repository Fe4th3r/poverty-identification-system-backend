package com.example.demo.controller;

import com.example.demo.entity.FamilyInfo;
import com.example.demo.service.FamilyInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/family-info")
public class FamilyInfoController {
    @Autowired
    private FamilyInfoService familyInfoService;

    @PostMapping("/{studentId}")
    public ResponseEntity<Object> addFamilyInfo(@PathVariable Long studentId, @RequestBody FamilyInfo familyInfo) {
        FamilyInfo newFamilyInfo = familyInfoService.addFamilyInfo(studentId, familyInfo);
        Map<String, Object> response = new HashMap<>();
        if (newFamilyInfo != null) {
            response.put("message", "家庭信息添加成功");
            response.put("familyInfo", newFamilyInfo);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "家庭信息添加失败, 请检查该学生是否存在或是否已有家庭信息");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<FamilyInfo> getFamilyInfoByStudentId(@PathVariable Long studentId) {
        FamilyInfo familyInfo = familyInfoService.getFamilyInfoByStudentId(studentId);
        if (familyInfo != null) {
            return ResponseEntity.ok(familyInfo);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{studentId}")
    public ResponseEntity<Object> updateFamilyInfo(@PathVariable Long studentId, @RequestBody FamilyInfo familyInfo) {
        FamilyInfo updatedFamilyInfo = familyInfoService.updateFamilyInfo(studentId, familyInfo);
        Map<String, Object> response = new HashMap<>();
        if (updatedFamilyInfo != null) {
            response.put("message", "家庭信息更新成功");
            response.put("familyInfo", updatedFamilyInfo);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "家庭信息更新失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{studentId}")
    public ResponseEntity<Object> deleteFamilyInfo(@PathVariable Long studentId) {
        familyInfoService.deleteFamilyInfo(studentId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "家庭信息删除成功");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<FamilyInfo>> getAllFamilyInfos() {
        List<FamilyInfo> familyInfos = familyInfoService.getAllFamilyInfos();
        return ResponseEntity.ok(familyInfos);
    }
}