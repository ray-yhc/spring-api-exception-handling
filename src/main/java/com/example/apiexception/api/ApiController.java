package com.example.apiexception.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping
    public ResponseEntity<String> callNumber(
            @RequestParam(required = true) Integer number
    ) {
        return ResponseEntity.ok().body("Your number is " + number);
    }

    @GetMapping("/{pathNumber}")
    public ResponseEntity<String> callPathNumber(
            @PathVariable Integer pathNumber
    ) {
        return ResponseEntity.ok().body("Your path number is " + pathNumber);
    }

    @GetMapping("/exception/illigalArgumentException")
    public void illigalArgumentException() {
        throw new IllegalArgumentException("IllegalArgumentException occurred");
    }

    @GetMapping("/exception/runTimeException")
    public void runTimeException() {
        throw new RuntimeException("RuntimeException occurred");
    }
}
