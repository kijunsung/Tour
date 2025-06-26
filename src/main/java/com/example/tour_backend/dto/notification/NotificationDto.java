package com.example.tour_backend.dto.notification;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto {
    private Long noticeId;   // 생성 시에는 null
    private Long userId;
    private Long threadId;
    private Long commentId;
    private String message;
    private boolean isRead;
}
