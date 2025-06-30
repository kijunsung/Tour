package com.example.tour_backend.controller;

import com.example.tour_backend.domain.schedule.Schedule;
import com.example.tour_backend.dto.map.MapDto;
import com.example.tour_backend.service.MapService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/maps")
@RequiredArgsConstructor
public class MapController {

    private final MapService mapService;

    /**
     * POST /maps
     * Body: MapDto(JSON)
     * -> 새로운 Map 을 생성
     */
    @PostMapping
    public ResponseEntity<MapDto> postMap(@RequestBody MapDto dto) {
        MapDto created = mapService.createMap(dto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }


    /**
     * GET /maps/{mapId}
     * -> mapId에 해당하는 Map 조회
     */
    @GetMapping("/{mapId}")
    public ResponseEntity<MapDto> getMap(@PathVariable Long mapId) {
        return mapService.getMap(mapId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * GET /maps/{mapId}
     * -> mapId에 해당하는 Map 조회
     */
//    @GetMapping("/{tourId}")
//    public ResponseEntity<MapDto> getMapTourId(@PathVariable Long tour) {
//        return mapService.getMap(tour)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
}