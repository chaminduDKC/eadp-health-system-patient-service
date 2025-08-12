package com.hope_health.patient_service.controller;

import com.hope_health.patient_service.request.NewsRequestDto;
import com.hope_health.patient_service.service.NewsService;
import com.hope_health.patient_service.util.StandardResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/news/")
@RestController
@RequiredArgsConstructor
public class NewsController {

    private final NewsService newsService;

    @PostMapping("/create-news")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<StandardResponse> createNews(@RequestBody NewsRequestDto requestDto){
        return new ResponseEntity<>(StandardResponse.builder()
                .code(201)
                .message("News created")
                .data(newsService.createNews(requestDto))
                .build(),
                HttpStatus.OK);
    }

    @DeleteMapping("/delete-news/{newsId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<StandardResponse> deleteNews(@PathVariable String newsId){
        return new ResponseEntity<>(StandardResponse.builder()
                .code(200)
                .message("News deleted")
                .data(newsService.deleteNews(newsId))
                .build(),
                HttpStatus.OK);
    }

    @PutMapping("/update-news/{newsId}")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<StandardResponse> updateNews(@RequestBody NewsRequestDto requestDto, @PathVariable String newsId){
        return new ResponseEntity<>(StandardResponse.builder()
                .code(200)
                .message("News updated")
                .data(newsService.updateNews(requestDto, newsId))
                .build(),
                HttpStatus.OK);
    }

    @GetMapping("/find-all news")
    @PreAuthorize("hasRole('admin')")
    public ResponseEntity<StandardResponse> allNews(){
        return new ResponseEntity<>(StandardResponse.builder()
                .code(200)
                .message("All news")
                .data(newsService.allNews())
                .build(),
                HttpStatus.OK);
    }
}
