package com.example.demo.service;

import com.example.demo.entity.SpecialGroup;
import com.example.demo.entity.Student;
import com.example.demo.repository.SpecialGroupRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SpecialGroupService {
    @Autowired
    private SpecialGroupRepository specialGroupRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Transactional
    public SpecialGroup addSpecialGroup(Long studentId, SpecialGroup specialGroup) {
        Student student = studentRepository.findById(studentId).orElse(null);
        if (student == null) {
            return null; // 学生不存在
        }
        specialGroup.setStudent(student);
        return specialGroupRepository.save(specialGroup);
    }

    public List<SpecialGroup> getSpecialGroupsByStudentId(Long studentId) {
        return specialGroupRepository.findByStudentId(studentId);
    }

    @Transactional
    public SpecialGroup updateSpecialGroup(Long id, SpecialGroup updatedSpecialGroup) {
        SpecialGroup existingSpecialGroup = specialGroupRepository.findById(id).orElse(null);
        if (existingSpecialGroup == null) {
            return null; // 记录不存在
        }

        // 更新字段
        existingSpecialGroup.setGroupType(updatedSpecialGroup.getGroupType());
        // ... 更新其他字段

        return specialGroupRepository.save(existingSpecialGroup);
    }

    @Transactional
    public void deleteSpecialGroup(Long id) {
        specialGroupRepository.deleteById(id);
    }
}