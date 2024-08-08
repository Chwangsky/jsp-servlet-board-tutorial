package com.study.mapper;

import org.apache.ibatis.annotations.Select;
import com.study.entity.BoardDetailEntity;

public interface BoardReadMapper {

    @Select("SELECT b.board_id AS boardId, "
            + "(SELECT c.name FROM category c WHERE c.category_id = b.category_id) AS category, "
            + "b.title AS title, " + "b.reg_date AS regDate, " + "b.update_date AS updateDate "
            + "FROM board b " + "WHERE b.board_id = #{boardId}")
    BoardDetailEntity selectBoardDetailById(int boardId);

}
