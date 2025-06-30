package com.example.tour_backend.dto.map;



import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter

public class MapDto {
    private Long mapId;
    private Long scheduleId;
    private Long tour;
    private String location;
    private LocalDateTime createDate;

    @Builder
    public MapDto(Long mapId, Long scheduleId, Long tour, String location, LocalDateTime createDate) {
        this.mapId = mapId;
        this.scheduleId = scheduleId;
        this.tour = tour;
        this.location = location;
        this.createDate = createDate;
    }
}