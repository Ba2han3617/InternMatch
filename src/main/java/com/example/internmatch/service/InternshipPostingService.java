package com.example.internmatch.service;

import com.example.internmatch.dto.request.CreateInternshipPostingRequest;
import com.example.internmatch.dto.request.PostingStatusUpdateRequest;
import com.example.internmatch.dto.request.UpdateInternshipPostingRequest;
import com.example.internmatch.dto.response.InternshipPostingResponseDto;
import com.example.internmatch.dto.response.InternshipPostingSummaryResponseDto;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;

import java.math.BigDecimal;
import java.util.List;

public interface InternshipPostingService {

    /**
     * Şirket yetkilisi adına yeni staj ilanı oluşturur.
     * Yalnızca ROLE_COMPANY yetkisine sahip ve şirket profili olan kullanıcılar yapabilir.
     */
    InternshipPostingResponseDto createPosting(CreateInternshipPostingRequest request, User currentUser);

    /**
     * Belirtilen ID'ye sahip staj ilanının detaylarını döndürür.
     * PUBLISHED ilanları herkese, diğerleri yalnızca sahibi ve admine görünür.
     */
    InternshipPostingResponseDto getPostingById(Long id, User currentUser);

    /**
     * Giriş yapmış kullanıcının şirketine ait tüm ilanları listeler.
     * Yalnızca ROLE_COMPANY.
     */
    List<InternshipPostingSummaryResponseDto> getMyCompanyPostings(User currentUser);

    /**
     * Yayındaki ilanları listeler; opsiyonel filtreler uygulanabilir.
     * city, workMode, department, positionName, minGpa, status parametreleri ile filtrelenable.
     */
    List<InternshipPostingSummaryResponseDto> getPublishedPostings(
            String city,
            WorkMode workMode,
            String department,
            String positionName,
            BigDecimal minGpa,
            PostingStatus status);

    /**
     * Admin için tüm ilanları listeler.
     * Yalnızca ROLE_ADMIN.
     */
    List<InternshipPostingSummaryResponseDto> getAllPostings();

    /**
     * Şirket yetkilisi kendi ilanını günceller.
     * Sadece kendi şirketine ait ilanı güncelleyebilir.
     */
    InternshipPostingResponseDto updatePosting(Long id, UpdateInternshipPostingRequest request, User currentUser);

    /**
     * İlan durumunu günceller.
     * Şirket yetkilisi kendi ilanını; Admin herhangi bir ilanı değiştirebilir.
     */
    InternshipPostingResponseDto updatePostingStatus(Long id, PostingStatusUpdateRequest request, User currentUser);

    /**
     * İlanı siler (fiziksel silme yerine PASSIVE durumuna çeker).
     * Şirket yetkilisi kendi ilanını; Admin herhangi bir ilanı pasife alabilir.
     */
    void deletePosting(Long id, User currentUser);
}
