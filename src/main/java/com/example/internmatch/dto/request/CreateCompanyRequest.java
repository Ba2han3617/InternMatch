package com.example.internmatch.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

/**
 * Yeni şirket profili oluşturma isteği.
 * ROLE_COMPANY yetkisine sahip kullanıcılar tarafından kullanılır.
 */
@Schema(description = "Şirket profili oluşturma isteği")
public class CreateCompanyRequest {

    @NotBlank(message = "Şirket adı boş olamaz")
    @Size(min = 2, max = 150, message = "Şirket adı 2-150 karakter arasında olmalıdır")
    @Schema(description = "Şirket adı", example = "TechNova Yazılım A.Ş.", required = true)
    private String name;

    @NotBlank(message = "Sektör boş olamaz")
    @Size(max = 100, message = "Sektör en fazla 100 karakter olabilir")
    @Schema(description = "Sektör", example = "Yazılım ve Teknoloji", required = true)
    private String industry;

    @NotBlank(message = "Şehir boş olamaz")
    @Size(max = 100, message = "Şehir en fazla 100 karakter olabilir")
    @Schema(description = "Şehir", example = "İstanbul", required = true)
    private String city;

    @Size(max = 150, message = "Konum en fazla 150 karakter olabilir")
    @Schema(description = "Bölge/ilçe bilgisi", example = "Maslak")
    private String location;

    @Size(max = 1000, message = "Adres en fazla 1000 karakter olabilir")
    @Schema(description = "Tam adres", example = "Maslak Mahallesi, Büyükdere Cad. No:123 Sarıyer/İstanbul")
    private String address;

    @Schema(description = "Şirket açıklaması", example = "Yazılım geliştirme ve teknoloji çözümleri sunan şirket")
    private String description;

    @Size(max = 255, message = "Web sitesi URL'si en fazla 255 karakter olabilir")
    @Schema(description = "Web sitesi URL", example = "https://www.technova.com.tr")
    private String website;

    @Email(message = "Geçerli bir e-posta adresi giriniz")
    @Size(max = 100, message = "E-posta en fazla 100 karakter olabilir")
    @Schema(description = "İletişim e-posta adresi", example = "info@technova.com.tr")
    private String contactEmail;

    @Pattern(regexp = "^[+]?[0-9\\s\\-().]{7,20}$", message = "Geçerli bir telefon numarası giriniz")
    @Schema(description = "İletişim telefon numarası", example = "+90 212 555 0100")
    private String contactPhone;

    @Size(max = 50, message = "Vergi numarası en fazla 50 karakter olabilir")
    @Schema(description = "Vergi numarası", example = "1234567890")
    private String taxNumber;

    // ─── Constructors ─────────────────────────────────────────────────────────────

    public CreateCompanyRequest() {}

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
