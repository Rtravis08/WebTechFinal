package com.auca.crimereport.repository;

import com.auca.crimereport.model.Category;
import com.auca.crimereport.model.Incident;
import com.auca.crimereport.model.IncidentCategory;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncidentCategoryRepository extends JpaRepository<IncidentCategory, Long> {
    Optional<IncidentCategory> findByIncidentAndCategory(Incident incident, Category category);

    @Transactional
    @Modifying
    @Query("DELETE FROM IncidentCategory ic WHERE ic.incident.incidentId = :incidentId")
    void deleteByIncidentId(Long incidentId);
}
