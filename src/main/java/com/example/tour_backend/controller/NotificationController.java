//package com.example.tour_backend.controller;
//
//import com.example.tour_backend.domain.notification.Notification;
//import com.example.tour_backend.dto.notification.NotificationDto;
//import lombok.RequiredArgsConstructor;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import com.example.tour_backend.service.NotificationService;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
//
//@RestController
//@RequestMapping("/api/notifications")
//@RequiredArgsConstructor
//public class NotificationController {
//    private final NotificationService notificationService;
//
//    @PostMapping
//    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationDto dto) {
//        NotificationDto created = notificationService.createNotification(dto);
//        return ResponseEntity.ok(created);
//    }
//
//
//    // (선택) 개별 조회용 메서드
//    @GetMapping("/{noticeId}")
//    public ResponseEntity<EntityModel<NotificationDto>> getNotificationById(@PathVariable Long noticeId) {
//        Notification notification = notificationService.findById(noticeId);
//        NotificationDto dto = toDto(n);
//        return ResponseEntity.ok(assembler.toModel(dto));
//    }
//
//    @GetMapping("/user/{userId}")
//    public ResponseEntity<CollectionModel<EntityModel<NotificationDto>>> getNotifications(@PathVariable Long userId) {
//        List<Notification> entities = notificationService.getUserNotifications(userId);
//
//        List<EntityModel<NotificationDto>> resources = entities.stream()
//                .map(this::toDto)
//                .map(assembler::toModel)
//                .collect(Collectors.toList());
//
//        CollectionModel<EntityModel<NotificationDto>> collection = CollectionModel.of(resources,
//                linkTo(methodOn(NotificationController.class)
//                        .getNotifications(userId)).withSelfRel()
//        );
//
//        return ResponseEntity.ok(collection);
//    }
//
//    // domain → 순수 DTO 변환 헬퍼
//    private NotificationDto toDto(Notification n) {
//        return NotificationDto.builder()
//                .noticeId(  n.getNoticeId()        )
//                .userId(    n.getUser().getUserId())
//                .threadId(  n.getThread().getThreadId())
//                .commentId( n.getComment().getCommentId())
//                .message(   n.getMessage()         )
//                .isRead(    n.isRead()             )
//                .build();
//    }
//
//    // createNotification 메서드는 그대로 두셔도 됩니다
//}