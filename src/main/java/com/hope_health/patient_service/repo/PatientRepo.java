package com.hope_health.patient_service.repo;

import com.hope_health.patient_service.entity.PatientEntity;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface PatientRepo extends JpaRepository<PatientEntity, String> {
    Optional<PatientEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    Optional<PatientEntity> findByUserId(String userId);
    boolean existsByUserId(String userId);

    @Query(value = "SELECT COUNT(patient_id) FROM patients WHERE name LIKE %?1% OR email LIKE %?1%", nativeQuery = true)
    long countAll(String searchText);

    @Query(value = "SELECT * FROM patients WHERE name LIKE %?1% OR email LIKE %?1%", nativeQuery = true)
    List<PatientEntity> searchAll(String searchText, Pageable pageable);
}
