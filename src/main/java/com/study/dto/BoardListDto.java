package com.study.dto;

import java.util.List;
import com.study.dto.items.BoardSearchItems;
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
    private List<BoardSearchItems> boardList;
    private PaginationDto paginationDto;

}
