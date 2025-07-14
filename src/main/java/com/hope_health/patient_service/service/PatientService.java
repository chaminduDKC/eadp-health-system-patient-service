package com.hope_health.patient_service.service;

import com.hope_health.patient_service.request.PatientRegisterRequest;
import com.hope_health.patient_service.response.PatientRegisterResponse;
import com.hope_health.patient_service.response.PatientResponse;
import com.hope_health.patient_service.response.PatientResponsePaginated;

import java.util.List;


public interface PatientService {
    PatientRegisterResponse createPatient(PatientRegisterRequest request);

    PatientResponse getPatientById(String patientId);

    PatientResponse updatePatient(PatientRegisterRequest request, String patientId);

    void deletePatientByUserId(String userId);

    PatientResponsePaginated findAllPatients(String searchText, int page, int size);

    long countAllPatients();
}
