package com.example.internmatch.controller;

import com.example.internmatch.dto.request.CreateInternshipPostingRequest;
import com.example.internmatch.dto.request.PostingStatusUpdateRequest;
import com.example.internmatch.dto.request.UpdateInternshipPostingRequest;
import com.example.internmatch.dto.response.InternshipPostingResponseDto;
import com.example.internmatch.dto.response.InternshipPostingSummaryResponseDto;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;
import com.example.internmatch.security.CustomUserDetails;
import com.example.internmatch.service.InternshipPostingService;
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
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/internship-postings")
@Tag(name = "Internship Postings", description = "Staj ilanı oluşturma ve yönetim işlemleri")
@SecurityRequirement(name = "bearerAuth")
public class InternshipPostingController {

    private final InternshipPostingService postingService;

    public InternshipPostingController(InternshipPostingService postingService) {
        this.postingService = postingService;
    }

    // ─── POST /api/internship-postings ────────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "Staj İlanı Oluştur",
            description = "ROLE_COMPANY yetkisine sahip, şirket profili olan kullanıcı tarafından yeni staj ilanı oluşturulur. " +
                    "İlan varsayılan olarak DRAFT durumunda oluşturulur. " +
                    "Başlangıç tarihi bitiş tarihinden önce olmalıdır. " +
                    "Son başvuru tarihi geçmiş bir ilan PUBLISHED olarak oluşturulamaz."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "İlan başarıyla oluşturuldu"),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek veya tarih hatası"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki (ROLE_COMPANY gerekli)"),
            @ApiResponse(responseCode = "404", description = "Şirket profili bulunamadı")
    })
    public ResponseEntity<InternshipPostingResponseDto> createPosting(
            @Valid @RequestBody CreateInternshipPostingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        InternshipPostingResponseDto response = postingService.createPosting(request, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ─── GET /api/internship-postings ────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Yayındaki İlanları Listele (Filtrelenebilir)",
            description = "Yayındaki (PUBLISHED) ve son başvuru tarihi geçmemiş staj ilanlarını listeler. " +
                    "Opsiyonel filtre parametreleri: city, workMode, department, positionName, minGpa. " +
                    "Hiçbir filtre girilmezse tüm aktif ilanlar döner."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "İlanlar başarıyla döndürüldü"),
            @ApiResponse(responseCode = "401", description = "Oturum açılmamış")
    })
    public ResponseEntity<List<InternshipPostingSummaryResponseDto>> getPublishedPostings(
            @Parameter(description = "Şehir filtresi", example = "Istanbul")
            @RequestParam(required = false) String city,

            @Parameter(description = "Çalışma modeli filtresi", example = "HYBRID")
            @RequestParam(required = false) WorkMode workMode,

            @Parameter(description = "Departman filtresi", example = "Software")
            @RequestParam(required = false) String department,

            @Parameter(description = "Pozisyon adı filtresi", example = "Backend")
            @RequestParam(required = false) String positionName,

            @Parameter(description = "Minimum GPA filtresi - bu değerden küçük GPA gereksinimi olanları filtreler", example = "3.0")
            @RequestParam(required = false) BigDecimal minGpa,

            @Parameter(description = "İlan durumu filtresi (sadece PUBLISHED görünür, admin dışı)", example = "PUBLISHED")
            @RequestParam(required = false) PostingStatus status) {

        List<InternshipPostingSummaryResponseDto> response =
                postingService.getPublishedPostings(city, workMode, department, positionName, minGpa, status);
        return ResponseEntity.ok(response);
    }

    // ─── GET /api/internship-postings/{id} ───────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "İlan Detayını Görüntüle",
            description = "Belirtilen ID'ye sahip staj ilanının tam detaylarını döndürür. " +
                    "PUBLISHED ilanlar tüm giriş yapmış kullanıcılara görünür. " +
                    "DRAFT/CLOSED/PASSIVE ilanlar sadece sahibi şirket veya admin görebilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "İlan detayı başarıyla döndürüldü"),
            @ApiResponse(responseCode = "403", description = "Bu ilana erişim yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "İlan bulunamadı"),
            @ApiResponse(responseCode = "401", description = "Oturum açılmamış")
    })
    public ResponseEntity<InternshipPostingResponseDto> getPostingById(
            @Parameter(description = "İlan ID'si") @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        InternshipPostingResponseDto response = postingService.getPostingById(id, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    // ─── GET /api/internship-postings/my-company ─────────────────────────────────

    @GetMapping("/my-company")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "Kendi Şirketimin İlanlarını Listele",
            description = "Giriş yapmış şirket yetkilisinin kendi şirketine ait tüm ilanları listeler. " +
                    "Tüm durumdaki ilanlar (DRAFT, PUBLISHED, CLOSED, PASSIVE) dahil edilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Şirketin ilanları başarıyla döndürüldü"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki (ROLE_COMPANY gerekli)"),
            @ApiResponse(responseCode = "400", description = "Şirket profili bulunamadı")
    })
    public ResponseEntity<List<InternshipPostingSummaryResponseDto>> getMyCompanyPostings(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<InternshipPostingSummaryResponseDto> response =
                postingService.getMyCompanyPostings(userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    // ─── GET /api/internship-postings/admin/all ───────────────────────────────────

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Tüm İlanları Listele (Admin)",
            description = "Admin tarafından sistemdeki tüm staj ilanlarını (tüm durumlar dahil) listeler. " +
                    "En son oluşturulan ilanlar önce gelir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tüm ilanlar başarıyla döndürüldü"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki (ROLE_ADMIN gerekli)")
    })
    public ResponseEntity<List<InternshipPostingSummaryResponseDto>> getAllPostings() {
        List<InternshipPostingSummaryResponseDto> response = postingService.getAllPostings();
        return ResponseEntity.ok(response);
    }

    // ─── PUT /api/internship-postings/{id} ───────────────────────────────────────

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    @Operation(
            summary = "İlanı Güncelle",
            description = "Belirtilen ID'ye sahip staj ilanını günceller. " +
                    "Şirket yetkilisi yalnızca kendi şirketine ait ilanları güncelleyebilir. " +
                    "Admin herhangi bir ilanı güncelleyebilir. " +
                    "Yalnızca gönderilen alanlar güncellenir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "İlan başarıyla güncellendi"),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek veya tarih hatası"),
            @ApiResponse(responseCode = "403", description = "Bu ilanı güncelleme yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "İlan bulunamadı")
    })
    public ResponseEntity<InternshipPostingResponseDto> updatePosting(
            @Parameter(description = "İlan ID'si") @PathVariable Long id,
            @Valid @RequestBody UpdateInternshipPostingRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        InternshipPostingResponseDto response = postingService.updatePosting(id, request, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    // ─── PATCH /api/internship-postings/{id}/status ───────────────────────────────

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    @Operation(
            summary = "İlan Durumunu Güncelle",
            description = "Belirtilen ID'ye sahip staj ilanının durumunu günceller. " +
                    "Şirket yetkilisi kendi ilanının durumunu değiştirebilir. " +
                    "Admin herhangi bir ilanın durumunu değiştirebilir. " +
                    "Son başvuru tarihi geçmiş bir ilan PUBLISHED yapılamaz."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "İlan durumu başarıyla güncellendi"),
            @ApiResponse(responseCode = "400", description = "Son başvuru tarihi geçmiş ilan yayınlanamaz"),
            @ApiResponse(responseCode = "403", description = "Bu ilanın durumunu değiştirme yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "İlan bulunamadı")
    })
    public ResponseEntity<InternshipPostingResponseDto> updatePostingStatus(
            @Parameter(description = "İlan ID'si") @PathVariable Long id,
            @Valid @RequestBody PostingStatusUpdateRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        InternshipPostingResponseDto response = postingService.updatePostingStatus(id, request, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    // ─── DELETE /api/internship-postings/{id} ────────────────────────────────────

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('COMPANY', 'ADMIN')")
    @Operation(
            summary = "İlanı Sil (Soft Delete)",
            description = "Belirtilen ID'ye sahip staj ilanını pasife alır (PASSIVE durumuna çeker). " +
                    "Fiziksel silme yapılmaz. " +
                    "Şirket yetkilisi yalnızca kendi şirketine ait ilanları pasife alabilir. " +
                    "Admin herhangi bir ilanı pasife alabilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "İlan başarıyla pasife alındı"),
            @ApiResponse(responseCode = "403", description = "Bu ilanı silme yetkiniz yok"),
            @ApiResponse(responseCode = "404", description = "İlan bulunamadı")
    })
    public ResponseEntity<Map<String, String>> deletePosting(
            @Parameter(description = "İlan ID'si") @PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        postingService.deletePosting(id, userDetails.getUser());
        return ResponseEntity.ok(Map.of(
                "message", "İlan başarıyla pasife alındı.",
                "postingId", String.valueOf(id)
        ));
    }
}
