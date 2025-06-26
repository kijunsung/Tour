package com.example.tour_backend.controller;

import com.example.tour_backend.domain.thread.Thread;
import com.example.tour_backend.dto.thread.ThreadDto;
import com.example.tour_backend.dto.thread.ThreadUpdateRequestDto;
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

    @PostMapping //게시글 생성
    public ResponseEntity<ThreadDto> createThread(@RequestBody ThreadDto dto) {
        ThreadDto created = threadService.createThread(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}") //특정 게시글 조회
    public ResponseEntity<ThreadDto> getThread(@PathVariable Long id) {
        return threadService.getThread(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping //모든 게시글 목록 조회
    public ResponseEntity<List<ThreadDto>> getAllThreads() {
        List<ThreadDto> threads = threadService.getAllThreads();
        return ResponseEntity.ok(threads);

    }
    @DeleteMapping("/{id}") // 게시글 삭제 (추추추가)
    public ResponseEntity<Void> deleteThread(@PathVariable Long id) {
        threadService.deleteThread(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}") // 게시물 수정(PUT /api/thread/{id} 엔드포인트 추가) 추추가
    public ResponseEntity<ThreadDto> updateThread(
            @PathVariable Long id,
            @RequestBody ThreadUpdateRequestDto requestDto) {
        // 수정된 게시글 받아오기
        Thread updated = threadService.updateThread(id, requestDto);

        // 응답은 ThreadDto로 변환해서 줘도 되고, entity 자체를 줘도 됨
        ThreadDto responseDto = new ThreadDto();
        responseDto.setThreadId(updated.getThreadId());
        responseDto.setUserId(updated.getUser().getUserId());
        responseDto.setTitle(updated.getTitle());
        responseDto.setContent(updated.getContent());
        responseDto.setAuthor(updated.getAuthor());
        responseDto.setPdfPath(updated.getPdfPath());
        responseDto.setArea(updated.getArea());
        responseDto.setCreateDate(updated.getCreateDate());
        responseDto.setModifiedDate(updated.getModifiedDate());
        responseDto.setCount(updated.getCount());
        responseDto.setHeart(updated.getHeart());
        responseDto.setCommentCount(updated.getCommentCount());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search") // 게시글 검색 기능 추추추추가
    public ResponseEntity<List<ThreadDto>> searchThreads(@RequestParam String keyword) {
        List<ThreadDto> results = threadService.searchThreads(keyword);
        return ResponseEntity.ok(results);
    }


}