package com.example.tour_backend.controller;

import com.example.tour_backend.dto.map.MapDto;
import com.example.tour_backend.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    /**
     * POST /api/maps
     */
    @PostMapping
    public ResponseEntity<EntityModel<MapDto>> postMap(@RequestBody MapDto dto) {
        MapDto created = mapService.createMap(dto);

        EntityModel<MapDto> resource = EntityModel.of(created,
                // self 링크
                linkTo(methodOn(MapController.class).postMap(dto)).withSelfRel(),
                // 생성된 맵 조회 링크
                linkTo(methodOn(MapController.class).getMap(created.getMapId())).withRel("map"),
                // 일정 리소스로 연결되는 링크
                linkTo(methodOn(ScheduleController.class).getSchedule(created.getScheduleId())).withRel("schedule")
        );

        return ResponseEntity
                .created(linkTo(methodOn(MapController.class).getMap(created.getMapId())).toUri())
                .body(resource);
    }

    /**
     * GET /api/maps/{mapId}
     */
    @GetMapping("/{mapId}")
    public ResponseEntity<EntityModel<MapDto>> getMap(@PathVariable Long mapId) {
        return mapService.getMap(mapId)
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(MapController.class).getMap(mapId)).withSelfRel(),
                        linkTo(methodOn(MapController.class).getAllMaps()).withRel("maps"),
                        linkTo(methodOn(ScheduleController.class).getSchedule(dto.getScheduleId())).withRel("schedule")
                ))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /api/maps
     */
    @GetMapping
    public ResponseEntity<CollectionModel<EntityModel<MapDto>>> getAllMaps() {
        List<EntityModel<MapDto>> list = mapService.getAllMaps().stream()
                .map(dto -> EntityModel.of(dto,
                        linkTo(methodOn(MapController.class).getMap(dto.getMapId())).withSelfRel(),
                        linkTo(methodOn(ScheduleController.class).getSchedule(dto.getScheduleId())).withRel("schedule")
                ))
                .collect(Collectors.toList());

        CollectionModel<EntityModel<MapDto>> collection = CollectionModel.of(list,
                linkTo(methodOn(MapController.class).getAllMaps()).withSelfRel()
        );

        return ResponseEntity.ok(collection);
    }
}
