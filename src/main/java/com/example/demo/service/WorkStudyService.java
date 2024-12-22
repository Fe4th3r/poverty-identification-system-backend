package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.entity.WorkStudy;
import com.example.demo.repository.StudentRepository;
import com.example.demo.repository.WorkStudyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkStudyService {
    @Autowired
    private WorkStudyRepository workStudyRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public WorkStudy addWorkStudy(Long studentId, WorkStudy workStudy) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null; // 学生不存在
        }
        workStudy.setStudent(student);
        return workStudyRepository.save(workStudy);
    }

    public List<WorkStudy> getWorkStudiesByStudentId(Long studentId) {
        return workStudyRepository.findByStudentId(studentId);
    }

    @Transactional
    public WorkStudy updateWorkStudy(Long id, WorkStudy updatedWorkStudy) {
        WorkStudy existingWorkStudy = workStudyRepository.findById(id).orElse(null);
        if (existingWorkStudy == null) {
            return null; // 记录不存在
        }

        // 更新字段
        existingWorkStudy.setPosition(updatedWorkStudy.getPosition());
        existingWorkStudy.setStartDate(updatedWorkStudy.getStartDate());
        existingWorkStudy.setEndDate(updatedWorkStudy.getEndDate());
        existingWorkStudy.setMonthlySalary(updatedWorkStudy.getMonthlySalary());
        // ... 更新其他字段

        return workStudyRepository.save(existingWorkStudy);
    }

    @Transactional
    public void deleteWorkStudy(Long id) {
        workStudyRepository.deleteById(id);
    }
}