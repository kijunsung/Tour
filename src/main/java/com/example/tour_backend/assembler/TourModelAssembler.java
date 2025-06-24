package com.example.tour_backend.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.example.tour_backend.controller.TourController;
import com.example.tour_backend.dto.tour.TourDto;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class TourModelAssembler
        implements RepresentationModelAssembler<TourDto, EntityModel<TourDto>> {

    @Override
    public EntityModel<TourDto> toModel(TourDto tour) {
        return EntityModel.of(tour,
                // 자기 자신 링크 (/api/tours/{id})
                linkTo(methodOn(TourController.class).getById(tour.getTourId()))
                        .withSelfRel(),
                // 전체 리스트 링크 (/api/tours)
                linkTo(methodOn(TourController.class).getAll())
                        .withRel("tours")
        );
    }
}
