package com.agh.emt.service.parameters;

import com.agh.emt.model.parameters.Parameter;
import com.agh.emt.model.parameters.ParameterRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ParameterService {
    ParameterRepository parameterRepository;

    public Parameter findParameter(String id) throws ParameterNotFoundException {
        return parameterRepository.findById(id).orElseThrow(() -> new ParameterNotFoundException("Nie znaleziono parametru: " + id));
    }

    public Parameter setParameter(Parameter parameter) {
        return parameterRepository.save(parameter);
    }
}
