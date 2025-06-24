package com.example.tour_backend.service;

import com.example.tour_backend.domain.tour.Tour;
import com.example.tour_backend.domain.tour.TourRepository;
import com.example.tour_backend.domain.user.User;
import com.example.tour_backend.domain.user.UserRepository;
import com.example.tour_backend.dto.tour.TourDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TourService {
    private final TourRepository tourRepository;
    private final UserRepository userRepository;

    /** 1) 생성 */
    @Transactional
    public TourDto createTour(TourDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("사용자가 없습니다. id=" + dto.getUserId()));

        Tour tour = Tour.builder()
                .user(user)
                .title(dto.getTitle())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();

        Tour saved = tourRepository.save(tour);
        return mapToDto(saved);
    }

    /** 2) 단건 조회 */
    @Transactional(readOnly = true)
    public TourDto getTour(Long tourId) {
        return tourRepository.findById(tourId)
                .map(this::mapToDto)
                .orElseThrow(() -> new EntityNotFoundException("Tour가 없습니다. id=" + tourId));
    }

    /** 3) 전체 조회 */
    @Transactional(readOnly = true)
    public List<TourDto> getAllTours() {
        return tourRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    /** 4) 수정 */
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

    /** 5) 삭제 */
    @Transactional
    public void deleteTour(Long tourId) {
        if (!tourRepository.existsById(tourId)) {
            throw new EntityNotFoundException("삭제할 Tour가 없습니다. id=" + tourId);
        }
        tourRepository.deleteById(tourId);
    }

    /** 헬퍼: Tour 엔티티 → TourDto 변환 */
    private TourDto mapToDto(Tour t) {
        return TourDto.builder()
                .tourId(t.getTourId())
                .userId(t.getUser().getUserId())
                .title(t.getTitle())
                .startDate(t.getStartDate())
                .endDate(t.getEndDate())
                .createDate(t.getCreateDate())
                .modifiedDate(t.getModifiedDate())
                .build();
    }
}
