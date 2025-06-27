package com.example.tour_backend.controller;

import com.example.tour_backend.domain.tour.Tour;
import com.example.tour_backend.domain.user.User;
import com.example.tour_backend.domain.tour.TourRepository;
import com.example.tour_backend.domain.user.UserRepository;
import com.example.tour_backend.dto.tour.TourDto;
import com.example.tour_backend.service.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;



@RestController
@RequestMapping("/api/tours")
@RequiredArgsConstructor
public class TourController {
    private final TourRepository tourRepository;
    private final UserRepository userRepository;
    private final TourService tourService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TourDto create(@RequestBody TourDto dto) {
        // dto.getUserId() 로 userId를 꺼내세요
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("사용자가 없습니다. id=" + dto.getUserId()));
        // 나머지는 앞서 드린 코드처럼…
        Tour tour = Tour.builder()
                .user(user)
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        Tour saved = tourRepository.save(tour);
        // DTO에 저장된 값 채워주고 반환
        dto.setTourId(saved.getTourId());
        dto.setCreateDate(saved.getCreateDate());
        dto.setModifiedDate(saved.getModifiedDate());
        return dto;
    }


    /** (1) 전체 조회는 기존처럼 */
    @GetMapping
    public List<Tour> getAll() {
        return tourRepository.findAll();
    }

    /** (2) 한 건 조회: schedules 를 함께 fetch 하는 findWithSchedulesByTourId 사용 */
    @GetMapping("/{id}")
    public TourDto getById(@PathVariable Long id) {
        return tourService.getTour(id);      // ★ DTO로 반환
    }


    @PutMapping("/{id}")
    public Tour update(@PathVariable Long id, @RequestBody Tour updated) {
        return tourRepository.findById(id).map(t -> {
            t.setTitle(updated.getTitle());
            t.setStartDate(updated.getStartDate());
            t.setEndDate(updated.getEndDate());
            t.setModifiedDate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
            return tourRepository.save(t);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tour not found"));
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        tourRepository.deleteById(id);
    }
}
