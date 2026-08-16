package com.example.internmatch.dto.request;

import com.example.internmatch.enums.CompanyVerificationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

/**
 * Admin tarafından şirket doğrulama durumunu güncellemek için kullanılır.
 */
@Schema(description = "Şirket doğrulama durumu güncelleme isteği (Admin yetkisi gerekir)")
public class CompanyStatusUpdateRequest {

    @NotNull(message = "Doğrulama durumu boş olamaz")
    @Schema(
            description = "Şirket doğrulama durumu",
            example = "VERIFIED",
            allowableValues = {"PENDING", "VERIFIED", "REJECTED"},
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private CompanyVerificationStatus verificationStatus;

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public CompanyStatusUpdateRequest() {}

    public CompanyStatusUpdateRequest(CompanyVerificationStatus verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public CompanyVerificationStatus getVerificationStatus() { return verificationStatus; }
    public void setVerificationStatus(CompanyVerificationStatus verificationStatus) { this.verificationStatus = verificationStatus; }
}
