package com.agrisetu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class LanguageUpdateRequest {

    @NotBlank(message = "Language code is required")
    @Pattern(regexp = "^(en|te|hi)$", message = "Language must be 'en' (English), 'te' (Telugu), or 'hi' (Hindi)")
    private String language;

    public LanguageUpdateRequest() {}

    public LanguageUpdateRequest(String language) {
        this.language = language;
    }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }
}
