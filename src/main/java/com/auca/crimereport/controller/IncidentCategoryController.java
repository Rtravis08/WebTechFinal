package com.auca.crimereport.controller;


import com.auca.crimereport.model.IncidentCategory;
import com.auca.crimereport.service.IncidentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/incident-categories")
public class IncidentCategoryController {

    @Autowired
    private IncidentCategoryService incidentCategoryService;

    @GetMapping
    public List<IncidentCategory> getAllIncidentCategories() {
        return incidentCategoryService.getAllIncidentCategories();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IncidentCategory> getIncidentCategoryById(@PathVariable Long id) {
        Optional<IncidentCategory> incidentCategory = incidentCategoryService.getIncidentCategoryById(id);
        if (incidentCategory.isPresent()) {
            return ResponseEntity.ok(incidentCategory.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<IncidentCategory> createIncidentCategory(@RequestBody IncidentCategoryRequest request) {
        try {
            IncidentCategory createdIncidentCategory = incidentCategoryService.createIncidentCategory(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdIncidentCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<IncidentCategory> updateIncidentCategory(@PathVariable Long id, @RequestBody IncidentCategory incidentCategoryDetails) {
        try {
            IncidentCategory updatedIncidentCategory = incidentCategoryService.updateIncidentCategory(id, incidentCategoryDetails);
            return ResponseEntity.ok(updatedIncidentCategory);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncidentCategory(@PathVariable Long id) {
        try {
            if (incidentCategoryService.deleteIncidentCategory(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
