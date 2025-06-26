package com.example.tour_backend.service;

import com.example.tour_backend.domain.comment.Comment;
import com.example.tour_backend.domain.comment.CommentRepository;
import com.example.tour_backend.domain.thread.Thread;
import com.example.tour_backend.domain.thread.ThreadRepository;
import com.example.tour_backend.dto.comment.CommentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final ThreadRepository threadRepository;

    @Transactional
    public CommentDto createComment(CommentDto dto) {
        // 1) 요청 DTO에 담긴 threadId로 Thread 엔티티 조회
        Thread thread = threadRepository.findById(dto.getThreadId())
                .orElseThrow(() -> new RuntimeException("게시물이 존재하지 않습니다."));

        Comment comment = Comment.builder()
                .thread(thread)
                .comment(dto.getComment())
                .author(dto.getAuthor())
                .build();

        Comment saved = commentRepository.save(comment);

        return toDto(saved);
    }

    /**
     * 전체 댓글 조회
     */
    @Transactional(readOnly = true)
    public List<CommentDto> getAllComments() {
        return commentRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * 단일 댓글 조회
     */
    @Transactional(readOnly = true)
    public Optional<CommentDto> getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .map(this::toDto);
    }

    /**
     * Comment 엔티티를 CommentDto로 변환
     */
    private CommentDto toDto(Comment c) {
        return CommentDto.builder()
                .commentId(c.getCommentId())
                .threadId(c.getThread().getThreadId())
                .comment(c.getComment())
                .author(c.getAuthor())
                .createDate(c.getCreateDate())
                .modifiedDate(c.getModifiedDate())
                .build();
    }
}
