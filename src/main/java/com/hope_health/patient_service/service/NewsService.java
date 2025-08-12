package com.hope_health.patient_service.service;

import com.hope_health.patient_service.request.NewsRequestDto;
import com.hope_health.patient_service.response.NewsResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NewsService {

    boolean createNews(NewsRequestDto requestDto);

    boolean deleteNews(String newsId);

    boolean updateNews(NewsRequestDto requestDto, String newsId);

    List<NewsResponse> allNews();
}
