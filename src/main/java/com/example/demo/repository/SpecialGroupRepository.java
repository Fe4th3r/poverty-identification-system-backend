package com.example.demo.repository;

import com.example.demo.entity.SpecialGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialGroupRepository extends JpaRepository<SpecialGroup, Long> {
    List<SpecialGroup> findByStudentId(Long studentId);
}