package com.agh.emt.model.news;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NewsRepository extends MongoRepository<News, String> {

}
