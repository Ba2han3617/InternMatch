package com.example.internmatch.service.impl;

import com.example.internmatch.dto.request.CompanyStatusUpdateRequest;
import com.example.internmatch.dto.request.CreateCompanyRequest;
import com.example.internmatch.dto.request.UpdateCompanyRequest;
import com.example.internmatch.dto.response.CompanyResponseDto;
import com.example.internmatch.dto.response.CompanySummaryResponseDto;
import com.example.internmatch.entity.Company;
import com.example.internmatch.entity.User;
import com.example.internmatch.enums.RoleName;
import com.example.internmatch.exception.BadRequestException;
import com.example.internmatch.exception.DuplicateResourceException;
import com.example.internmatch.exception.ResourceNotFoundException;
import com.example.internmatch.repository.CompanyRepository;
import com.example.internmatch.repository.UserRepository;
import com.example.internmatch.service.CompanyService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
    }

    // ─── Create ──────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public CompanyResponseDto createCompany(CreateCompanyRequest request, User currentUser) {
        // Kullanıcının ROLE_COMPANY rolü olup olmadığını kontrol et
        boolean hasCompanyRole = currentUser.getRoles().stream()
                .anyMatch(r -> r.getName() == RoleName.ROLE_COMPANY);
        if (!hasCompanyRole) {
            throw new BadRequestException(
                    "Şirket profili yalnızca ROLE_COMPANY yetkisine sahip kullanıcılar tarafından oluşturulabilir.");
        }

        // Kullanıcı zaten bir şirkete bağlı mı?
        if (currentUser.getCompany() != null) {
            throw new DuplicateResourceException(
                    "Bu kullanıcı zaten bir şirkete bağlı. Şirket güncellemek için PUT /api/companies/me kullanın.");
        }

        // Aynı şirket adı var mı?
        if (companyRepository.existsByName(request.getName())) {
            throw new DuplicateResourceException(
                    "'" + request.getName() + "' adında bir şirket zaten kayıtlı. Lütfen farklı bir isim kullanın.");
        }

        // Şirket oluştur
        Company company = Company.builder()
                .name(request.getName())
                .industry(request.getIndustry())
                .city(request.getCity())
                .location(request.getLocation())
                .address(request.getAddress())
                .description(request.getDescription())
                .website(request.getWebsite())
                .contactEmail(request.getContactEmail())
                .contactPhone(request.getContactPhone())
                .taxNumber(request.getTaxNumber())
                .build();

        Company savedCompany = companyRepository.save(company);

        // Kullanıcıyı şirkete bağla
        currentUser.setCompany(savedCompany);
        userRepository.save(currentUser);

        return mapToDto(savedCompany);
    }

    // ─── Read: My Company ────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public CompanyResponseDto getMyCompany(User currentUser) {
        Company company = companyRepository.findByOfficials_Id(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Şirket profili bulunamadı. Önce şirket oluşturun: POST /api/companies"));
        return mapToDto(company);
    }

    // ─── Update: My Company ──────────────────────────────────────────────────────

    @Override
    @Transactional
    public CompanyResponseDto updateMyCompany(UpdateCompanyRequest request, User currentUser) {
        Company company = companyRepository.findByOfficials_Id(currentUser.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Şirket profili bulunamadı. Önce şirket oluşturun: POST /api/companies"));

        // Şirket adı değiştirilmek isteniyorsa benzersizlik kontrolü
        if (request.getName() != null && !request.getName().equals(company.getName())) {
            if (companyRepository.existsByName(request.getName())) {
                throw new DuplicateResourceException(
                        "'" + request.getName() + "' adında bir şirket zaten kayıtlı. Lütfen farklı bir isim kullanın.");
            }
            company.setName(request.getName());
        }

        if (request.getIndustry() != null)    company.setIndustry(request.getIndustry());
        if (request.getCity() != null)         company.setCity(request.getCity());
        if (request.getLocation() != null)     company.setLocation(request.getLocation());
        if (request.getAddress() != null)      company.setAddress(request.getAddress());
        if (request.getDescription() != null)  company.setDescription(request.getDescription());
        if (request.getWebsite() != null)      company.setWebsite(request.getWebsite());
        if (request.getContactEmail() != null) company.setContactEmail(request.getContactEmail());
        if (request.getContactPhone() != null) company.setContactPhone(request.getContactPhone());
        if (request.getTaxNumber() != null)    company.setTaxNumber(request.getTaxNumber());

        Company updatedCompany = companyRepository.save(company);
        return mapToDto(updatedCompany);
    }

    // ─── Read: By ID ─────────────────────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public CompanyResponseDto getCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", id));
        return mapToDto(company);
    }

    // ─── Read: Active Companies (public list) ─────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<CompanySummaryResponseDto> getActiveCompanies() {
        return companyRepository.findByIsActiveTrue()
                .stream()
                .map(this::mapToSummaryDto)
                .collect(Collectors.toList());
    }

    // ─── Read: All Companies (Admin) ──────────────────────────────────────────────

    @Override
    @Transactional(readOnly = true)
    public List<CompanyResponseDto> getAllCompanies() {
        return companyRepository.findAll()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // ─── Admin: Update Verification Status ────────────────────────────────────────

    @Override
    @Transactional
    public CompanyResponseDto updateVerificationStatus(Long companyId, CompanyStatusUpdateRequest request) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));

        company.setVerificationStatus(request.getVerificationStatus());
        Company updated = companyRepository.save(company);
        return mapToDto(updated);
    }

    // ─── Admin: Update Active Status ──────────────────────────────────────────────

    @Override
    @Transactional
    public CompanyResponseDto updateActiveStatus(Long companyId, Boolean isActive) {
        if (isActive == null) {
            throw new BadRequestException("'isActive' parametresi boş olamaz.");
        }
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company", "id", companyId));

        company.setIsActive(isActive);
        Company updated = companyRepository.save(company);
        return mapToDto(updated);
    }

    // ─── Mapping Helpers ──────────────────────────────────────────────────────────

    private CompanyResponseDto mapToDto(Company company) {
        return CompanyResponseDto.builder()
                .id(company.getId())
                .name(company.getName())
                .taxNumber(company.getTaxNumber())
                .description(company.getDescription())
                .website(company.getWebsite())
                .industry(company.getIndustry())
                .city(company.getCity())
                .location(company.getLocation())
                .address(company.getAddress())
                .contactEmail(company.getContactEmail())
                .contactPhone(company.getContactPhone())
                .verificationStatus(company.getVerificationStatus())
                .isActive(company.getIsActive())
                .officialCount(company.getOfficials() != null ? company.getOfficials().size() : 0)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();
    }

    private CompanySummaryResponseDto mapToSummaryDto(Company company) {
        return CompanySummaryResponseDto.builder()
                .id(company.getId())
                .name(company.getName())
                .industry(company.getIndustry())
                .city(company.getCity())
                .verificationStatus(company.getVerificationStatus())
                .isActive(company.getIsActive())
                .website(company.getWebsite())
                .build();
    }
}
