package com.study.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchDto {
    private String category;
    private int fileCount;
    private String title;
    private String writer;
    private int views;
    private Date regDate;
    private Date updateDate;
}
