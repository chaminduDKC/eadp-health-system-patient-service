package com.hope_health.patient_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRegisterResponse {
    private String patientId;
    private String name;
    private String email;

}
