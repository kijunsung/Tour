package com.example.tour_backend.dto.schedule;

import lombok.*;
import org.springframework.http.ResponseEntity;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Optional;

@NoArgsConstructor
@Getter
@Setter
public class ScheduleDto {
    private Long scheduleId;
    private Long tourId;
    private String scheduleTitle;
    private String content;
    private Date date;
    private Time startTime;
    private Time endTime;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;

    @Builder
    public ScheduleDto (Long scheduleId,Long tourId,String scheduleTitle, String content, Date date,Time startTime,
                        Time endTime,LocalDateTime createDate, LocalDateTime modifiedDate){
        this.scheduleId = scheduleId;
        this.tourId = tourId;
        this.scheduleTitle = scheduleTitle;
        this.content = content;
        this.date = date;
        this.startTime =startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.modifiedDate=modifiedDate;
    }

}
