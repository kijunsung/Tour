package com.example.tour_backend.service;

import com.example.tour_backend.domain.comment.Comment;
import com.example.tour_backend.domain.comment.CommentRepository;
import com.example.tour_backend.domain.notification.Notification;
import com.example.tour_backend.domain.notification.NotificationRepository;
import com.example.tour_backend.domain.thread.Thread;
import com.example.tour_backend.domain.thread.ThreadRepository;
import com.example.tour_backend.domain.user.User;
import com.example.tour_backend.domain.user.UserRepository;
import com.example.tour_backend.dto.notification.NotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final UserRepository         userRepository;
    private final ThreadRepository       threadRepository;
    private final CommentRepository      commentRepository;

    /**
     * 알림 생성
     */
    @Transactional
    public NotificationDto createNotification(NotificationDto dto) {
        // 1) 연관 엔티티 조회 및 예외 처리
        User    user    = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("해당 유저가 없습니다."));
        Thread  thread  = threadRepository.findById(dto.getThreadId())
                .orElseThrow(() -> new RuntimeException("해당 게시글이 없습니다."));
        Comment comment = commentRepository.findById(dto.getCommentId())
                .orElseThrow(() -> new RuntimeException("해당 댓글이 없습니다."));

        // 2) Notification 엔티티 생성 및 저장
        Notification saved = notificationRepository.save(
                Notification.builder()
                        .user(user)
                        .thread(thread)
                        .comment(comment)
                        .message(dto.getMessage())
                        .isRead(dto.isRead())
                        .createDate(LocalDateTime.now())   // Builder 에 createDate 필드가 필요하다면 명시
                        .build()
        );

        // 3) 저장된 엔티티를 DTO로 변환해 반환
        return NotificationDto.builder()
                .noticeId(   saved.getNoticeId()        )
                .userId(     saved.getUser().getUserId())
                .threadId(   saved.getThread().getThreadId())
                .commentId(  saved.getComment().getCommentId())
                .message(    saved.getMessage()         )
                .isRead(     saved.isRead()             )
                .build();
    }

    /**
     * 개별 알림 조회
     */
    @Transactional(readOnly = true)
    public Notification findById(Long noticeId) {
        return notificationRepository.findById(noticeId)
                .orElseThrow(() -> new RuntimeException("해당 알림이 없습니다."));
    }

    /**
     * 특정 유저의 알림 리스트 조회 (최신 순)
     */
    @Transactional(readOnly = true)
    public List<Notification> getUserNotifications(Long userId) {
        return notificationRepository
                .findByUserUserIdOrderByCreateDateDesc(userId);
    }
}
