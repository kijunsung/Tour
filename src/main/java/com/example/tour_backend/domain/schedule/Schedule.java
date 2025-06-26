package com.example.tour_backend.domain.schedule;

import com.example.tour_backend.domain.tour.Tour;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.sql.Date;
import java.sql.Time;

import java.time.LocalDateTime;


@Entity
@Table(name = "schedule")
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scheduleId;


    // ⚠️ Tour 쪽에서 @JsonManagedReference 를 걸었으니 여기엔 @JsonBackReference 를 달아 줍니다
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourId", nullable = false)
    @JsonBackReference
    private Tour tour;

    @Column(nullable = false, length = 225)
    private String scheduleTitle;

    @Column(nullable = false, length = 225)
    private String content;

    @Column(nullable = false)
    private Date date;

    private Time startTime;

    private Time endTime;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

//    @OneToMany(mappedBy = "schedule")
//    private List<MapEntity> maps;

    @Builder
    public Schedule(Tour tour, String scheduleTitle, String content,
                    Date date, Time startTime, Time endTime,
                    LocalDateTime createDate, LocalDateTime modifiedDate /*List<Map> maps*/) {
        this.tour = tour;
        this.scheduleTitle = scheduleTitle;
        this.content = content;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;

    }
}