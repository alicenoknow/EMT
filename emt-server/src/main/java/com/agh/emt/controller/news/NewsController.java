package com.agh.emt.controller.news;

import com.agh.emt.model.news.News;
import com.agh.emt.service.news.NewsNotFoundException;
import com.agh.emt.service.news.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/news")
@AllArgsConstructor
public class NewsController {
    private NewsService newsService;

    public ResponseEntity<List<News>> findAll() {
        return ResponseEntity.ok(newsService.findAll());
    }

    public ResponseEntity<News> addNews(News news) {
        return ResponseEntity.ok(newsService.addNews(news));
    }

//    public News findNews(Long id) throws NewsNotFoundException {
//        return newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("Nie znaleziono wiadomości o id: " + id));
//    }
//
//    public News editNews(News news) {
//        return newsRepository.save(news);
//    }
//
//    public void deleteNews(Long id) {
//        newsRepository.deleteById(id);
//    }
}
