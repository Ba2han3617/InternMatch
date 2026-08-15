package com.example.internmatch.controller;

import com.example.internmatch.dto.response.MatchScoreResponseDto;
import com.example.internmatch.security.CustomUserDetails;
import com.example.internmatch.service.MatchScoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/match-scores")
@Tag(name = "Match Score", description = "Öğrenci ve Staj İlanı Uygunluk Skoru İşlemleri")
public class MatchScoreController {

    private final MatchScoreService matchScoreService;

    public MatchScoreController(MatchScoreService matchScoreService) {
        this.matchScoreService = matchScoreService;
    }

    @PostMapping("/calculate/{postingId}")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "İlan İçin Uygunluk Skorunu Hesapla",
            description = "Giriş yapmış öğrencinin ilgili staj ilanına ne kadar uygun olduğunu yüzde (0-100) olarak hesaplar ve detaylı sonuçları kaydeder. Aynı ilan tekrar hesaplandığında eski kayıt güncellenir."
    )
    public ResponseEntity<MatchScoreResponseDto> calculateMatchScore(
            @Parameter(description = "Uygunluk skoru hesaplanacak staj ilanının ID'si") @PathVariable Long postingId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        MatchScoreResponseDto response = matchScoreService.calculateMatchScore(postingId, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('STUDENT')")
    @Operation(
            summary = "Kendi Skorlarımı Listele",
            description = "Giriş yapmış öğrencinin tüm staj ilanları için hesaplanmış uygunluk skorlarını listeler."
    )
    public ResponseEntity<List<MatchScoreResponseDto>> getMyMatchScores(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MatchScoreResponseDto> response = matchScoreService.getMyMatchScores(userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/posting/{postingId}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    @Operation(
            summary = "İlana Ait Tüm Skorları Listele (Şirket/Admin)",
            description = "Şirket kullanıcısı kendi oluşturduğu staj ilanına ait öğrenci skorlarını, Admin ise tüm ilan skorlarını görüntüleyebilir."
    )
    public ResponseEntity<List<MatchScoreResponseDto>> getPostingMatchScores(
            @Parameter(description = "Skorları listelenecek staj ilanının ID'si") @PathVariable Long postingId,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<MatchScoreResponseDto> response = matchScoreService.getPostingMatchScores(postingId, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('STUDENT', 'COMPANY', 'ADMIN')")
    @Operation(
            summary = "Skor Detayını Görüntüle",
            description = "Belirtilen ID'ye sahip uygunluk skorunun detaylarını ve kriter bazlı puan dağılımını döndürür."
    )
    public ResponseEntity<MatchScoreResponseDto> getMatchScoreById(
            @Parameter(description = "Görüntülenecek skor kaydının ID'si") @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        MatchScoreResponseDto response = matchScoreService.getMatchScoreById(id, userDetails.getUser());
        return ResponseEntity.ok(response);
    }
}
