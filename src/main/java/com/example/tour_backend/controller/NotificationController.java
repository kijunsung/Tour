package com.example.tour_backend.controller;

import com.example.tour_backend.domain.notification.Notification;
import com.example.tour_backend.dto.notification.NotificationDto;
import com.example.tour_backend.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto dto) {
        NotificationDto created = notificationService.createNotification(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NotificationDto>> getNotifications(@PathVariable Long userId) {
        List<NotificationDto> dtos = notificationService.getUserNotifications(userId).stream()
                .map(n -> {
                    // 엔티티 → DTO 변환
                    NotificationDto dto = NotificationDto.builder()
                            .noticeId(n.getNoticeId())
                            .userId(  n.getUser().getUserId())
                            .threadId(n.getThread().getThreadId())
                            .commentId(n.getComment().getCommentId())
                            .message(n.getMessage())
                            .isRead(n.isRead())
                            .build();

                    // 각 DTO에 thread 링크만 추가
                    dto.add(linkTo(methodOn(ThreadController.class)
                            .getThread(dto.getThreadId()))
                            .withRel("thread"));

                    return dto;
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(dtos);
    }
}
