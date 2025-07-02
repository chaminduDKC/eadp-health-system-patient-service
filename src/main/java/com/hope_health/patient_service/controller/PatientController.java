package com.hope_health.patient_service.controller;

import com.hope_health.patient_service.request.PatientRegisterRequest;
import com.hope_health.patient_service.service.PatientService;
import com.hope_health.patient_service.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/patients")
@RestController
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping("/test")
    public ResponseEntity<StandardResponse> test(){
        return new ResponseEntity<>(
                StandardResponse.builder()
                        .code(200)
                        .message("Test controller working")
                        .data(null)
                        .build(),
                HttpStatus.OK
        );
    }

    @PostMapping("/register-patient")
    public ResponseEntity<StandardResponse> registerPatient(@RequestBody PatientRegisterRequest request) {
        System.out.println("Called from user service with " + request);
        return new ResponseEntity<>(
                StandardResponse.builder()
                        .code(201)
                        .message("Patient registration endpoint")
                        .data(patientService.createPatient(request))
                        .build(),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/find-patient/{patientId}")
    @PreAuthorize("hasRole('admin') or hasRole('doctor')")
    public ResponseEntity<StandardResponse> getPatientById(@PathVariable String patientId) {
        return new ResponseEntity<>(
                StandardResponse.builder()
                        .code(200)
                        .message("Patient retrieved successfully")
                        .data(patientService.getPatientById(patientId))
                        .build(),
                HttpStatus.OK
        );
    }

    @PutMapping("/update-patient/{patientId}")
    public ResponseEntity<StandardResponse> updatePatient(@RequestBody PatientRegisterRequest request, @PathVariable String patientId) {
        return new ResponseEntity<>(
                StandardResponse.builder()
                        .code(200)
                        .message("Patient updated successfully")
                        .data(patientService.updatePatient(request, patientId))
                        .build(),
                HttpStatus.OK
        );
    }

    @DeleteMapping("/delete-patient/{patientId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<StandardResponse> deletePatientById(@PathVariable String patientId) {
        patientService.deletePatientById(patientId);
        return new ResponseEntity<>(
                StandardResponse.builder()
                        .code(200)
                        .message("Patient deleted successfully with id: " + patientId)
                        .data(null)
                        .build(),
                HttpStatus.OK
        );
    }

    @GetMapping("/find-all-patients")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<StandardResponse> findAllPatients(@RequestParam String searchText, @RequestParam int page, @RequestParam int size) {
        return new ResponseEntity<>(
                StandardResponse.builder()
                        .code(200)
                        .message("All patients retrieved")
                        .data(patientService.findAllPatients(searchText, page, size))
                        .build(),
                HttpStatus.OK
        );
    }

}
