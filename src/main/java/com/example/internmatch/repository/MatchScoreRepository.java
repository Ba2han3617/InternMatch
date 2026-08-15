package com.example.internmatch.repository;

import com.example.internmatch.entity.MatchScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchScoreRepository extends JpaRepository<MatchScore, Long> {
    Optional<MatchScore> findByStudentProfileIdAndInternshipPostingId(Long studentProfileId, Long postingId);
    List<MatchScore> findByStudentProfileIdOrderByCalculatedAtDesc(Long studentProfileId);
    List<MatchScore> findByInternshipPostingIdOrderByTotalScoreDesc(Long postingId);
}

