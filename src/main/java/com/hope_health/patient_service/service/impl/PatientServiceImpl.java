package com.hope_health.patient_service.service.impl;

import com.hope_health.patient_service.entity.PatientEntity;
import com.hope_health.patient_service.repo.PatientRepo;
import com.hope_health.patient_service.request.PatientRegisterRequest;
import com.hope_health.patient_service.response.PatientRegisterResponse;
import com.hope_health.patient_service.response.PatientResponse;
import com.hope_health.patient_service.response.PatientResponsePaginated;
import com.hope_health.patient_service.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepo patientRepo;

    @Override
    public PatientRegisterResponse createPatient(PatientRegisterRequest request) {
        if(patientRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Patient with this email already exists");
        }
        PatientEntity patient = toEntity(request);

        PatientEntity savedPatient = patientRepo.save(patient);
        return toResponseRegister(savedPatient);
    }

    @Override
    public PatientResponse getPatientById(String patientId) {
        PatientEntity entity = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        return PatientResponse.builder()
                .patientId(entity.getPatientId())
                .name(entity.getName())
                .email(entity.getEmail())
                .userId(entity.getUserId())
                .build();
    }

    @Override
    public PatientResponse updatePatient(PatientRegisterRequest request, String patientId) {
        PatientEntity existingPatient = patientRepo.findById(patientId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + patientId));

        existingPatient.setPatientId(patientId);
        existingPatient.setName(request.getName());
        existingPatient.setEmail(request.getEmail());
        existingPatient.setUserId(request.getUserId());

        patientRepo.save(existingPatient);

        return new PatientResponse(
                existingPatient.getPatientId(),
                existingPatient.getName(),
                existingPatient.getEmail(),
                existingPatient.getUserId()
        );
    }

    @Override
    public void deletePatientById(String patientId) {
        if (!patientRepo.existsById(patientId)) {
            throw new RuntimeException("Patient not found with id: " + patientId);
        }
        patientRepo.deleteById(patientId);
    }

    @Override
    public PatientResponsePaginated findAllPatients(String searchText, int page, int size) {
        return PatientResponsePaginated.builder()
                .patientCount(patientRepo.countAll(searchText))
                .patientList(patientRepo.searchAll(searchText, PageRequest.of(page, size)).stream()
                        .map(this::toResponse).toList())
                .build();
    }

    private PatientResponse toResponse(PatientEntity patientEntity) {
        return PatientResponse.builder()
                .patientId(patientEntity.getPatientId())
                .name(patientEntity.getName())
                .email(patientEntity.getEmail())
                .userId(patientEntity.getUserId())
                .build();
    }


    private PatientRegisterResponse toResponseRegister(PatientEntity entity) {
        return PatientRegisterResponse.builder()
                .patientId(entity.getPatientId())
                .name(entity.getName())
                .email(entity.getEmail())
                .build();
    }

    private PatientEntity toEntity(PatientRegisterRequest request) {
       return PatientEntity.builder()
                .patientId(UUID.randomUUID().toString())
                .name(request.getName())
                .email(request.getEmail())
                .userId(request.getUserId())
                .build();
    }
}
