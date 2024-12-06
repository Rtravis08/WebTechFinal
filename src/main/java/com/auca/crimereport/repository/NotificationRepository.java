package com.auca.crimereport.repository;

import com.auca.crimereport.model.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    @Transactional
    @Modifying
    @Query("DELETE FROM Notification n WHERE n.incident.incidentId = :incidentId")
    void deleteByIncidentId(Long incidentId);
}
