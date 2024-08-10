package com.hiredhub.api.dto;

public record JobPostingSearchRequest(String companyName, String position) {
    public boolean hasCompanyName() {
        return companyName != null && !companyName.isBlank();
    }

    public boolean hasPosition() {
        return position != null && !position.isBlank();
    }

    public boolean hasAllParams() {
        return hasCompanyName() && hasPosition();
    }
}
