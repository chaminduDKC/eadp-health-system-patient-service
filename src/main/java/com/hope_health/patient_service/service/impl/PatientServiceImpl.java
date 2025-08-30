package com.hope_health.patient_service.service.impl;

import com.hope_health.patient_service.config.WebClientConfig;
import com.hope_health.patient_service.entity.PatientEntity;
import com.hope_health.patient_service.repo.PatientRepo;
import com.hope_health.patient_service.request.PatientRegisterRequest;
import com.hope_health.patient_service.request.PatientStatisticResponse;
import com.hope_health.patient_service.response.PatientRegisterResponse;
import com.hope_health.patient_service.response.PatientResponse;
import com.hope_health.patient_service.response.PatientResponsePaginated;
import com.hope_health.patient_service.service.PatientService;
import com.hope_health.patient_service.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientException;


import java.time.LocalDate;
import java.time.YearMonth;
import java.util.*;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepo patientRepo;
    private final WebClientConfig webClientConfig;

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
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        return PatientResponse.builder()
                .patientId(entity.getPatientId())
                .name(entity.getName())
                .email(entity.getEmail())
                .userId(entity.getUserId())
                .address(entity.getAddress())
                .age(entity.getAge())
                .phone(entity.getPhone())
                .gender(entity.getGender())
                .build();
    }

    @Override
    public PatientResponse updatePatient(PatientRegisterRequest request, String userId) {
        PatientEntity existingPatient = patientRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found with id: " + userId));

        existingPatient.setName(request.getName());
        existingPatient.setAddress(request.getAddress());
        existingPatient.setAge(request.getAge());
        existingPatient.setGender(request.getGender());
        existingPatient.setPhone(request.getPhone());

        patientRepo.save(existingPatient);

        return new PatientResponse(
                existingPatient.getPatientId(),
                existingPatient.getName(),
                existingPatient.getEmail(),
                existingPatient.getUserId(),
                existingPatient.getAddress(),
                existingPatient.getAge(),
                existingPatient.getGender(),
                existingPatient.getPhone()
        );
    }

    @Override
    public void deletePatientByUserId(String userId) {
        if (!patientRepo.existsByUserId(userId)) {
            throw new RuntimeException("Patient not found");
        }
        PatientEntity patient = patientRepo.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Patient not found "));

        String patientId = patient.getPatientId();
        patientRepo.deleteById(patientId);
        try{
            webClientConfig.webClient().delete()
                    .uri("http://localhost:9093/api/bookings/delete-booking-by-patient/{patientId}", patientId)
                    .retrieve().bodyToMono(StandardResponse.class).block();
        } catch (WebClientException e) {
            throw new RuntimeException("Failed to delete from booking service "+e);
        }
    }

    @Override
    public PatientResponsePaginated findAllPatients(String searchText, int page, int size) {
        return PatientResponsePaginated.builder()
                .patientCount(patientRepo.countAll(searchText))
                .patientList(patientRepo.searchAll(searchText, PageRequest.of(page, size)).stream()
                        .map(this::toResponse).toList())
                .build();
    }

    @Override
    public long countAllPatients() {
        return patientRepo.countAll("");
    }

    @Override
    public Boolean updateEmail(String userId, String email) {
        Optional<PatientEntity> patientEntity = patientRepo.findByUserId(userId);
        if(patientEntity.isPresent()) {
            patientEntity.get().setEmail(email);
            patientRepo.save(patientEntity.get());
            return true;
        }
        return false;
    }

    @Override
    public PatientResponse getPatientByEmail(String email) {
    Optional<PatientEntity> patientEntity = patientRepo.findByEmail(email);
    if(patientEntity.isPresent()){
        PatientEntity entity = patientEntity.get();
        return PatientResponse.builder()
                .age(entity.getAge())
                .gender(entity.getGender())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .name(entity.getName())
                .email(entity.getEmail())
                .patientId(entity.getPatientId())
                .userId(entity.getUserId())
                .build();
    }
        throw new RuntimeException("Patient not found with email: " + email);
    }

    @Override
    public List<PatientStatisticResponse> getPatientsByMonth(int numberOfMonths) {
        List<PatientStatisticResponse> stats = new ArrayList<>();
        int monthNumber = 0;
       for (int i = numberOfMonths; i > 0; i--) {
           YearMonth month = YearMonth.now().minusMonths(monthNumber);
           String monthString = YearMonth.now().minusMonths(monthNumber).getMonth().toString();
           int patientCount = patientRepo.findByMonth(month);
           System.out.println(monthString);
           System.out.println(month);
           stats.add(PatientStatisticResponse.builder().patients(patientCount).month(monthString).build());
           monthNumber++;
       }
       Collections.reverse(stats);
       return stats;
    }

    @Override
    public List<Integer> getPatientsByDate() {
        List<Integer> statList = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            LocalDate date = LocalDate.now().minusDays(i);
            int patientCount = patientRepo.findByDate(date);
            statList.add(patientCount);
        }
        Collections.reverse(statList);
        return statList;
    }


    private PatientResponse toResponse(PatientEntity patientEntity) {
        return PatientResponse.builder()
                .patientId(patientEntity.getPatientId())
                .name(patientEntity.getName())
                .email(patientEntity.getEmail())
                .address(patientEntity.getAddress())
                .age(patientEntity.getAge())
                .phone(patientEntity.getPhone())
                .gender(patientEntity.getGender())
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
                .age(request.getAge())
                .address(request.getAddress())
                .phone(request.getPhone())
                .gender(request.getGender())
                .name(request.getName())
                .email(request.getEmail())
                .userId(request.getUserId())
                .createdDate(request.getCreatedDate())
                .build();
    }
}
