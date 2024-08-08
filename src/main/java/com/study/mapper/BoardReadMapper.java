package com.study.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import com.study.entity.BoardDetailEntity;
import com.study.entity.CommentEntity;
import com.study.entity.FileEntity;

public interface BoardReadMapper {

        @Select("SELECT b.board_id AS boardId, "
                        + "(SELECT c.name FROM category c WHERE c.category_id = b.category_id) AS category, "
                        + "b.title AS title, " + "b.reg_date AS regDate, "
                        + "b.update_date AS updateDate " + "FROM board b "
                        + "WHERE b.board_id = #{boardId}")
        BoardDetailEntity selectBoardDetailById(int boardId);

        @Select("SELECT c.comment_id AS commentId, " + "c.content AS content, "
                        + "c.reg_date AS regDate " + "FROM comments c "
                        + "WHERE c.board_id = #{boardId}")
        List<CommentEntity> selectCommentsByBoardId(@Param("boardId") int boardId);

        @Select("SELECT f.files_id AS filesId, " + "f.attach_type AS attachType, "
                        + "f.byte_size AS byteSize, " + "f.uuid_name AS uuidName, "
                        + "f.org_name AS orgName, " + "f.file_dir AS fileDir " + "FROM files f "
                        + "WHERE f.board_id = #{boardId}")
        List<FileEntity> selectFilesByBoardId(@Param("boardId") int boardId);

}
