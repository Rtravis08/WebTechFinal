package com.auca.crimereport.service;

import com.auca.crimereport.controller.IncidentCategoryRequest;
import com.auca.crimereport.model.Category;
import com.auca.crimereport.model.Incident;
import com.auca.crimereport.model.IncidentCategory;
import com.auca.crimereport.repository.CategoryRepository;
import com.auca.crimereport.repository.IncidentCategoryRepository;
import com.auca.crimereport.repository.IncidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentCategoryService {

    @Autowired
    private IncidentCategoryRepository incidentCategoryRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public List<IncidentCategory> getAllIncidentCategories() {
        return incidentCategoryRepository.findAll();
    }

    public Optional<IncidentCategory> getIncidentCategoryById(Long id) {
        return incidentCategoryRepository.findById(id);
    }

    public IncidentCategory createIncidentCategory(IncidentCategoryRequest request) throws Exception {
        Incident incident = incidentRepository.findById(request.getIncidentId())
                .orElseThrow(() -> new Exception("Incident not found with id " + request.getIncidentId()));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new Exception("Category not found with id " + request.getCategoryId()));

        if (incidentCategoryRepository.findByIncidentAndCategory(incident, category).isPresent()) {
            throw new Exception("Incident category with specified incident and category already exists.");
        }

        IncidentCategory incidentCategory = new IncidentCategory();
        incidentCategory.setIncident(incident);
        incidentCategory.setCategory(category);

        return incidentCategoryRepository.save(incidentCategory);
    }

    public IncidentCategory updateIncidentCategory(Long id, IncidentCategory incidentCategoryDetails) throws Exception {
        Optional<IncidentCategory> optionalIncidentCategory = incidentCategoryRepository.findById(id);
        if (optionalIncidentCategory.isPresent()) {
            IncidentCategory incidentCategory = optionalIncidentCategory.get();

            if (incidentCategoryDetails.getIncident() == null || incidentCategoryDetails.getCategory() == null) {
                throw new Exception("Incident or Category cannot be null.");
            }

            incidentCategory.setIncident(incidentCategoryDetails.getIncident());
            incidentCategory.setCategory(incidentCategoryDetails.getCategory());
            return incidentCategoryRepository.save(incidentCategory);
        }
        throw new Exception("Incident category not found with id " + id);
    }

    public boolean deleteIncidentCategory(Long id) throws Exception {
        if (incidentCategoryRepository.existsById(id)) {
            incidentCategoryRepository.deleteById(id);
            return true;
        }
        throw new Exception("Incident category not found with id " + id);
    }
}
