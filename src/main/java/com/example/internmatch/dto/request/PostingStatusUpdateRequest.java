package com.example.internmatch.dto.request;

import com.example.internmatch.enums.PostingStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "İlan durumu güncelleme isteği")
public class PostingStatusUpdateRequest {

    @NotNull(message = "Durum (status) boş olamaz")
    @Schema(description = "Yeni ilan durumu", example = "PUBLISHED",
            allowableValues = {"DRAFT", "PUBLISHED", "CLOSED", "PASSIVE"})
    private PostingStatus status;

    public PostingStatus getStatus() { return status; }
    public void setStatus(PostingStatus status) { this.status = status; }
}
