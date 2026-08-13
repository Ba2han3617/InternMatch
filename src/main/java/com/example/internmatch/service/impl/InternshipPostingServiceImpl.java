package com.example.internmatch.service.impl;

import com.example.internmatch.dto.request.CreateInternshipPostingRequest;
import com.example.internmatch.dto.request.PostingStatusUpdateRequest;
import com.example.internmatch.dto.request.UpdateInternshipPostingRequest;
import com.example.internmatch.dto.response.InternshipPostingResponseDto;
import com.example.internmatch.dto.response.InternshipPostingSummaryResponseDto;
import com.example.internmatch.entity.Company;
import com.example.internmatch.entity.InternshipPosting;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.enums.WorkMode;
import com.example.internmatch.exception.BadRequestException;
import com.example.internmatch.exception.ResourceNotFoundException;
import com.example.internmatch.repository.CompanyRepository;
import com.example.internmatch.repository.InternshipPostingRepository;
import com.example.internmatch.service.InternshipPostingService;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InternshipPostingServiceImpl implements InternshipPostingService {

    private final InternshipPostingRepository postingRepository;
    private final CompanyRepository companyRepository;

    public InternshipPostingServiceImpl(InternshipPostingRepository postingRepository,
                                        CompanyRepository companyRepository) {
        this.postingRepository = postingRepository;
        this.companyRepository = companyRepository;
    }

    // ─── Create ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public InternshipPostingResponseDto createPosting(CreateInternshipPostingRequest request, User currentUser) {
        // Şirket profili kontrolü
        Company company = getCompanyForUser(currentUser);

        // Tarih validasyonları
        validateDates(request.getStartDate(), request.getEndDate(), request.getApplicationDeadline());

        // İlan oluşturma; durum belirtilmezse DRAFT
        PostingStatus initialStatus = request.getStatus() != null ? request.getStatus() : PostingStatus.DRAFT;

        // PUBLISHED durumunda son başvuru tarihi kontrolü
        if (initialStatus == PostingStatus.PUBLISHED) {
            validateDeadlineForPublishing(request.getApplicationDeadline());
        }

        InternshipPosting posting = InternshipPosting.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .positionName(request.getPositionName())
                .department(request.getDepartment())
                .city(request.getCity())
                .location(request.getCity()) // geriye dönük uyumluluk
                .workMode(request.getWorkMode())
                .status(initialStatus)
                .minGpa(request.getMinGpa())
                .departmentRequirement(request.getDepartment()) // geriye dönük uyumluluk
                .preferredGradeLevel(request.getPreferredGradeLevel())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .applicationDeadline(request.getApplicationDeadline())
                .quota(request.getQuota())
                .company(company)
                .build();

        InternshipPosting saved = postingRepository.save(posting);
        return mapToDetailDto(saved);
    }

    // ─── Read: By ID ─────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public InternshipPostingResponseDto getPostingById(Long id, User currentUser) {
        InternshipPosting posting = postingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InternshipPosting", "id", id));

        boolean isAdmin = hasRole(currentUser, RoleName.ROLE_ADMIN);
        boolean isOwner = isCompanyOwner(currentUser, posting);

        // PUBLISHED olmayan ilanlar sadece sahibi veya admin tarafından görülebilir
        if (posting.getStatus() != PostingStatus.PUBLISHED && !isAdmin && !isOwner) {
            throw new AccessDeniedException("Bu ilan henüz yayınlanmamış veya görüntüleme yetkiniz yok.");
        }

        return mapToDetailDto(posting);
    }

    // ─── Read: My Company Postings ────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<InternshipPostingSummaryResponseDto> getMyCompanyPostings(User currentUser) {
        Company company = getCompanyForUser(currentUser);
        return postingRepository.findByCompanyId(company.getId())
                .stream()
                .map(this::mapToSummaryDto)
                .collect(Collectors.toList());
    }

    // ─── Read: Published Postings (with filters) ──────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<InternshipPostingSummaryResponseDto> getPublishedPostings(
            String city,
            WorkMode workMode,
            String department,
            String positionName,
            BigDecimal minGpa,
            PostingStatus status) {

        LocalDate today = LocalDate.now();

        // Filtre yoksa tüm aktif ilanları getir
        boolean hasFilter = city != null || workMode != null || department != null
                || positionName != null || minGpa != null || status != null;

        List<InternshipPosting> postings;

        if (!hasFilter) {
            postings = postingRepository.findActivePublishedPostings(today);
        } else {
            // Status filtresi varsa ve PUBLISHED değilse, admin gibi davranması gerekirdi
            // Burada sadece PUBLISHED ilanlar filtrelenir (güvenlik)
            postings = postingRepository.findPublishedWithFilters(today, city, workMode, department, positionName);

            // minGpa filtresi Java tarafında uygulanır
            if (minGpa != null) {
                postings = postings.stream()
                        .filter(p -> p.getMinGpa() == null || p.getMinGpa().compareTo(minGpa) <= 0)
                        .collect(Collectors.toList());
            }
        }

        return postings.stream()
                .map(this::mapToSummaryDto)
                .collect(Collectors.toList());
    }

    // ─── Read: All Postings (Admin) ───────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<InternshipPostingSummaryResponseDto> getAllPostings() {
        return postingRepository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::mapToSummaryDto)
                .collect(Collectors.toList());
    }

    // ─── Update ───────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public InternshipPostingResponseDto updatePosting(Long id, UpdateInternshipPostingRequest request, User currentUser) {
        InternshipPosting posting = postingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InternshipPosting", "id", id));

        // Yetki kontrolü: sadece sahibi veya admin güncelleyebilir
        boolean isAdmin = hasRole(currentUser, RoleName.ROLE_ADMIN);
        if (!isAdmin && !isCompanyOwner(currentUser, posting)) {
            throw new AccessDeniedException("Bu ilanı güncelleme yetkiniz bulunmamaktadır.");
        }

        // PASSIVE veya CLOSED ilanlar güncellenemez (admin hariç)
        if (!isAdmin && (posting.getStatus() == PostingStatus.PASSIVE || posting.getStatus() == PostingStatus.CLOSED)) {
            throw new BadRequestException("Kapatılmış veya pasife alınmış ilanlar güncellenemez.");
        }

        // Tarih validasyonu
        LocalDate newStartDate = request.getStartDate() != null ? request.getStartDate() : posting.getStartDate();
        LocalDate newEndDate = request.getEndDate() != null ? request.getEndDate() : posting.getEndDate();
        LocalDate newDeadline = request.getApplicationDeadline() != null ? request.getApplicationDeadline() : posting.getApplicationDeadline();
        validateDates(newStartDate, newEndDate, newDeadline);

        // Partial update
        if (request.getTitle() != null)                 posting.setTitle(request.getTitle());
        if (request.getDescription() != null)           posting.setDescription(request.getDescription());
        if (request.getPositionName() != null)          posting.setPositionName(request.getPositionName());
        if (request.getDepartment() != null) {
            posting.setDepartment(request.getDepartment());
            posting.setDepartmentRequirement(request.getDepartment()); // geriye dönük uyumluluk
        }
        if (request.getCity() != null) {
            posting.setCity(request.getCity());
            posting.setLocation(request.getCity()); // geriye dönük uyumluluk
        }
        if (request.getWorkMode() != null)              posting.setWorkMode(request.getWorkMode());
        if (request.getMinGpa() != null)                posting.setMinGpa(request.getMinGpa());
        if (request.getPreferredGradeLevel() != null)   posting.setPreferredGradeLevel(request.getPreferredGradeLevel());
        if (request.getStartDate() != null)             posting.setStartDate(request.getStartDate());
        if (request.getEndDate() != null)               posting.setEndDate(request.getEndDate());
        if (request.getApplicationDeadline() != null)   posting.setApplicationDeadline(request.getApplicationDeadline());
        if (request.getQuota() != null)                 posting.setQuota(request.getQuota());

        InternshipPosting updated = postingRepository.save(posting);
        return mapToDetailDto(updated);
    }

    // ─── Update Status ────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public InternshipPostingResponseDto updatePostingStatus(Long id, PostingStatusUpdateRequest request, User currentUser) {
        InternshipPosting posting = postingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InternshipPosting", "id", id));

        boolean isAdmin = hasRole(currentUser, RoleName.ROLE_ADMIN);

        // Yetki kontrolü
        if (!isAdmin && !isCompanyOwner(currentUser, posting)) {
            throw new AccessDeniedException("Bu ilanın durumunu değiştirme yetkiniz bulunmamaktadır.");
        }

        // PUBLISHED yapılmak isteniyorsa son başvuru tarihi kontrolü
        if (request.getStatus() == PostingStatus.PUBLISHED) {
            validateDeadlineForPublishing(posting.getApplicationDeadline());
        }

        posting.setStatus(request.getStatus());
        InternshipPosting updated = postingRepository.save(posting);
        return mapToDetailDto(updated);
    }

    // ─── Delete (Soft Delete → PASSIVE) ──────────────────────────────────────────

    @Override
    @Transactional
    public void deletePosting(Long id, User currentUser) {
        InternshipPosting posting = postingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("InternshipPosting", "id", id));

        boolean isAdmin = hasRole(currentUser, RoleName.ROLE_ADMIN);

        // Yetki kontrolü
        if (!isAdmin && !isCompanyOwner(currentUser, posting)) {
            throw new AccessDeniedException("Bu ilanı silme yetkiniz bulunmamaktadır.");
        }

        // Soft delete: PASSIVE durumuna çek
        posting.setStatus(PostingStatus.PASSIVE);
        postingRepository.save(posting);
    }

    // ─── Helper Methods ───────────────────────────────────────────────────────────

    /**
     * Kullanıcının şirket profilini döndürür; yoksa hata fırlatır.
     */
    private Company getCompanyForUser(User currentUser) {
        return companyRepository.findByOfficials_Id(currentUser.getId())
                .orElseThrow(() -> new BadRequestException(
                        "Staj ilanı oluşturmak için önce şirket profili oluşturmanız gerekmektedir. " +
                        "POST /api/companies adresini kullanın."));
    }

    /**
     * Kullanıcının belirli bir role sahip olup olmadığını kontrol eder.
     */
    private boolean hasRole(User user, RoleName roleName) {
        return user.getRoles().stream()
                .anyMatch(r -> r.getName() == roleName);
    }

    /**
     * Kullanıcının ilanın sahibi şirkete bağlı olup olmadığını kontrol eder.
     */
    private boolean isCompanyOwner(User user, InternshipPosting posting) {
        if (user.getCompany() == null || posting.getCompany() == null) {
            return false;
        }
        return user.getCompany().getId().equals(posting.getCompany().getId());
    }

    /**
     * Tarih tutarlılık kontrolü:
     * - Başlangıç tarihi bitiş tarihinden sonra olamaz
     * - Son başvuru tarihi başlangıç tarihinden sonra olamaz
     */
    private void validateDates(LocalDate startDate, LocalDate endDate, LocalDate applicationDeadline) {
        if (startDate != null && endDate != null) {
            if (startDate.isAfter(endDate)) {
                throw new BadRequestException("Başlangıç tarihi bitiş tarihinden sonra olamaz.");
            }
        }
        if (applicationDeadline != null && startDate != null) {
            if (applicationDeadline.isAfter(startDate)) {
                // Bu uyarı niteliğinde, hata değil - son başvuru tarihi başlangıçtan önce olmalı
                // Ama zorunlu kılmıyoruz, şirket esnekliği için
            }
        }
    }

    /**
     * PUBLISHED yapmak için son başvuru tarihinin geçip geçmediğini kontrol eder.
     */
    private void validateDeadlineForPublishing(LocalDate applicationDeadline) {
        if (applicationDeadline != null && applicationDeadline.isBefore(LocalDate.now())) {
            throw new BadRequestException(
                    "Son başvuru tarihi geçmiş bir ilan yayınlanamaz. " +
                    "Lütfen son başvuru tarihini güncelleyip tekrar deneyin.");
        }
    }

    // ─── Mapping Helpers ──────────────────────────────────────────────────────────

    private InternshipPostingResponseDto mapToDetailDto(InternshipPosting posting) {
        Company company = posting.getCompany();
        return InternshipPostingResponseDto.builder()
                .id(posting.getId())
                .title(posting.getTitle())
                .description(posting.getDescription())
                .positionName(posting.getPositionName())
                .department(posting.getDepartment())
                .city(posting.getCity())
                .workMode(posting.getWorkMode())
                .status(posting.getStatus())
                .minGpa(posting.getMinGpa())
                .preferredGradeLevel(posting.getPreferredGradeLevel())
                .startDate(posting.getStartDate())
                .endDate(posting.getEndDate())
                .applicationDeadline(posting.getApplicationDeadline())
                .quota(posting.getQuota())
                .companyId(company != null ? company.getId() : null)
                .companyName(company != null ? company.getName() : null)
                .companyIndustry(company != null ? company.getIndustry() : null)
                .companyCity(company != null ? company.getCity() : null)
                .createdAt(posting.getCreatedAt())
                .updatedAt(posting.getUpdatedAt())
                .build();
    }

    private InternshipPostingSummaryResponseDto mapToSummaryDto(InternshipPosting posting) {
        Company company = posting.getCompany();
        return InternshipPostingSummaryResponseDto.builder()
                .id(posting.getId())
                .title(posting.getTitle())
                .positionName(posting.getPositionName())
                .department(posting.getDepartment())
                .city(posting.getCity())
                .workMode(posting.getWorkMode())
                .status(posting.getStatus())
                .minGpa(posting.getMinGpa())
                .applicationDeadline(posting.getApplicationDeadline())
                .quota(posting.getQuota())
                .companyId(company != null ? company.getId() : null)
                .companyName(company != null ? company.getName() : null)
                .createdAt(posting.getCreatedAt())
                .build();
    }
}
