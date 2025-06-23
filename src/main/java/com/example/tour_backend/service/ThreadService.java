package com.example.tour_backend.service;

import com.example.tour_backend.domain.thread.Thread;
import com.example.tour_backend.domain.thread.ThreadRepository;
import com.example.tour_backend.domain.user.User;
import com.example.tour_backend.domain.user.UserRepository;
import com.example.tour_backend.dto.thread.ThreadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThreadService {
    private final ThreadRepository threadRepository;
    private final UserRepository userRepository;

    @Transactional
    public ThreadDto createThread(ThreadDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 사용자입니다."));

        Thread thread = Thread.builder()
                .user(user)
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(dto.getAuthor())
                .pdfPath(dto.getPdfPath())
                .area(dto.getArea())
                .build();

        threadRepository.save(thread);

        dto.setThreadId(thread.getThreadId());
        dto.setCount(thread.getCount());
        dto.setHeart(thread.getHeart());
        dto.setCommentCount(thread.getCommentCount());
        dto.setCreateDate(thread.getCreateDate());
        dto.setModifiedDate(thread.getModifiedDate());

        return dto;
    }

    public Optional<ThreadDto> getThread(Long threadid) {
        return threadRepository.findById(threadid)
                .map(thread -> {
                    ThreadDto dto = new ThreadDto();
                    dto.setThreadId(thread.getThreadId());
                    dto.setUserId(thread.getUser().getUserId());
                    dto.setTitle(thread.getTitle());
                    dto.setContent(thread.getContent());
                    dto.setAuthor(thread.getAuthor());
                    dto.setCount(thread.getCount());
                    dto.setHeart(thread.getHeart());
                    dto.setPdfPath(thread.getPdfPath());
                    dto.setCommentCount(thread.getCommentCount());
                    dto.setArea(thread.getArea());
                    dto.setCreateDate(thread.getCreateDate());
                    dto.setModifiedDate(thread.getModifiedDate());
                    return dto;
                });
    }

    public List<ThreadDto> getAllThreads() {
        return threadRepository.findAll().stream()
                .map(thread -> {
                    ThreadDto dto = new ThreadDto();
                    dto.setThreadId(thread.getThreadId());
                    dto.setUserId(thread.getUser().getUserId());
                    dto.setTitle(thread.getTitle());
                    dto.setContent(thread.getContent());
                    dto.setAuthor(thread.getAuthor());
                    dto.setCount(thread.getCount());
                    dto.setHeart(thread.getHeart());
                    dto.setPdfPath(thread.getPdfPath());
                    dto.setCommentCount(thread.getCommentCount());
                    dto.setArea(thread.getArea());
                    dto.setCreateDate(thread.getCreateDate());
                    dto.setModifiedDate(thread.getModifiedDate());
                    return dto;
                })
                .collect(Collectors.toList());
    }
}
