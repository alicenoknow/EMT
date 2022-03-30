package com.agh.emt.model.news;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("news")
@Data
public class News {
    @Id
    private Long id;
//    private LocalDateTime timeAdded;
    private String message;
}
