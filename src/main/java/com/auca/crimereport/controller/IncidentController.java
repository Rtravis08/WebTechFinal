package com.auca.crimereport.controller;

import com.auca.crimereport.model.Incident;
import com.auca.crimereport.service.IncidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/incidents")
public class IncidentController {

    @Autowired
    private IncidentService incidentService;

    @GetMapping
    public List<Incident> getAllIncidents() {
        return incidentService.getAllIncidents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Incident> getIncidentById(@PathVariable Long id) {
        Optional<Incident> incident = incidentService.getIncidentById(id);
        if (incident.isPresent()) {
            return ResponseEntity.ok(incident.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PostMapping
    public ResponseEntity<IncidentResponse> createIncident(@RequestParam Long userId, @RequestBody Incident incident) {
        try {
            IncidentResponse response = incidentService.createIncident(userId, incident);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new IncidentResponse(e.getMessage(), null));
        }
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<Incident> updateIncident(@PathVariable Long id, @RequestBody Incident incidentDetails) {
//        try {
//            Incident updatedIncident = incidentService.updateIncident(id, incidentDetails);
//            return ResponseEntity.ok(updatedIncident);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        }
//    }
@PutMapping("/{id}")
public ResponseEntity<Incident> updateIncident(@RequestParam Long userId, @PathVariable Long id, @RequestBody Incident incidentDetails) {
    try {
        Incident updatedIncident = incidentService.updateIncident(userId, id, incidentDetails);
        return ResponseEntity.ok(updatedIncident);
    } catch (Exception e) {
        return ResponseEntity.badRequest().body(null);
    }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIncident(@PathVariable Long id) {
        try {
            if (incidentService.deleteIncident(id)) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/status/{status}")
    public List<Incident> getIncidentsByStatus(@PathVariable String status) {
        return incidentService.getIncidentsByStatus(status);
    }
}
