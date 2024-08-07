package com.study.dto.resultset;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchResultSet {
    private String category;
    private int fileCount;
    private String title;
    private String writer;
    private int views;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;

    public String getRegDateAsString() {
        return regDate != null ? regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "";
    }

    public String getUpdateDateAsString() {
        return updateDate != null
                ? updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "";
    }
}
