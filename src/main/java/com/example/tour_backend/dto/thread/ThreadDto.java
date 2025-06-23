package com.example.tour_backend.dto.thread;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ThreadDto {
    private Long threadId;
    private Long userId;
    private String title;
    private String content;
    private String author;
    private int count;
    private int heart;
    private String pdfPath;
    private int commentCount;
    private String area;
    private LocalDateTime createDate;
    private LocalDateTime modifiedDate;
}