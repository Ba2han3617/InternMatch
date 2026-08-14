package com.example.internmatch.controller;

import com.example.internmatch.dto.request.CreatePostingCriterionRequest;
import com.example.internmatch.dto.request.UpdatePostingCriterionRequest;
import com.example.internmatch.dto.response.PostingCriterionResponseDto;
import com.example.internmatch.security.CustomUserDetails;
import com.example.internmatch.service.PostingCriterionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Staj İlanı Kriterleri", description = "Staj ilanlarına ait değerlendirme kriteri ve ağırlık yönetimi")
@SecurityRequirement(name = "bearerAuth")
public class PostingCriterionController {

    private final PostingCriterionService criterionService;

    public PostingCriterionController(PostingCriterionService criterionService) {
        this.criterionService = criterionService;
    }

    @PostMapping("/internship-postings/{postingId}/criteria")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "İlana kriter ekle",
            description = "Şirket kullanıcısı yalnızca kendi şirketine ait staj ilanına kriter ekleyebilir. " +
                    "Kriter ağırlığı 1-100 arasında olmalı ve aynı ilandaki toplam ağırlık 100'ü geçmemelidir. " +
                    "Desteklenen kriter tipleri: SKILL, LOCATION, WORK_MODE, GPA, GRADE_LEVEL, CUSTOM."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Kriter başarıyla oluşturuldu"),
            @ApiResponse(responseCode = "400", description = "Eksik alan, geçersiz kriter tipi veya toplam ağırlık hatası"),
            @ApiResponse(responseCode = "403", description = "Bu ilana kriter ekleme yetkisi yok"),
            @ApiResponse(responseCode = "404", description = "İlan veya beceri bulunamadı")
    })
    public ResponseEntity<PostingCriterionResponseDto> createCriterion(
            @Parameter(description = "Kriter eklenecek staj ilanının ID'si") @PathVariable Long postingId,
            @Valid @RequestBody CreatePostingCriterionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        PostingCriterionResponseDto response = criterionService.createCriterion(postingId, request, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/internship-postings/{postingId}/criteria")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "İlan kriterlerini listele",
            description = "Admin tüm ilanların kriterlerini görüntüleyebilir. " +
                    "Şirket kullanıcısı kendi ilanlarının kriterlerini görüntüleyebilir. " +
                    "Öğrenci yalnızca PUBLISHED durumundaki ilanların kriterlerini görüntüleyebilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kriterler başarıyla listelendi"),
            @ApiResponse(responseCode = "403", description = "Bu ilana ait kriterleri görüntüleme yetkisi yok"),
            @ApiResponse(responseCode = "404", description = "İlan bulunamadı")
    })
    public ResponseEntity<List<PostingCriterionResponseDto>> getCriteria(
            @Parameter(description = "Kriterleri listelenecek staj ilanının ID'si") @PathVariable Long postingId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(criterionService.getCriteriaByPosting(postingId, userDetails.getUser()));
    }

    @PutMapping("/posting-criteria/{criteriaId}")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "Kriter güncelle",
            description = "Şirket kullanıcısı yalnızca kendi ilanına ait kriteri güncelleyebilir. " +
                    "Güncelleme sonrası aynı ilandaki kriter ağırlıkları toplamı 100'ü geçemez."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kriter başarıyla güncellendi"),
            @ApiResponse(responseCode = "400", description = "Eksik alan, geçersiz kriter tipi veya toplam ağırlık hatası"),
            @ApiResponse(responseCode = "403", description = "Bu kriteri güncelleme yetkisi yok"),
            @ApiResponse(responseCode = "404", description = "Kriter veya beceri bulunamadı")
    })
    public ResponseEntity<PostingCriterionResponseDto> updateCriterion(
            @Parameter(description = "Güncellenecek kriter ID'si") @PathVariable Long criteriaId,
            @Valid @RequestBody UpdatePostingCriterionRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(criterionService.updateCriterion(criteriaId, request, userDetails.getUser()));
    }

    @DeleteMapping("/posting-criteria/{criteriaId}")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "Kriter sil",
            description = "Şirket kullanıcısı yalnızca kendi ilanına ait kriteri silebilir. Silme işlemi kriteri veritabanından kaldırır."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Kriter başarıyla silindi"),
            @ApiResponse(responseCode = "403", description = "Bu kriteri silme yetkisi yok"),
            @ApiResponse(responseCode = "404", description = "Kriter bulunamadı")
    })
    public ResponseEntity<Map<String, String>> deleteCriterion(
            @Parameter(description = "Silinecek kriter ID'si") @PathVariable Long criteriaId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        criterionService.deleteCriterion(criteriaId, userDetails.getUser());
        return ResponseEntity.ok(Map.of(
                "message", "Kriter başarıyla silindi.",
                "criteriaId", String.valueOf(criteriaId)
        ));
    }
}
