/**
 * // TODO
 * 
 * this code is generated from chat-gpt
 */


package com.study.mapper;

import com.study.domain.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface CommentMapper {

    @Select("SELECT * FROM comments WHERE board_id = #{boardId}")
    List<Comment> selectCommentsByBoardId(int boardId);

    @Insert("INSERT INTO comments(board_id, content, reg_date) VALUES(#{boardId}, #{content}, #{regDate})")
    @Options(useGeneratedKeys = true, keyProperty = "commentId")
    void insertComment(Comment comment);

    @Update("UPDATE comments SET content=#{content}, reg_date=#{regDate} WHERE comment_id=#{commentId}")
    void updateComment(Comment comment);

    @Delete("DELETE FROM comments WHERE comment_id=#{commentId}")
    void deleteComment(int commentId);
}
