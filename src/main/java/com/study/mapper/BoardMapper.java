/**
 * // TODO
 * 
 * this code is generated from chat-gpt
 */

package com.study.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.One;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import com.study.domain.Board;

public interface BoardMapper {

        @Select("SELECT * FROM board WHERE board_id = #{boardId}")
        @Results({@Result(property = "boardId", column = "board_id"),
                        @Result(property = "title", column = "title"),
                        @Result(property = "writer", column = "writer"),
                        @Result(property = "views", column = "views"),
                        @Result(property = "regDate", column = "reg_date"),
                        @Result(property = "updateDate", column = "update_date"),
                        @Result(property = "categoryId", column = "category_id"),
                        @Result(property = "content", column = "content"),
                        @Result(property = "password", column = "password"),
                        @Result(property = "category", column = "category_id", one = @One(
                                        select = "com.study.mapper.CategoryMapper.selectCategoryById")),
                        @Result(property = "comments", column = "board_id", many = @Many(
                                        select = "com.study.mapper.CommentMapper.selectCommentsByBoardId")),
                        @Result(property = "files", column = "board_id", many = @Many(
                                        select = "com.study.mapper.FileMapper.selectFilesByBoardId"))})
        Board selectBoardById(int boardId);

        @Insert("INSERT INTO board(title, writer, views, reg_date, update_date, category_id, content, password) "
                        + "VALUES(#{title}, #{writer}, #{views}, #{regDate}, #{updateDate}, #{categoryId}, #{content}, #{password})")
        @Options(useGeneratedKeys = true, keyProperty = "boardId")
        void insertBoard(Board board);

        @Update("UPDATE board SET title=#{title}, writer=#{writer}, views=#{views}, reg_date=#{regDate}, "
                        + "update_date=#{updateDate}, category_id=#{categoryId}, content=#{content}, password=#{password} "
                        + "WHERE board_id=#{boardId}")
        void updateBoard(Board board);

        @Delete("DELETE FROM board WHERE board_id=#{boardId}")
        void deleteBoard(int boardId);
}
