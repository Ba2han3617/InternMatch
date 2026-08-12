package com.example.internmatch.repository;

import com.example.internmatch.entity.StudentSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentSkillRepository extends JpaRepository<StudentSkill, Long> {
    List<StudentSkill> findByStudentProfileId(Long studentProfileId);
    Boolean existsByStudentProfileIdAndSkillId(Long studentProfileId, Long skillId);
    Optional<StudentSkill> findByIdAndStudentProfileId(Long id, Long studentProfileId);
}
