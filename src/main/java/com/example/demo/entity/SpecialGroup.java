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
public class SpecialGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne // 多对一关系
    @JoinColumn(name = "student_id")
    @JsonIgnore
    private Student student;

    private String groupType; // 特殊群体类型 (例如：低保户、建档立卡贫困户、残疾人家庭、单亲家庭、孤儿等)
    // ... 其他字段


}