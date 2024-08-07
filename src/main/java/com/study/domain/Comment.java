package com.study.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Comment {
    private int commentId;
    private int boardId;
    private String content;
    private Date regDate;

    private Board board;
}
