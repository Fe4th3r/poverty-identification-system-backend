package com.example.demo.service;

import com.example.demo.entity.Student;
import com.example.demo.entity.PovertyLevel;
import com.example.demo.entity.FamilyInfo;
import com.example.demo.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PovertyIdentificationService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private FamilyInfoService familyInfoService;

    @Autowired
    private SpecialGroupService specialGroupService;

    @Autowired
    private WorkStudyService workStudyService;

    public void identifyPovertyLevels() {
        List<Student> students = studentRepository.findAll();
        for (Student student : students) {
            PovertyLevel level = calculatePovertyLevel(student);
            student.setPovertyLevel(level);
            studentRepository.save(student);
        }
    }

    private PovertyLevel calculatePovertyLevel(Student student) {
        // 1. 获取家庭信息
        FamilyInfo familyInfo = familyInfoService.getFamilyInfoByStudentId(student.getId());

        // 2. 获取特殊群体信息
        List<String> specialGroups = specialGroupService.getSpecialGroupsByStudentId(student.getId()).stream()
                .map(com.example.demo.entity.SpecialGroup::getGroupType)
                .collect(Collectors.toList()); 

        // 3. 获取勤工助学信息
        List<com.example.demo.entity.WorkStudy> workStudies = workStudyService.getWorkStudiesByStudentId(student.getId());
        double totalWorkStudyIncome = workStudies.stream()
            .mapToDouble(workStudy -> Double.parseDouble(workStudy.getMonthlySalary()))
            .sum();

        // 4. 根据规则计算贫困等级
        if (familyInfo == null) {
            return PovertyLevel.NOT_POOR; // 无法获取家庭信息，暂定为非贫困
        }

        double familyIncome = familyInfo.getTotalIncome() == null ? 0 : familyInfo.getTotalIncome();
        int familySize = familyInfo.getPopulation() == null ? 1 : familyInfo.getPopulation();
        double perCapitaIncome = familySize > 0 ? familyIncome / familySize : 0;

        if (perCapitaIncome < 1000) {
            // 家庭人均年收入低于1000
            if (specialGroups.contains("低保户") || specialGroups.contains("建档立卡贫困户")) {
                return PovertyLevel.SPECIAL_POOR; // 特困
            } else {
                return PovertyLevel.POOR; // 困难
            }
        } else if (perCapitaIncome < 2000) {
            // 家庭人均年收入低于2000
            if (totalWorkStudyIncome < 500) {
                return PovertyLevel.POOR;
            }
            return PovertyLevel.GENERAL; // 一般
        } else {
            return PovertyLevel.NOT_POOR; // 非贫困
        }
    }
}