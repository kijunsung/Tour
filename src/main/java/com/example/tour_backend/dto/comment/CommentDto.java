package com.example.tour_backend.dto.comment;

import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter @Setter
public class CommentDto {
    private Long commentId;    // 저장 후 되돌려줄 PK
    private Long threadId;     // 요청 시 클라이언트가 전달할 Thread PK
    private String comment;    // 댓글 본문
    private String author;     // 작성자
    private LocalDateTime createDate;   // 저장 시 자동 세팅
    private LocalDateTime modifiedDate; // 수정 시 자동 세팅

    @Builder
    public CommentDto(Long commentId,
                      Long threadId,
                      String comment,
                      String author,
                      LocalDateTime createDate,
                      LocalDateTime modifiedDate) {
        this.commentId    = commentId;
        this.threadId     = threadId;
        this.comment      = comment;
        this.author       = author;
        this.createDate   = createDate;
        this.modifiedDate = modifiedDate;
    }
}
