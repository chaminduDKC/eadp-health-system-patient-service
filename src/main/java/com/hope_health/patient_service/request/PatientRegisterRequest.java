package com.hope_health.patient_service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientRegisterRequest {
    private String name;
    private String email;
    private String userId;
    private String address;
    private String age;
    private String gender;
    private String phone;
}
