package com.study.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.study.entity.BoardSearchEntity;
import java.time.LocalDateTime;

public interface BoardSearchMapper {
    List<BoardSearchEntity> boardSearch(

            @Param("regDateStart") String regDateStart, @Param("regDateEnd") String regDateEnd,
            @Param("categoryName") String categoryName,

            @Param("titleAndContentKeyword") String titleAndContentKeyword,
            @Param("limit") int limit, @Param("offset") int offset);



    int boardSearchCount(@Param("regDateStart") String regDateStart,
            @Param("regDateEnd") String regDateEnd, @Param("categoryName") String categoryName,

            @Param("titleAndContentKeyword") String titleAndContentKeyword);

    // this is just for TEST -- and it works
    @Select("SELECT reg_date FROM board")
    List<LocalDateTime> getAllRegDates();


}
