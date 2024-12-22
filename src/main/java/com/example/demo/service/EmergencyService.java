package com.example.demo.service;

import com.example.demo.entity.Emergency;
import com.example.demo.entity.Student;
import com.example.demo.repository.EmergencyRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmergencyService {
    @Autowired
    private EmergencyRepository emergencyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public Emergency addEmergency(Long studentId, Emergency emergency) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null; // 学生不存在
        }
        emergency.setStudent(student);
        return emergencyRepository.save(emergency);
    }

    public List<Emergency> getEmergenciesByStudentId(Long studentId) {
        return emergencyRepository.findByStudentId(studentId);
    }

    @Transactional
    public Emergency updateEmergency(Long id, Emergency updatedEmergency) {
        Emergency existingEmergency = emergencyRepository.findById(id).orElse(null);
        if (existingEmergency == null) {
            return null; // 记录不存在
        }

        // 更新字段
        existingEmergency.setDescription(updatedEmergency.getDescription());
        existingEmergency.setOccurrenceDate(updatedEmergency.getOccurrenceDate());
        // ... 更新其他字段

        return emergencyRepository.save(existingEmergency);
    }

    @Transactional
    public void deleteEmergency(Long id) {
        emergencyRepository.deleteById(id);
    }
}