package com.example.internmatch.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/**
 * Mevcut şirket profilini güncelleme isteği.
 * Tüm alanlar opsiyoneldir; yalnızca gönderilen alanlar güncellenir.
 */
@Schema(description = "Şirket profili güncelleme isteği (tüm alanlar opsiyonel)")
public class UpdateCompanyRequest {

    @Size(min = 2, max = 150, message = "Şirket adı 2-150 karakter arasında olmalıdır")
    @Schema(description = "Şirket adı", example = "TechNova Yazılım A.Ş.")
    private String name;

    @Size(max = 100, message = "Sektör en fazla 100 karakter olabilir")
    @Schema(description = "Sektör", example = "Yazılım ve Teknoloji")
    private String industry;

    @Size(max = 100, message = "Şehir en fazla 100 karakter olabilir")
    @Schema(description = "Şehir", example = "İstanbul")
    private String city;

    @Size(max = 150, message = "Konum en fazla 150 karakter olabilir")
    @Schema(description = "Bölge/ilçe bilgisi", example = "Maslak")
    private String location;

    @Size(max = 1000, message = "Adres en fazla 1000 karakter olabilir")
    @Schema(description = "Tam adres")
    private String address;

    @Schema(description = "Şirket açıklaması")
    private String description;

    @Size(max = 255, message = "Web sitesi URL'si en fazla 255 karakter olabilir")
    @Schema(description = "Web sitesi URL")
    private String website;

    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Size(max = 100, message = "E-posta en fazla 100 karakter olabilir")
    @Schema(description = "İletişim e-posta adresi")
    private String contactEmail;

    @Pattern(regexp = "^[+]?[0-9\\s\\-().]{7,20}$", message = "Geçerli bir telefon numarası giriniz")
    @Schema(description = "İletişim telefon numarası")
    private String contactPhone;

    @Size(max = 50, message = "Vergi numarası en fazla 50 karakter olabilir")
    @Schema(description = "Vergi numarası")
    private String taxNumber;

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public UpdateCompanyRequest() {}

    // ─── Getters & Setters ────────────────────────────────────────────────────────

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }

    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String contactEmail) { this.contactEmail = contactEmail; }

    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }

    public String getTaxNumber() { return taxNumber; }
    public void setTaxNumber(String taxNumber) { this.taxNumber = taxNumber; }
}
