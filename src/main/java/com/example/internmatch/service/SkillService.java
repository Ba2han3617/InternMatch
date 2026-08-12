package com.example.internmatch.service;

import com.example.internmatch.dto.request.CreateSkillRequest;
import com.example.internmatch.dto.response.SkillResponseDto;

import java.util.List;

public interface SkillService {

    /**
     * Sistemdeki tüm becerileri listeler.
     */
    List<SkillResponseDto> getAllSkills();

    /**
     * ID ile tek beceri getirir.
     * Bulunamazsa ResourceNotFoundException fırlatır.
     */
    SkillResponseDto getSkillById(Long id);

    /**
     * Admin için yeni beceri oluşturur.
     * Aynı isimde beceri varsa DuplicateResourceException fırlatır.
     */
    SkillResponseDto createSkill(CreateSkillRequest request);
}
