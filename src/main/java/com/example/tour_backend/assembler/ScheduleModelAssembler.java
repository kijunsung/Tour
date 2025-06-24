package com.example.tour_backend.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.tour_backend.controller.ScheduleController;
import com.example.tour_backend.dto.schedule.ScheduleDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ScheduleModelAssembler
        implements RepresentationModelAssembler<ScheduleDto, EntityModel<ScheduleDto>> {

    @Override
    public EntityModel<ScheduleDto> toModel(ScheduleDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ScheduleController.class).getById(dto.getScheduleId()))
                        .withSelfRel(),
                linkTo(methodOn(ScheduleController.class).getAll())
                        .withRel("schedules")
        );
    }
}
