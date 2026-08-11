package com.example.internmatch.repository;

import com.example.internmatch.entity.InternshipPosting;
import com.example.internmatch.enums.PostingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InternshipPostingRepository extends JpaRepository<InternshipPosting, Long> {
    List<InternshipPosting> findByCompanyId(Long companyId);
    List<InternshipPosting> findByStatus(PostingStatus status);
}
