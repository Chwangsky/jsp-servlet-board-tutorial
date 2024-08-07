package com.study.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.study.entity.BoardSearchEntity;

public interface BoardSearchMapper {
    List<BoardSearchEntity> boardSearch(

            @Param("regDateStart") String regDateStart, @Param("regDateEnd") String regDateEnd,
            @Param("categoryName") String categoryName,

            @Param("titleAndContentKeyword") String titleAndContentKeyword,
            @Param("limit") int limit, @Param("offset") int offset);
}
