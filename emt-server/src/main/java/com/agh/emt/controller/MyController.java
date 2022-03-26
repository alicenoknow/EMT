package com.agh.emt.controller;

import com.agh.emt.service.MyService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/api")
public class MyController {
    MyService myService;

    @GetMapping
    public ResponseEntity<String> myControllerMethod() {
        return ResponseEntity.ok(myService.myServiceMethod());
    }
}
