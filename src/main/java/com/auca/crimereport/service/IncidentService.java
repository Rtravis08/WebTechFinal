package com.auca.crimereport.service;

import com.auca.crimereport.controller.IncidentResponse;
import com.auca.crimereport.model.Incident;
import com.auca.crimereport.model.User;
import com.auca.crimereport.repository.IncidentCategoryRepository;
import com.auca.crimereport.repository.IncidentRepository;
import com.auca.crimereport.repository.NotificationRepository;
import com.auca.crimereport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private IncidentCategoryRepository incidentCategoryRepository;

    public List<Incident> getAllIncidents() {
        return incidentRepository.findAll();
    }

    public Optional<Incident> getIncidentById(Long id) {
        return incidentRepository.findById(id);
    }

//    public Incident createIncident(Incident incident) {
//        incident.setReportedAt(LocalDateTime.now());
//        return incidentRepository.save(incident);
//    }
//public Incident createIncident(Long userId, Incident incident) {
//
//    System.out.println("#### userId: " + userId);
//
//    Optional<User> user = userRepository.findById(userId);
//    if (user.isPresent()) {
//        incident.setUser(user.get());
//        incident.setReportedAt(LocalDateTime.now());
//        return incidentRepository.save(incident);
//    } else {
//        throw new IllegalArgumentException("User not found");
//    }
//}
public IncidentResponse createIncident(Long userId, Incident incident) throws Exception {
    Optional<User> user = userRepository.findById(userId);
    if (user.isPresent()) {
        incident.setUser(user.get());
        incident.setReportedAt(LocalDateTime.now());
        Incident savedIncident = incidentRepository.save(incident);
        return new IncidentResponse("Incident created successfully", savedIncident.getIncidentId());
    } else {
        throw new IllegalArgumentException("User not found");
    }
}


    public Incident updateIncident(Long userId, Long incidentId, Incident incidentDetails) throws Exception {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) {
            throw new IllegalArgumentException("User not found");
        }

        Optional<Incident> optionalIncident = incidentRepository.findById(incidentId);
        if (!optionalIncident.isPresent()) {
            throw new IllegalArgumentException("Incident not found");
        }

        Incident incident = optionalIncident.get();
        incident.setTitle(incidentDetails.getTitle());
        incident.setDescription(incidentDetails.getDescription());
        incident.setLatitude(incidentDetails.getLatitude());
        incident.setLongitude(incidentDetails.getLongitude());
        incident.setStatus(incidentDetails.getStatus());
        incident.setSeverity(incidentDetails.getSeverity());
        incident.setImageUrl(incidentDetails.getImageUrl());
        incident.setUser(user.get());

        return incidentRepository.save(incident);
    }

    public boolean deleteIncident(Long id) throws Exception {
        if (incidentRepository.existsById(id)) {
            incidentCategoryRepository.deleteByIncidentId(id);

            notificationRepository.deleteByIncidentId(id);

            incidentRepository.deleteById(id);
            return true;
        }
        throw new Exception("Incident not found with id " + id);
    }

    public List<Incident> getIncidentsByStatus(String status) {
        return incidentRepository.findByStatus(status);
    }
}
