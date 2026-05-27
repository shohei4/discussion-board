package com.example.discussion_board.dto;

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
    private UserSummary userSummary;
}
