package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Student findByStuId(String stuId);

    @EntityGraph(attributePaths = {"familyInfo", "specialGroups", "emergencies", "workStudies"})
    List<Student> findByNameContaining(String name); // 根据姓名模糊查询

    @SuppressWarnings("null")
    @EntityGraph(attributePaths = {"familyInfo", "specialGroups", "emergencies", "workStudies"})
    List<Student> findAll();

    @Query("SELECT DISTINCT s FROM Student s LEFT JOIN FETCH s.familyInfo LEFT JOIN FETCH s.specialGroups LEFT JOIN FETCH s.emergencies LEFT JOIN FETCH s.workStudies WHERE s.id = :id")
    Optional<Student> findByIdWithAllCollections(Long id);
}