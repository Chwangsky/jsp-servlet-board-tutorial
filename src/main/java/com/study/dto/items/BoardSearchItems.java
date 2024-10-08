package com.study.dto.items;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder // TODO
@AllArgsConstructor
@NoArgsConstructor
public class BoardSearchItems {
    private int boardId;
    private String category;
    private int fileCount;
    private String title;
    private String writer;
    private int views;
    private LocalDateTime regDate;
    private LocalDateTime updateDate;


    // for jsp
    public String getRegDateAsString() {
        return regDate != null ? regDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "";
    }

    // for jsp
    public String getUpdateDateAsString() {
        return updateDate != null
                ? updateDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                : "";
    }
}
