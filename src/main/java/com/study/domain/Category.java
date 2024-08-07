package com.study.domain;

import java.util.List;
import lombok.Data;

@Data
public class Category {
    private int categoryId;
    private String name;
    private List<Board> boards;
}
