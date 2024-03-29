package com.agh.emt.controller.news;

import com.agh.emt.model.news.News;
import com.agh.emt.service.news.NewsNotFoundException;
import com.agh.emt.service.news.NewsService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/news")
@AllArgsConstructor
public class NewsController {
    private NewsService newsService;

    @GetMapping
//    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<News>> findAll() {
        return ResponseEntity.ok(newsService.findAll());
    }

    @PostMapping
    public ResponseEntity<News> addNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.addNews(news));
    }

    @GetMapping("/{id}")
//    @PreAuthorize("hasRole('CONTRACT_COORDINATOR')")
    public ResponseEntity<News> findNews(@PathVariable String id) throws NewsNotFoundException {
        return ResponseEntity.ok(newsService.findNews(id));
    }

    @PutMapping
    public ResponseEntity<News> editNews(@RequestBody News news) {
        return ResponseEntity.ok(newsService.editNews(news));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('FACULTY_COORDINATOR')")
    public void deleteNews(@PathVariable String id) {
        newsService.deleteNews(id);
    }
}
