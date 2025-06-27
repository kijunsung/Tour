package com.example.tour_backend.dto.notification;

import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationDto extends RepresentationModel<NotificationDto> {
    private Long noticeId;   // 생성 시에는 null
    private Long userId;
    private Long threadId;
    private Long commentId;
    private String message;
    private boolean isRead;
}
