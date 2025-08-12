package com.hope_health.patient_service.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "news")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class NewsEntity {

    @Id
    @Column(name = "news_id")
    private String newsId;

    private String imageUrl;

    @Column(length = 2000)
    private String title;

    @Column(length = 2000)
    private String content;

}
