package com.example.internmatch.repository;

import com.example.internmatch.entity.PostingCriterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostingCriterionRepository extends JpaRepository<PostingCriterion, Long> {
    List<PostingCriterion> findByPostingId(Long postingId);
}
