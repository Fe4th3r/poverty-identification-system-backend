package com.example.demo.controller;

import com.example.demo.entity.Emergency;
import com.example.demo.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/emergency")
public class EmergencyController {
    @Autowired
    private EmergencyService emergencyService;

    @PostMapping("/{studentId}")
    public ResponseEntity<Object> addEmergency(@PathVariable Long studentId, @RequestBody Emergency emergency) {
        Emergency newEmergency = emergencyService.addEmergency(studentId, emergency);
        Map<String, Object> response = new HashMap<>();
        if (newEmergency != null) {
            response.put("message", "突发状况信息添加成功");
            response.put("emergency", newEmergency);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "突发状况信息添加失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<List<Emergency>> getEmergenciesByStudentId(@PathVariable Long studentId) {
        List<Emergency> emergencies = emergencyService.getEmergenciesByStudentId(studentId);
        return ResponseEntity.ok(emergencies);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmergency(@PathVariable Long id, @RequestBody Emergency emergency) {
        Emergency updatedEmergency = emergencyService.updateEmergency(id, emergency);
        Map<String, Object> response = new HashMap<>();
        if (updatedEmergency != null) {
            response.put("message", "突发状况信息更新成功");
            response.put("emergency", updatedEmergency);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "突发状况信息更新失败");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteEmergency(@PathVariable Long id) {
        emergencyService.deleteEmergency(id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "突发状况信息删除成功");
        return ResponseEntity.ok(response);
    }
}