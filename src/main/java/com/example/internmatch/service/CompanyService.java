package com.example.internmatch.service;

import com.example.internmatch.dto.request.CompanyStatusUpdateRequest;
import com.example.internmatch.dto.request.CreateCompanyRequest;
import com.example.internmatch.dto.request.UpdateCompanyRequest;
import com.example.internmatch.dto.response.CompanyResponseDto;
import com.example.internmatch.dto.response.CompanySummaryResponseDto;
import com.example.internmatch.entity.User;

import java.util.List;

public interface CompanyService {

    /**
     * Şirket profili oluşturur.
     * Yalnızca ROLE_COMPANY yetkisine sahip kullanıcılar yapabilir.
     * Şirket adı benzersiz olmalıdır.
     */
    CompanyResponseDto createCompany(CreateCompanyRequest request, User currentUser);

    /**
     * Giriş yapmış şirket yetkilisinin kendi şirket profilini döndürür.
     */
    CompanyResponseDto getMyCompany(User currentUser);

    /**
     * Giriş yapmış şirket yetkilisinin kendi şirket profilini günceller.
     * Yalnızca gönderilen alanlar güncellenir.
     */
    CompanyResponseDto updateMyCompany(UpdateCompanyRequest request, User currentUser);

    /**
     * ID ile belirli bir şirketi döndürür.
     * Herhangi bir oturum açmış kullanıcı görebilir (aktif şirketler için).
     */
    CompanyResponseDto getCompanyById(Long id);

    /**
     * Aktif şirketleri listeler.
     * Tüm oturum açmış kullanıcılar görebilir.
     */
    List<CompanySummaryResponseDto> getActiveCompanies();

    /**
     * Admin için tüm şirketleri listeler.
     */
    List<CompanyResponseDto> getAllCompanies();

    /**
     * Admin tarafından şirketin doğrulama durumunu günceller.
     * PENDING → VERIFIED veya REJECTED
     */
    CompanyResponseDto updateVerificationStatus(Long companyId, CompanyStatusUpdateRequest request);

    /**
     * Admin tarafından şirketi aktif veya pasif yapar.
     */
    CompanyResponseDto updateActiveStatus(Long companyId, Boolean isActive);
}
