package com.hope_health.patient_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "patient")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PatientEntity {

    @Id
    @Column(name = "patient_id")
    private String patientId;
    private String name;
    private String email;

    @Column(name = "user_id")
    private String userId; // for user service

    private String address;
    private String age;
    private String gender;
    private String phone;
    @Column(name = "created_date")
    private LocalDate createdDate;
}
