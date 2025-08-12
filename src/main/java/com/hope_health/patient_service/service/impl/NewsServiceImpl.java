package com.hope_health.patient_service.service.impl;

import com.hope_health.patient_service.entity.NewsEntity;
import com.hope_health.patient_service.repo.NewsRepo;
import com.hope_health.patient_service.request.NewsRequestDto;
import com.hope_health.patient_service.response.NewsResponse;
import com.hope_health.patient_service.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepo newsRepo;
    @Override
    public boolean createNews(NewsRequestDto requestDto) {
        if(requestDto == null){
            throw new IllegalArgumentException("requestDto is null");
        }
        NewsEntity entity = NewsEntity.builder()
                .newsId(UUID.randomUUID().toString())
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .imageUrl(requestDto.getImageUrl())
                .build();
        newsRepo.save(entity);
        return true;
    }

    @Override
    public boolean deleteNews(String newsId) {
        Optional<NewsEntity> newsEntity = newsRepo.findById(newsId);
        if(newsEntity.isPresent()){
            newsRepo.delete(newsEntity.get());
            return true;
        } else {
            throw new IllegalArgumentException("newsId is not exist");
        }
    }

    @Override
    public boolean updateNews(NewsRequestDto requestDto, String newsId) {
        Optional<NewsEntity> newsEntity = newsRepo.findById(newsId);
        if(newsEntity.isPresent()){
            NewsEntity entity = newsEntity.get();
            entity.setTitle(requestDto.getTitle());
            entity.setContent(requestDto.getContent());
            entity.setImageUrl(requestDto.getImageUrl());
            newsRepo.save(entity);
            return true;
        } else {
            throw new IllegalArgumentException("newsId is not exist");
        }
    }

    @Override
    public List<NewsResponse> allNews() {
        List<NewsEntity> newsEntities = newsRepo.findAll();
        return newsEntities.stream().map(this::toResponse).toList();
    }

    private NewsResponse toResponse(NewsEntity entity){
        return NewsResponse.builder()
                .newsId(entity.getNewsId())
                .content(entity.getContent())
                .imageUrl(entity.getImageUrl())
                .title(entity.getTitle())
                .build();
    }
}
