package com.example.internmatch.repository;

import com.example.internmatch.entity.Company;
import com.example.internmatch.enums.CompanyVerificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    /** Şirket adına göre arama */
    Optional<Company> findByName(String name);

    /** Şirket adı daha önce kullanılmış mı kontrol et */
    Boolean existsByName(String name);

    /** Belirli bir kullanıcıya bağlı şirketi bul (company.officials listesinde userId olan) */
    Optional<Company> findByOfficials_Id(Long userId);

    /** Aktif şirketleri listele */
    List<Company> findByIsActiveTrue();

    /** Aktif şirketleri doğrulama durumuna göre listele */
    List<Company> findByIsActiveTrueAndVerificationStatus(CompanyVerificationStatus verificationStatus);

    /** Doğrulama durumuna göre tüm şirketleri listele (admin için) */
    List<Company> findByVerificationStatus(CompanyVerificationStatus verificationStatus);

    /** Şirket adını içeren aktif şirketleri ara (case-insensitive) */
    List<Company> findByNameContainingIgnoreCaseAndIsActiveTrue(String name);
}
