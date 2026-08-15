package com.example.internmatch.service;

import com.example.internmatch.dto.response.MatchScoreResponseDto;
import com.example.internmatch.entity.User;

import java.util.List;

public interface MatchScoreService {

    MatchScoreResponseDto calculateMatchScore(Long postingId, User currentUser);

    List<MatchScoreResponseDto> getMyMatchScores(User currentUser);

    List<MatchScoreResponseDto> getPostingMatchScores(Long postingId, User currentUser);

    MatchScoreResponseDto getMatchScoreById(Long id, User currentUser);
}
