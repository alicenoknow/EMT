package com.agh.emt.controller.news;

import com.agh.emt.model.news.News;
import com.agh.emt.service.news.NewsNotFoundException;
import com.agh.emt.service.news.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/news")
@AllArgsConstructor
public class NewsController {
    private NewsService newsService;

    @GetMapping
    public ResponseEntity<List<News>> findAll() {
        return ResponseEntity.ok(newsService.findAll());
    }

    @PostMapping
    public ResponseEntity<News> addNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.addNews(news));
    }

    @GetMapping("/{id}")
    public ResponseEntity<News> findNews(@PathVariable Long id) throws NewsNotFoundException {
        return ResponseEntity.ok(newsService.findNews(id));
    }

    @PutMapping
    public ResponseEntity<News> editNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.editNews(news));
    }

    @DeleteMapping("/{id}")
    public void deleteNews(@PathVariable Long id) {
        newsService.deleteNews(id);
    }
}
