package com.example.internmatch.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateSkillRequest {

    @NotBlank(message = "Beceri adı boş olamaz")
    @Size(max = 100, message = "Beceri adı en fazla 100 karakter olabilir")
    private String name;

    @Size(max = 50, message = "Kategori en fazla 50 karakter olabilir")
    private String category;

    public CreateSkillRequest() {
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
