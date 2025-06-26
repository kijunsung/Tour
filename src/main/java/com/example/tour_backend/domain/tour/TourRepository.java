package com.example.tour_backend.domain.tour;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TourRepository extends JpaRepository<Tour, Long> {

    /**
     * Tour 한 건 조회 시 schedules 컬렉션을 즉시 가져오도록 해 주는 메서드.
     * JPQL의 JOIN FETCH 와 동일 효과를 EntityGraph로 손쉽게.
     */
    @EntityGraph(attributePaths = "schedules")
    Optional<Tour> findWithSchedulesByTourId(Long tourId);
}
