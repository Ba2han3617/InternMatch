package com.example.internmatch.repository;

import com.example.internmatch.entity.InternshipPosting;
import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InternshipPostingRepository
        extends JpaRepository<InternshipPosting, Long>, JpaSpecificationExecutor<InternshipPosting> {

    // ─── Şirkete göre listeleme ───────────────────────────────────────────────────

    /** Belirli bir şirkete ait tüm ilanları listeler */
    List<InternshipPosting> findByCompanyId(Long companyId);

    /** Belirli bir şirkete ait ilanları duruma göre filtreler */
    List<InternshipPosting> findByCompanyIdAndStatus(Long companyId, PostingStatus status);

    // ─── Duruma göre listeleme ────────────────────────────────────────────────────

    /** Belirli bir durumdaki tüm ilanları listeler */
    List<InternshipPosting> findByStatus(PostingStatus status);

    /** Belirli bir durumdaki ilanları oluşturulma tarihine göre sıralı listeler */
    List<InternshipPosting> findByStatusOrderByCreatedAtDesc(PostingStatus status);

    // ─── Aktif/yayındaki ilanlar ──────────────────────────────────────────────────

    /** Son başvuru tarihi geçmemiş PUBLISHED ilanları listeler */
    @Query("SELECT p FROM InternshipPosting p WHERE p.status = 'PUBLISHED' " +
           "AND (p.applicationDeadline IS NULL OR p.applicationDeadline >= :today) " +
           "ORDER BY p.createdAt DESC")
    List<InternshipPosting> findActivePublishedPostings(@Param("today") LocalDate today);

    // ─── Filtreleme metotları ─────────────────────────────────────────────────────

    /** Şehir, çalışma modeli, departman ve pozisyon adına göre PUBLISHED ilanları filtreler */
    @Query("SELECT p FROM InternshipPosting p WHERE p.status = 'PUBLISHED' " +
           "AND (p.applicationDeadline IS NULL OR p.applicationDeadline >= :today) " +
           "AND (:city IS NULL OR LOWER(p.city) LIKE LOWER(CONCAT('%', :city, '%'))) " +
           "AND (:workMode IS NULL OR p.workMode = :workMode) " +
           "AND (:department IS NULL OR LOWER(p.department) LIKE LOWER(CONCAT('%', :department, '%'))) " +
           "AND (:positionName IS NULL OR LOWER(p.positionName) LIKE LOWER(CONCAT('%', :positionName, '%'))) " +
           "ORDER BY p.createdAt DESC")
    List<InternshipPosting> findPublishedWithFilters(
            @Param("today") LocalDate today,
            @Param("city") String city,
            @Param("workMode") WorkMode workMode,
            @Param("department") String department,
            @Param("positionName") String positionName);

    // ─── Admin: tüm ilanlar ───────────────────────────────────────────────────────

    /** Admin için tüm ilanları oluşturulma tarihine göre sıralı listeler */
    List<InternshipPosting> findAllByOrderByCreatedAtDesc();

    // ─── Son başvuru tarihi kontrolü ──────────────────────────────────────────────

    /** Son başvuru tarihi geçmiş PUBLISHED ilanları listeler (temizleme amaçlı) */
    @Query("SELECT p FROM InternshipPosting p WHERE p.status = 'PUBLISHED' " +
           "AND p.applicationDeadline IS NOT NULL AND p.applicationDeadline < :today")
    List<InternshipPosting> findExpiredPublishedPostings(@Param("today") LocalDate today);
}
