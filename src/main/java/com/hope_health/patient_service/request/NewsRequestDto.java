package com.hope_health.patient_service.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsRequestDto {

    private String title;
    private String content;
    private String imageUrl;

}
