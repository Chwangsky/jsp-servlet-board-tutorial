package com.study.dto;

import java.util.List;
import com.study.dto.resultset.BoardSearchResultSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BoardListDto {
    private int totalCount;

    private List<BoardSearchResultSet> boardList;

    private PaginationDto paginationDto;
}
