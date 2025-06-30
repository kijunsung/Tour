package com.example.tour_backend.service;

import com.example.tour_backend.domain.tour.Tour;
import com.example.tour_backend.domain.tour.TourRepository;
import com.example.tour_backend.domain.user.User;
import com.example.tour_backend.domain.user.UserRepository;
import com.example.tour_backend.dto.map.MapDto;
import com.example.tour_backend.dto.tour.TourDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.tour_backend.dto.schedule.ScheduleDto;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    /**
     * 1) 생성
     */
    @Transactional
    public TourDto createTour(TourDto dto) {
        // 1-1) user 조회 (없으면 404)
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자가 없습니다. id=" + dto.getUserId()));

        // 1-2) 엔티티 빌드 및 저장
        Tour tour = Tour.builder()
                .user(user)
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        Tour saved = tourRepository.save(tour);
        return mapToDto(saved);
    }

    /**
     * 2) 단건 조회
     */
    @Transactional(readOnly = true)
    public TourDto getTour(Long tourId) {
        return tourRepository.findById(tourId)
                .map(this::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Tour가 없습니다. id=" + tourId));
    }

    /**
     * 3) 전체 조회
     */
    @Transactional(readOnly = true)
    public List<TourDto> getAllTours() {
        return tourRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }



    /**
     * 4) 수정
     */
    @Transactional
    public TourDto updateTour(Long tourId, TourDto dto) {
        Tour existing = tourRepository.findById(tourId)
                .orElseThrow(() -> new EntityNotFoundException("수정할 Tour가 없습니다. id=" + tourId));

        existing.setTitle(dto.getTitle());
        existing.setStartDate(dto.getStartDate());
        existing.setEndDate(dto.getEndDate());

        Tour saved = tourRepository.save(existing);
        return mapToDto(saved);
    }

    /**
     * 5) 삭제
     */
    @Transactional
    public void deleteTour(Long tourId) {
        if (!tourRepository.existsById(tourId)) {
            throw new EntityNotFoundException("삭제할 Tour가 없습니다. id=" + tourId);
        }
        tourRepository.deleteById(tourId);
    }


    /** 헬퍼: Tour 엔티티 → TourDto 변환 */
    private TourDto mapToDto(Tour t) {
        // 1) Schedule 엔티티 목록을 ScheduleDto 목록으로 변환
        List<ScheduleDto> scheduleDtos = t.getSchedules().stream()
                .map(s -> ScheduleDto.builder()
                        .scheduleId(s.getScheduleId())
                        .scheduleTitle(s.getScheduleTitle())
                        .content(s.getContent())
                        .date(s.getDate())
                        .startTime(s.getStartTime())
                        .endTime(s.getEndTime())
                        .createDate(s.getCreateDate())
                        .modifiedDate(s.getModifiedDate())
                        .build()
                )
                .collect(Collectors.toList());

        // 2) TourDto 빌더에 schedules 필드 추가
        return TourDto.builder()
                .tourId(t.getTourId())
                .userId(t.getUser().getUserId())
                .title(t.getTitle())
                .startDate(t.getStartDate())
                .endDate(t.getEndDate())
                .createDate(t.getCreateDate())
                .modifiedDate(t.getModifiedDate())
                .schedules(scheduleDtos)        // ← 새로 추가된 부분
                .build();
    }
}
