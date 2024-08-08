package com.study.dto.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardDetailDto {
    private int boardId;
    private String category;
    private String boardTitle;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;
}
