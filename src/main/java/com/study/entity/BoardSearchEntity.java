package com.study.entity;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BoardSearchEntity {
    private String category;
    private int fileCount;
    private String title;
    private String writer;
    private int views;
    private Date regDate;
    private Date updateDate;
}
