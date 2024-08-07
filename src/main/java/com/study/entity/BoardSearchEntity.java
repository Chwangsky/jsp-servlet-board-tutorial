package com.study.entity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private int totalCount;
    private String category;
    private int fileCount;
    private String title;
    private String writer;
    private int views;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public String getRegDateAsString() {
        return regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getUpdateDateAsString() {
        return updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
