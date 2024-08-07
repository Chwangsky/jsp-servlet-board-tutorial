package com.study.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {
    private String category;
    private int fileCount;
    private String title;
    private String writer;
    private int views;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    private int currentPage;
    private int totalPage;
}
