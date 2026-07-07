package com.example.discussion_board.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GidaiResponse {

    private Long id;
    private String title;
    private String body;
    private String genre;
    //編集ボタン表示用フラグ
    private boolean editable;
    //削除フラグ
    @Builder.Default
    private boolean isDeleted = false;
    private UserSummary userSummary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
