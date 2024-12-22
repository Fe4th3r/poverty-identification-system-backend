package com.example.demo.controller;

import com.example.demo.entity.WorkStudy;
import com.example.demo.service.WorkStudyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/work-study")
public class WorkStudyController {
    @Autowired
    private WorkStudyService workStudyService;

    @PostMapping("/{studentId}")
    public ResponseEntity<Object> addWorkStudy(@PathVariable Long studentId, @RequestBody WorkStudy workStudy) {
        WorkStudy newWorkStudy = workStudyService.addWorkStudy(studentId, workStudy);
        Map<String, Object> response = new HashMap<>();
        if (newWorkStudy != null) {
            response.put("message", "勤工助学信息添加成功");
            response.put("workStudy", newWorkStudy);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "勤工助学信息添加失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<WorkStudy>> getWorkStudiesByStudentId(@PathVariable Long studentId) {
        List<WorkStudy> workStudies = workStudyService.getWorkStudiesByStudentId(studentId);
        return ResponseEntity.ok(workStudies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateWorkStudy(@PathVariable Long id, @RequestBody WorkStudy workStudy) {
        WorkStudy updatedWorkStudy = workStudyService.updateWorkStudy(id, workStudy);
        Map<String, Object> response = new HashMap<>();
        if (updatedWorkStudy != null) {
            response.put("message", "勤工助学信息更新成功");
            response.put("workStudy", updatedWorkStudy);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "勤工助学信息更新失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteWorkStudy(@PathVariable Long id) {
        workStudyService.deleteWorkStudy(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "勤工助学信息删除成功");
        return ResponseEntity.ok(response);
    }
}