package com.study.domain;

import lombok.Data;
import java.util.Date;
import java.util.List;

@Data
public class Board {
    private String title;
    private String writer;
    private int views;
    private Date regDate;
    private Date updateDate;
    private int categoryId;
    private String content;
    private String password;

    private Category category;
    private List<Comment> comments;
    private List<File> files;
}
