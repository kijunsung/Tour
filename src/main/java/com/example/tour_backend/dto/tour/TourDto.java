package com.example.tour_backend.dto.tour;

import com.example.tour_backend.dto.map.MapDto;
import com.example.tour_backend.dto.schedule.ScheduleDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class TourDto {
    private Long tourId;
    private Long userId;
    private String title;
    private Date startDate;
    private Date endDate;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    /** 새롭게 추가된 부분: 이 투어에 속한 스케줄 목록 */
    private List<ScheduleDto> schedules;
    private List<MapDto> maps;

    @Builder
    public TourDto (Long tourId, Long userId,String title,
                    Date startDate, Date endDate,LocalDateTime createDate,
                    LocalDateTime modifiedDate, List<ScheduleDto> schedules,
                    List<MapDto> maps){

        this.tourId = tourId;
        this.userId =userId;
        this.title=title;
        this.startDate = startDate;
        this.endDate=endDate;
        this.createDate=createDate;
        this.modifiedDate=modifiedDate;
        this.schedules = schedules;
        this.maps = maps;

    }
}
