package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString(exclude = {"student"})
public class FamilyInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne // 一对一关系
    @JoinColumn(name = "student_id", referencedColumnName = "id") // 外键关联到 Student 表的 id 字段
    @JsonIgnore
    private Student student;

    private String address; // 家庭住址
    private Integer population; // 家庭人口
    private Double totalIncome; // 家庭年总收入
    // ... 其他字段，例如：主要经济来源、劳动力人数等


}