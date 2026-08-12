package com.example.internmatch.enums;

/**
 * Şirket doğrulama durumu.
 * PENDING  : Admin onayı bekliyor (varsayılan)
 * VERIFIED : Admin tarafından doğrulandı
 * REJECTED : Admin tarafından reddedildi
 */
public enum CompanyVerificationStatus {
    PENDING,
    VERIFIED,
    REJECTED
}
