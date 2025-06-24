package com.example.tour_backend.service;

import com.example.tour_backend.domain.schedule.Schedule;
import com.example.tour_backend.domain.schedule.ScheduleRepository;
import com.example.tour_backend.domain.tour.Tour;
import com.example.tour_backend.domain.tour.TourRepository;
import com.example.tour_backend.dto.schedule.ScheduleDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final TourRepository tourRepository;

    /** 1) 생성 */
    @Transactional //계좌이체 예시로 들었음 오류 났을 때 없던것처럼 만들기 위홰
    public ScheduleDto createSchedule(ScheduleDto dto) {
        Tour tour = tourRepository.findById(dto.getTourId())
                .orElseThrow(() -> new EntityNotFoundException("여행 정보가 존재하지 않습니다."));

        Schedule schedule = Schedule.builder()
                .tour(tour)
                .scheduleTitle(dto.getScheduleTitle())
                .content(dto.getContent())
                .date(dto.getDate())
                .startTime(dto.getStartTime())
                .endTime(dto.getEndTime())
                .build();

        Schedule saved = scheduleRepository.save(schedule);
        return mapToDto(saved);
    }

    /** 2) 단건 조회 */
    @Transactional(readOnly = true)
    public ScheduleDto getSchedule(Long id) {
        return scheduleRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() ->
                        new EntityNotFoundException("Schedule가 없습니다. id=" + id));
    }


    /** 3) 전체 조회 */
    @Transactional(readOnly = true)
    public List<ScheduleDto> getAllSchedules() {
        return scheduleRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /** 4) 수정 */
    @Transactional
    public ScheduleDto updateSchedule(Long id, ScheduleDto dto) {
        Schedule s = scheduleRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("수정할 Schedule가 없습니다. id=" + id));

        s.setScheduleTitle(dto.getScheduleTitle());
        s.setContent(dto.getContent());
        s.setDate(dto.getDate());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());

        Schedule saved = scheduleRepository.save(s);
        return mapToDto(saved);
    }

    /** 5) 삭제 */
    @Transactional
    public void deleteSchedule(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("삭제할 Schedule가 없습니다. id=" + id);
        }
        scheduleRepository.deleteById(id);
    }

    /** 헬퍼: Schedule → ScheduleDto */
    private ScheduleDto mapToDto(Schedule s) {
        return ScheduleDto.builder()
                .scheduleId(s.getScheduleId())
                .tourId(s.getTour().getTourId())
                .scheduleTitle(s.getScheduleTitle())
                .content(s.getContent())
                .date(s.getDate())
                .startTime(s.getStartTime())
                .endTime(s.getEndTime())
                .createDate(s.getCreateDate())
                .modifiedDate(s.getModifiedDate())
                .build();
    }
}

