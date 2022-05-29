package com.agh.emt.controller.parameter;

import com.agh.emt.model.parameters.Parameter;
import com.agh.emt.service.parameters.ParameterNotFoundException;
import com.agh.emt.service.parameters.ParameterService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/parameter")
@CrossOrigin("http://localhost:3000/")
@AllArgsConstructor
public class ParameterController {
    private final ParameterService parameterService;

    @GetMapping("/{id}")
    ResponseEntity<Parameter> findById(@PathVariable String id) throws ParameterNotFoundException {
        return ResponseEntity.ok(parameterService.findParameter(id));
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    @PreAuthorize("hasAnyRole('FACULTY_COORDINATOR', 'CONTRACT_COORDINATOR', 'DEAN_OFFICE_WORKER', 'FOREIGN_COUNTRIES_DEPARTMENT_REP', 'OTHER_ADMIN')")
    ResponseEntity<Parameter> setParameter(@Valid @RequestBody Parameter parameter) {
        return ResponseEntity.ok(parameterService.setParameter(parameter));
    }
}
