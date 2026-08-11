package com.example.internmatch.repository;

import com.example.internmatch.entity.MatchScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MatchScoreRepository extends JpaRepository<MatchScore, Long> {
    Optional<MatchScore> findByApplicationId(Long applicationId);
}
