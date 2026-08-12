package com.example.internmatch.controller;

import com.example.internmatch.dto.request.CompanyStatusUpdateRequest;
import com.example.internmatch.dto.request.CreateCompanyRequest;
import com.example.internmatch.dto.request.UpdateCompanyRequest;
import com.example.internmatch.dto.response.CompanyResponseDto;
import com.example.internmatch.dto.response.CompanySummaryResponseDto;
import com.example.internmatch.security.CustomUserDetails;
import com.example.internmatch.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/companies")
@Tag(name = "Company Profile", description = "Şirket profil yönetimi işlemleri")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    // ─── POST /api/companies ──────────────────────────────────────────────────────

    @PostMapping
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "Şirket Profili Oluştur",
            description = "ROLE_COMPANY yetkisine sahip kullanıcı tarafından yeni şirket profili oluşturulur. " +
                    "Şirket adı benzersiz olmalıdır. Her kullanıcı yalnızca bir şirkete bağlanabilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Şirket başarıyla oluşturuldu"),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek veya kullanıcı zaten bir şirkete bağlı"),
            @ApiResponse(responseCode = "409", description = "Aynı isimde şirket zaten mevcut"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki (ROLE_COMPANY gerekli)")
    })
    public ResponseEntity<CompanyResponseDto> createCompany(
            @Valid @RequestBody CreateCompanyRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CompanyResponseDto response = companyService.createCompany(request, userDetails.getUser());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // ─── GET /api/companies/me ────────────────────────────────────────────────────

    @GetMapping("/me")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "Kendi Şirket Profilimi Görüntüle",
            description = "Giriş yapmış şirket yetkilisinin bağlı olduğu şirketin profil bilgilerini döndürür."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Şirket profili başarıyla döndürüldü"),
            @ApiResponse(responseCode = "404", description = "Henüz şirket profili oluşturulmamış"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki")
    })
    public ResponseEntity<CompanyResponseDto> getMyCompany(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CompanyResponseDto response = companyService.getMyCompany(userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    // ─── PUT /api/companies/me ────────────────────────────────────────────────────

    @PutMapping("/me")
    @PreAuthorize("hasRole('COMPANY')")
    @Operation(
            summary = "Kendi Şirket Profilimi Güncelle",
            description = "Giriş yapmış şirket yetkilisinin kendi şirket profilini günceller. " +
                    "Yalnızca gönderilen alanlar güncellenir. Şirket adı değiştirilmek istenirse benzersiz olmalıdır."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Şirket profili başarıyla güncellendi"),
            @ApiResponse(responseCode = "404", description = "Şirket profili bulunamadı"),
            @ApiResponse(responseCode = "409", description = "Güncellenmeye çalışılan şirket adı zaten kullanımda"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki")
    })
    public ResponseEntity<CompanyResponseDto> updateMyCompany(
            @Valid @RequestBody UpdateCompanyRequest request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        CompanyResponseDto response = companyService.updateMyCompany(request, userDetails.getUser());
        return ResponseEntity.ok(response);
    }

    // ─── GET /api/companies ───────────────────────────────────────────────────────

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Aktif Şirketleri Listele",
            description = "Sistemdeki aktif şirketlerin özet listesini döndürür. Tüm oturum açmış kullanıcılar görebilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Şirket listesi başarıyla döndürüldü"),
            @ApiResponse(responseCode = "401", description = "Oturum açılmamış")
    })
    public ResponseEntity<List<CompanySummaryResponseDto>> getActiveCompanies() {
        List<CompanySummaryResponseDto> response = companyService.getActiveCompanies();
        return ResponseEntity.ok(response);
    }

    // ─── GET /api/companies/{id} ──────────────────────────────────────────────────

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(
            summary = "Şirketi ID ile Görüntüle",
            description = "Belirli bir şirketin tam profil bilgilerini ID ile döndürür. Tüm oturum açmış kullanıcılar görebilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Şirket başarıyla döndürüldü"),
            @ApiResponse(responseCode = "404", description = "Şirket bulunamadı"),
            @ApiResponse(responseCode = "401", description = "Oturum açılmamış")
    })
    public ResponseEntity<CompanyResponseDto> getCompanyById(
            @Parameter(description = "Şirket ID'si") @PathVariable Long id) {
        CompanyResponseDto response = companyService.getCompanyById(id);
        return ResponseEntity.ok(response);
    }

    // ─── PATCH /api/companies/{id}/verification-status (Admin) ───────────────────

    @PatchMapping("/{id}/verification-status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Şirket Doğrulama Durumunu Güncelle (Admin)",
            description = "Admin tarafından belirli bir şirketin doğrulama durumunu günceller. " +
                    "Durum: PENDING, VERIFIED veya REJECTED olabilir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Doğrulama durumu başarıyla güncellendi"),
            @ApiResponse(responseCode = "404", description = "Şirket bulunamadı"),
            @ApiResponse(responseCode = "400", description = "Geçersiz durum değeri"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki (ROLE_ADMIN gerekli)")
    })
    public ResponseEntity<CompanyResponseDto> updateVerificationStatus(
            @Parameter(description = "Şirket ID'si") @PathVariable Long id,
            @Valid @RequestBody CompanyStatusUpdateRequest request) {
        CompanyResponseDto response = companyService.updateVerificationStatus(id, request);
        return ResponseEntity.ok(response);
    }

    // ─── PATCH /api/companies/{id}/active-status (Admin) ─────────────────────────

    @PatchMapping("/{id}/active-status")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Şirketi Aktif/Pasif Yap (Admin)",
            description = "Admin tarafından belirli bir şirketi aktif veya pasif duruma getirir. " +
                    "Request body'de {\"isActive\": true} veya {\"isActive\": false} gönderilmelidir."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Aktif durum başarıyla güncellendi"),
            @ApiResponse(responseCode = "404", description = "Şirket bulunamadı"),
            @ApiResponse(responseCode = "400", description = "Geçersiz istek"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki (ROLE_ADMIN gerekli)")
    })
    public ResponseEntity<CompanyResponseDto> updateActiveStatus(
            @Parameter(description = "Şirket ID'si") @PathVariable Long id,
            @RequestBody Map<String, Boolean> body) {
        Boolean isActive = body.get("isActive");
        CompanyResponseDto response = companyService.updateActiveStatus(id, isActive);
        return ResponseEntity.ok(response);
    }

    // ─── GET /api/companies/admin/all (Admin) ─────────────────────────────────────

    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(
            summary = "Tüm Şirketleri Listele (Admin)",
            description = "Admin tarafından sistemdeki tüm şirketleri (aktif + pasif) tam detaylarıyla listeler."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Tüm şirketler başarıyla döndürüldü"),
            @ApiResponse(responseCode = "403", description = "Yetersiz yetki (ROLE_ADMIN gerekli)")
    })
    public ResponseEntity<List<CompanyResponseDto>> getAllCompanies() {
        List<CompanyResponseDto> response = companyService.getAllCompanies();
        return ResponseEntity.ok(response);
    }
}
