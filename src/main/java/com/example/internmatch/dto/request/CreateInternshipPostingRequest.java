package com.example.internmatch.dto.request;

import com.example.internmatch.enums.PostingStatus;
import com.example.internmatch.enums.WorkMode;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Schema(description = "Staj ilanı oluşturma isteği")
public class CreateInternshipPostingRequest {

    @NotBlank(message = "İlan başlığı boş olamaz")
    @Size(min = 3, max = 150, message = "Başlık 3-150 karakter arasında olmalıdır")
    @Schema(description = "İlan başlığı", example = "Backend Developer Stajyeri Aranıyor")
    private String title;

    @Schema(description = "İlan açıklaması", example = "Şirketimizde backend geliştirme üzerine çalışacak stajyerler arıyoruz.")
    private String description;

    @NotBlank(message = "Pozisyon adı boş olamaz")
    @Size(min = 2, max = 150, message = "Pozisyon adı 2-150 karakter arasında olmalıdır")
    @Schema(description = "Pozisyon adı", example = "Backend Developer Intern")
    private String positionName;

    @NotBlank(message = "Departman boş olamaz")
    @Size(max = 100, message = "Departman adı en fazla 100 karakter olabilir")
    @Schema(description = "Departman", example = "Software Engineering")
    private String department;

    @NotBlank(message = "Şehir boş olamaz")
    @Size(max = 100, message = "Şehir adı en fazla 100 karakter olabilir")
    @Schema(description = "Şehir", example = "Istanbul")
    private String city;

    @NotNull(message = "Çalışma modeli boş olamaz")
    @Schema(description = "Çalışma modeli", example = "HYBRID")
    private WorkMode workMode;

    @DecimalMin(value = "0.0", inclusive = true, message = "Minimum GPA 0.0'dan küçük olamaz")
    @DecimalMax(value = "4.0", inclusive = true, message = "Minimum GPA 4.0'dan büyük olamaz")
    @Schema(description = "Minimum not ortalaması (0.0-4.0)", example = "2.5")
    private BigDecimal minGpa;

    @Schema(description = "Tercih edilen sınıf seviyesi", example = "3. veya 4. Sınıf")
    private String preferredGradeLevel;

    @FutureOrPresent(message = "Başlangıç tarihi geçmiş bir tarih olamaz")
    @Schema(description = "Staj başlangıç tarihi", example = "2026-09-01")
    private LocalDate startDate;

    @Future(message = "Bitiş tarihi gelecekte olmalıdır")
    @Schema(description = "Staj bitiş tarihi", example = "2026-12-31")
    private LocalDate endDate;

    @FutureOrPresent(message = "Son başvuru tarihi geçmiş bir tarih olamaz")
    @Schema(description = "Son başvuru tarihi", example = "2026-08-31")
    private LocalDate applicationDeadline;

    @Min(value = 1, message = "Kontenjan en az 1 olmalıdır")
    @Schema(description = "Kontenjan sayısı", example = "3")
    private Integer quota;

    @Schema(description = "İlan durumu (belirtilmezse DRAFT olarak oluşturulur)", example = "DRAFT")
    private PostingStatus status;

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPositionName() { return positionName; }
    public void setPositionName(String positionName) { this.positionName = positionName; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public WorkMode getWorkMode() { return workMode; }
    public void setWorkMode(WorkMode workMode) { this.workMode = workMode; }

    public BigDecimal getMinGpa() { return minGpa; }
    public void setMinGpa(BigDecimal minGpa) { this.minGpa = minGpa; }

    public String getPreferredGradeLevel() { return preferredGradeLevel; }
    public void setPreferredGradeLevel(String preferredGradeLevel) { this.preferredGradeLevel = preferredGradeLevel; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public LocalDate getApplicationDeadline() { return applicationDeadline; }
    public void setApplicationDeadline(LocalDate applicationDeadline) { this.applicationDeadline = applicationDeadline; }

    public Integer getQuota() { return quota; }
    public void setQuota(Integer quota) { this.quota = quota; }

    public PostingStatus getStatus() { return status; }
    public void setStatus(PostingStatus status) { this.status = status; }
}
