package com.example.internmatch.repository;

import com.example.internmatch.entity.Application;
import com.example.internmatch.enums.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByStudentProfileId(Long studentProfileId);
    List<Application> findByPostingId(Long postingId);
    List<Application> findByStatus(ApplicationStatus status);
    Optional<Application> findByStudentProfileIdAndPostingId(Long studentProfileId, Long postingId);
    Boolean existsByStudentProfileIdAndPostingId(Long studentProfileId, Long postingId);
}
