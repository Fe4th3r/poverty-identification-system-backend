package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(exclude = {"familyInfo", "specialGroups", "emergencies", "workStudies"})
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stuId; // 学号
    private String name;  // 姓名
    private String gender; // 性别
    private String idCard; // 身份证号
    private String major;   // 专业
    private PovertyLevel povertyLevel; // 贫困等级
    // ... 其他字段，例如：出生日期、民族、政治面貌、联系方式、入学时间等

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private FamilyInfo familyInfo;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<SpecialGroup> specialGroups = new HashSet<>(); // 使用 Set 代替 List

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Emergency> emergencies = new HashSet<>(); // 使用 Set 代替 List

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<WorkStudy> workStudies = new HashSet<>(); // 使用 Set 代替 List

}