//package com.example.tour_backend.assembler;
//
//import com.example.tour_backend.controller.NotificationController;
//import com.example.tour_backend.controller.ThreadController;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.hateoas.server.RepresentationModelAssembler;
//import org.springframework.stereotype.Component;
//
//import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
//
//@Component
//public class NotificationDtoAssembler
//        implements RepresentationModelAssembler<NotificationDto, EntityModel<NotificationDto>> {
//
//    @Override
//    public EntityModel<NotificationDto> toModel(NotificationDto dto) {
//        return EntityModel.of(dto,
//                linkTo(methodOn(NotificationController.class)
//                        .getNotificationById(dto.getNoticeId()))
//                        .withSelfRel(),
//                linkTo(methodOn(ThreadController.class)
//                        .getThread(dto.getThreadId()))
//                        .withRel("thread")
//        );
//    }
//}
