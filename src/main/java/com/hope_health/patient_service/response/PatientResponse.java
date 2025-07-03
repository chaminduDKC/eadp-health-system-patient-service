package com.hope_health.patient_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponse {
    private String patientId;
    private String userId;
    private String name;
    private String email;
    private String address;
    private String age;
    private String gender;
    private String phone;
    // complete response including address, phone number, etc. can be added later
}
