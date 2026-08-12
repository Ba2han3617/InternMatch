package com.example.internmatch.service.impl;

import com.example.internmatch.dto.request.CreateSkillRequest;
import com.example.internmatch.dto.response.SkillResponseDto;
import com.example.internmatch.entity.Skill;
import com.example.internmatch.exception.DuplicateResourceException;
import com.example.internmatch.exception.ResourceNotFoundException;
import com.example.internmatch.repository.SkillRepository;
import com.example.internmatch.service.SkillService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SkillServiceImpl implements SkillService {

    private final SkillRepository skillRepository;

    public SkillServiceImpl(SkillRepository skillRepository) {
        this.skillRepository = skillRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillResponseDto> getAllSkills() {
        return skillRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public SkillResponseDto getSkillById(Long id) {
        Skill skill = skillRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Skill", "id", id));
        return mapToDto(skill);
    }

    @Override
    @Transactional
    public SkillResponseDto createSkill(CreateSkillRequest request) {
        if (skillRepository.existsByNameIgnoreCase(request.getName())) {
            throw new DuplicateResourceException(
                    "'" + request.getName() + "' adında bir beceri zaten mevcut.");
        }

        Skill skill = Skill.builder()
                .name(request.getName().trim())
                .category(request.getCategory())
                .build();

        Skill savedSkill = skillRepository.save(skill);
        return mapToDto(savedSkill);
    }

    private SkillResponseDto mapToDto(Skill skill) {
        return SkillResponseDto.builder()
                .id(skill.getId())
                .name(skill.getName())
                .category(skill.getCategory())
                .build();
    }
}
