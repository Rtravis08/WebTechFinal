package com.auca.crimereport.service;

import com.auca.crimereport.controller.NotificationRequest;
import com.auca.crimereport.model.Incident;
import com.auca.crimereport.model.Notification;
import com.auca.crimereport.model.User;
import com.auca.crimereport.repository.IncidentRepository;
import com.auca.crimereport.repository.NotificationRepository;
import com.auca.crimereport.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private IncidentRepository incidentRepository;

    @Autowired
    private UserRepository adminRepository;

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public Notification createNotification(NotificationRequest request) throws Exception {
        Incident incident = incidentRepository.findById(request.getIncidentId())
                .orElseThrow(() -> new Exception("Incident not found with id " + request.getIncidentId()));
        User admin = adminRepository.findById(request.getAdminId())
                .orElseThrow(() -> new Exception("Admin not found with id " + request.getAdminId()));

        Notification notification = new Notification();
        notification.setIncident(incident);
        notification.setAdmin(admin);
        notification.setEmail(request.getEmail());
        notification.setSentAt(LocalDateTime.now());
        notification.setStatus(request.getStatus());

        return notificationRepository.save(notification);
    }

    public Notification updateNotification(Long id, String status) throws Exception {
        Optional<Notification> optionalNotification = notificationRepository.findById(id);
        if (optionalNotification.isPresent()) {
            Notification notification = optionalNotification.get();
            notification.setStatus(status);
            return notificationRepository.save(notification);
        }
        throw new Exception("Notification not found with id " + id);
    }

    public boolean deleteNotification(Long id) throws Exception {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        throw new Exception("Notification not found with id " + id);
    }
}
