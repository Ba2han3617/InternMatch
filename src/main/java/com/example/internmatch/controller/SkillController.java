package com.example.internmatch.controller;

import com.example.internmatch.dto.request.CreateSkillRequest;
import com.example.internmatch.dto.response.SkillResponseDto;
import com.example.internmatch.service.SkillService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/skills")
@Tag(name = "Skills", description = "Beceri kataloğu yönetimi işlemleri")
public class SkillController {

    private final SkillService skillService;

    public SkillController(SkillService skillService) {
        this.skillService = skillService;
    }

    @GetMapping
    @Operation(
            summary = "Tüm Becerileri Listele",
            description = "Sistemdeki tüm becerileri listeler. Giriş yapmış tüm kullanıcılar erişebilir."
    )
    public ResponseEntity<List<SkillResponseDto>> getAllSkills() {
        List<SkillResponseDto> response = skillService.getAllSkills();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Beceri Detayı",
            description = "ID ile belirtilen becerinin detaylarını döndürür."
    )
    public ResponseEntity<SkillResponseDto> getSkillById(
            @Parameter(description = "Beceri ID'si") @PathVariable Long id) {
        SkillResponseDto response = skillService.getSkillById(id);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Yeni Beceri Ekle (Admin)",
            description = "Admin rolü için sisteme yeni bir beceri ekler. Aynı isimde beceri tekrar eklenemez."
    )
    public ResponseEntity<SkillResponseDto> createSkill(
            @Valid @RequestBody CreateSkillRequest request) {
        SkillResponseDto response = skillService.createSkill(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
