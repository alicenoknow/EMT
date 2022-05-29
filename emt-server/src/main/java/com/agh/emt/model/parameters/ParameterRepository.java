package com.agh.emt.model.parameters;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParameterRepository extends MongoRepository<Parameter, String> {
}
