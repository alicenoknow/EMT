package com.agh.emt.model.news;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("news")
@Data
public class News {
    @Transient
    public static final String SEQUENCE_NAME = "news_sequence";

    @Id
    private Long id;
    private LocalDateTime timeAdded;
    private String title;
    private String message;
}
