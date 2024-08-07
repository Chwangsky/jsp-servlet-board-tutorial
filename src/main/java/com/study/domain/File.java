package com.study.domain;

import lombok.Data;

@Data
public class File {
    private int filesId;
    private String attachType;
    private int byteSize;
    private String uuidName;
    private String orgName;
    private String fileDir;
    private int boardId;

    private Board board;
}
