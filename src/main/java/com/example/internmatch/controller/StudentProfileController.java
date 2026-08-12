package com.example.internmatch.controller;

import com.example.internmatch.dto.request.AddStudentSkillRequest;
import com.example.internmatch.dto.request.CreateStudentProfileRequest;
import com.example.internmatch.dto.request.UpdateStudentProfileRequest;
import com.example.internmatch.dto.request.UpdateStudentSkillRequest;
import com.example.internmatch.dto.response.StudentProfileResponseDto;
import com.example.internmatch.dto.response.StudentSkillResponseDto;
import com.example.internmatch.security.CustomUserDetails;
import com.example.internmatch.service.StudentProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
@Tag(name = "Student Profile", description = "Öğrenci profili ve beceri yönetimi işlemleri")
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    public StudentProfileController(StudentProfileService studentProfileService) {
        this.studentProfileService = studentProfileService;
    }

    // ─── Profile Endpoints ────────────────────────────────────────────────────────

    @PostMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Öğrenci Profili Oluştur",
            description = "Giriş yapmış öğrenci için yeni bir profil oluşturur. Her öğrenci yalnızca bir profil oluşturabilir."
    )
    public ResponseEntity<StudentProfileResponseDto> createProfile(
            @Valid @RequestBody CreateStudentProfileRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        StudentProfileResponseDto response = studentProfileService.createProfile(request, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/profile/me")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Kendi Profilimi Görüntüle",
            description = "Giriş yapmış öğrencinin kendi profil bilgilerini döndürür."
    )
    public ResponseEntity<StudentProfileResponseDto> getMyProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        StudentProfileResponseDto response = studentProfileService.getMyProfile(userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/profile")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Kendi Profilimi Güncelle",
            description = "Giriş yapmış öğrencinin profil bilgilerini günceller. Yalnızca gönderilen alanlar güncellenir."
    )
    public ResponseEntity<StudentProfileResponseDto> updateProfile(
            @Valid @RequestBody UpdateStudentProfileRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        StudentProfileResponseDto response = studentProfileService.updateProfile(request, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Öğrenci Profilini ID ile Görüntüle (Admin)",
            description = "Admin rolü için belirli bir öğrencinin profil bilgilerini ID ile döndürür."
    )
    public ResponseEntity<StudentProfileResponseDto> getStudentById(
            @Parameter(description = "Öğrenci profil ID'si") @PathVariable Long id) {
        StudentProfileResponseDto response = studentProfileService.getStudentProfileById(id);
        return ResponseEntity.ok(response);
    }

    // ─── Skill Endpoints ──────────────────────────────────────────────────────────

    @GetMapping("/profile/me/skills")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Kendi Becerilerimi Listele",
            description = "Giriş yapmış öğrencinin profiline eklenmiş tüm becerileri listeler."
    )
    public ResponseEntity<List<StudentSkillResponseDto>> getMySkills(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<StudentSkillResponseDto> response = studentProfileService.getMySkills(userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile/me/skills")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Profilime Beceri Ekle",
            description = "Giriş yapmış öğrencinin profiline yeni bir beceri ekler. Aynı beceri tekrar eklenemez."
    )
    public ResponseEntity<StudentSkillResponseDto> addSkill(
            @Valid @RequestBody AddStudentSkillRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        StudentSkillResponseDto response = studentProfileService.addSkill(request, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/profile/me/skills/{studentSkillId}")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Beceri Seviyesini Güncelle",
            description = "Giriş yapmış öğrencinin belirtilen beceri kaydının seviyesini ve deneyim yılını günceller."
    )
    public ResponseEntity<StudentSkillResponseDto> updateSkill(
            @Parameter(description = "Güncellenecek öğrenci beceri kaydının ID'si") @PathVariable Long studentSkillId,
            @Valid @RequestBody UpdateStudentSkillRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        StudentSkillResponseDto response = studentProfileService.updateSkill(studentSkillId, request, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/profile/me/skills/{studentSkillId}")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Becerimi Sil",
            description = "Giriş yapmış öğrencinin profilinden belirtilen beceri kaydını siler."
    )
    public ResponseEntity<Void> deleteSkill(
            @Parameter(description = "Silinecek öğrenci beceri kaydının ID'si") @PathVariable Long studentSkillId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        studentProfileService.deleteSkill(studentSkillId, userDetails.getUser());
        return ResponseEntity.noContent().build();
    }
}
