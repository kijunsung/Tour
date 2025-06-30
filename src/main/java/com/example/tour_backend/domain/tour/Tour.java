package com.example.tour_backend.domain.tour;

import com.example.tour_backend.domain.schedule.Schedule;
import com.example.tour_backend.domain.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tour")
@NoArgsConstructor
@Getter
@Setter
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tourId;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;


    @Column(nullable = false, length = 225)
    private String title;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @CreationTimestamp
    private LocalDateTime createDate;

    @UpdateTimestamp
    private LocalDateTime modifiedDate;

    /** Tour → Schedule 일대다 (Jackson이 serialize할 때 "schedules" 필드 보이도록) */
    @OneToMany(mappedBy = "tour", fetch = FetchType.LAZY)
    @JsonManagedReference             // ⚠️ Jackson 순환참조 방지
    private List<Schedule> schedules = new ArrayList<>();

//    @OneToMany(mappedBy = "tour")
//    private List<Traffic> traffics;
//
//    @OneToMany(mappedBy = "tour")
//    private List<Weather> weathers;

    @Builder
    public Tour(User user, String title, Date startDate, Date endDate,
                LocalDateTime createDate, LocalDateTime modifiedDate, List<Schedule> schedules
                /*List<Traffic> traffics, List<Weather> weathers*/) {
        this.user = user;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createDate = createDate;
        this.modifiedDate = modifiedDate;
        this.schedules = schedules;
//        this.traffics = traffics;
//        this.weathers = weathers;
    }

}
