package com.example.tour_backend.controller;

import com.example.tour_backend.domain.schedule.Schedule;
import com.example.tour_backend.domain.tour.Tour;
import com.example.tour_backend.domain.tour.TourRepository;
import com.example.tour_backend.domain.schedule.ScheduleRepository;
import com.example.tour_backend.dto.schedule.ScheduleDto;
import com.example.tour_backend.service.ScheduleService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleController {
    private final ScheduleRepository scheduleRepository;
    private final TourRepository tourRepository;
    private final ScheduleService scheduleService;

    @GetMapping
    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @GetMapping("/{id}")
    public Schedule getById(@PathVariable Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ScheduleDto create(@RequestBody ScheduleDto dto) {
        Tour tour = tourRepository.findById(dto.getTourId())
                .orElseThrow(() -> new EntityNotFoundException("여행 정보가 없습니다."));
        Schedule schedule = Schedule.builder()
                .tour(tour)
                .scheduleTitle(dto.getScheduleTitle())
                .content(dto.getContent())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();
        Schedule saved = scheduleRepository.save(schedule);
        dto.setScheduleId(saved.getScheduleId());
        dto.setCreateDate(saved.getCreateDate());
        dto.setModifiedDate(saved.getModifiedDate());
        return dto;
    }

    @PutMapping("/{id}")
    public Schedule update(@PathVariable Long id, @RequestBody Schedule updated) {
        return scheduleRepository.findById(id).map(s -> {
            s.setScheduleTitle(updated.getScheduleTitle());
            s.setContent(updated.getContent());
            s.setDate(updated.getDate());
            s.setStartTime(updated.getStartTime());
            s.setEndTime(updated.getEndTime());
            s.setModifiedDate(new Timestamp(System.currentTimeMillis()).toLocalDateTime());
            return scheduleRepository.save(s);
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Schedule not found"));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        scheduleRepository.deleteById(id);
    }
}
