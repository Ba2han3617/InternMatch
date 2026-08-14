package com.example.internmatch.repository;

import com.example.internmatch.entity.PostingCriterion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PostingCriterionRepository extends JpaRepository<PostingCriterion, Long> {
    List<PostingCriterion> findByPostingId(Long postingId);

    List<PostingCriterion> findByPostingIdOrderByIdAsc(Long postingId);

    @Query("SELECT COALESCE(SUM(c.weight), 0) FROM PostingCriterion c WHERE c.posting.id = :postingId")
    BigDecimal sumWeightByPostingId(@Param("postingId") Long postingId);

    @Query("SELECT COALESCE(SUM(c.weight), 0) FROM PostingCriterion c WHERE c.posting.id = :postingId AND c.id <> :criteriaId")
    BigDecimal sumWeightByPostingIdExcludingCriterion(@Param("postingId") Long postingId,
                                                      @Param("criteriaId") Long criteriaId);
}
