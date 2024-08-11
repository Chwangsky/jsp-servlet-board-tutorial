package com.study.dto;

import java.time.LocalDateTime;
import com.study.entity.BoardUpdateDetailEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateDetailDto {
    int boardId;
    String category;
    LocalDateTime regDate;
    LocalDateTime updateDate;
    int views;
    String writer;
    String password;
    String title;
    String content;

    public BoardUpdateDetailDto(BoardUpdateDetailEntity entity) {
        this.boardId = entity.getBoardId();
        this.category = entity.getCategory();
        this.updateDate = entity.getRegDate();
        this.regDate = entity.getUpdateDate();
        this.views = entity.getViews();
        this.writer = entity.getWriter();
        this.password = entity.getPassword();
        this.title = entity.getTitle();
        this.content = entity.getContent();
    }
}

