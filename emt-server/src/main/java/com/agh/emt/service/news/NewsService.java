package com.agh.emt.service.news;

import com.agh.emt.model.news.News;
import com.agh.emt.model.news.NewsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NewsService {
    private final NewsRepository newsRepository;

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public News addNews(News news) {
        return newsRepository.insert(news);
    }

    public News findNews(Long id) throws NewsNotFoundException {
        return newsRepository.findById(id).orElseThrow(() -> new NewsNotFoundException("Nie znaleziono wiadomości o id: " + id));
    }

    public News editNews(News news) {
        return newsRepository.save(news);
    }

    public void deleteNews(Long id) {
        newsRepository.deleteById(id);
    }
}
