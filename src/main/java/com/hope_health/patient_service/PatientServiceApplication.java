package com.hope_health.patient_service;

import com.hope_health.patient_service.repo.PatientRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.YearMonth;

@SpringBootApplication
public class PatientServiceApplication {

	public static void main(String[] args) {
		YearMonth month = YearMonth.now();
		System.out.println(month);
		SpringApplication.run(PatientServiceApplication.class, args);
	}

}
