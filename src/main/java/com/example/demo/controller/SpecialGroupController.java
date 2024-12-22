package com.example.demo.controller;

import com.example.demo.entity.SpecialGroup;
import com.example.demo.service.SpecialGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/special-group")
public class SpecialGroupController {
    @Autowired
    private SpecialGroupService specialGroupService;

    @PostMapping("/{studentId}")
    public ResponseEntity<Object> addSpecialGroup(@PathVariable Long studentId, @RequestBody SpecialGroup specialGroup) {
        SpecialGroup newSpecialGroup = specialGroupService.addSpecialGroup(studentId, specialGroup);
        Map<String, Object> response = new HashMap<>();
        if (newSpecialGroup != null) {
            response.put("message", "特殊群体信息添加成功");
            response.put("specialGroup", newSpecialGroup);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "特殊群体信息添加失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<SpecialGroup>> getSpecialGroupsByStudentId(@PathVariable Long studentId) {
        List<SpecialGroup> specialGroups = specialGroupService.getSpecialGroupsByStudentId(studentId);
        return ResponseEntity.ok(specialGroups);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateSpecialGroup(@PathVariable Long id, @RequestBody SpecialGroup specialGroup) {
        SpecialGroup updatedSpecialGroup = specialGroupService.updateSpecialGroup(id, specialGroup);
        Map<String, Object> response = new HashMap<>();
        if (updatedSpecialGroup != null) {
            response.put("message", "特殊群体信息更新成功");
            response.put("specialGroup", updatedSpecialGroup);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "特殊群体信息更新失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteSpecialGroup(@PathVariable Long id) {
        specialGroupService.deleteSpecialGroup(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "特殊群体信息删除成功");
        return ResponseEntity.ok(response);
    }
}