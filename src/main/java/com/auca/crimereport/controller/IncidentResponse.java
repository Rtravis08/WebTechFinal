package com.auca.crimereport.controller;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IncidentResponse {
    private String message;
    private Long incidentId;
}
