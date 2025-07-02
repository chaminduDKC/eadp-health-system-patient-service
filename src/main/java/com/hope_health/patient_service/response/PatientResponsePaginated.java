package com.hope_health.patient_service.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PatientResponsePaginated {
    private long patientCount;
    private List<PatientResponse> patientList;
 }
