package com.example.tour_backend.controller;

import com.example.tour_backend.dto.thread.ThreadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.tour_backend.service.ThreadService;

import java.util.List;

@RestController
@RequestMapping("/api/thread")
@RequiredArgsConstructor
public class ThreadController {
    private final ThreadService threadService;

    @PostMapping
    public ResponseEntity<ThreadDto> createThread(@RequestBody ThreadDto dto) {
        ThreadDto created = threadService.createThread(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThreadDto> getThread(@PathVariable Long id) {
        return threadService.getThread(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<ThreadDto>> getAllThreads() {
        List<ThreadDto> threads = threadService.getAllThreads();
        return ResponseEntity.ok(threads);

    }

}