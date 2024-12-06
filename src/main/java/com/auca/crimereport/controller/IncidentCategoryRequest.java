package com.auca.crimereport.controller;

public class IncidentCategoryRequest {

    private Long incidentId;
    private Long categoryId;

    // getters and setters
    public Long getIncidentId() {
        return incidentId;
    }

    public void setIncidentId(Long incidentId) {
        this.incidentId = incidentId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
