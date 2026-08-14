package com.example.internmatch.service;

import com.example.internmatch.dto.request.CreatePostingCriterionRequest;
import com.example.internmatch.dto.request.UpdatePostingCriterionRequest;
import com.example.internmatch.dto.response.PostingCriterionResponseDto;
import com.example.internmatch.entity.User;

import java.util.List;

public interface PostingCriterionService {

    PostingCriterionResponseDto createCriterion(Long postingId, CreatePostingCriterionRequest request, User currentUser);

    List<PostingCriterionResponseDto> getCriteriaByPosting(Long postingId, User currentUser);

    PostingCriterionResponseDto updateCriterion(Long criteriaId, UpdatePostingCriterionRequest request, User currentUser);

    void deleteCriterion(Long criteriaId, User currentUser);
}
