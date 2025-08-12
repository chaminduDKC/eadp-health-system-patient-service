package com.hope_health.patient_service.repo;

import com.hope_health.patient_service.entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepo extends JpaRepository<NewsEntity, String> {
}
