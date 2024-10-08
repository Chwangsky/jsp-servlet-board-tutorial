package com.study.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import com.study.entity.BoardCreateEntity;
import com.study.entity.CategoryIdNameEntity;
import com.study.entity.FileEntity;

public interface BoardWriteMapper {

        @Select("SELECT category_id as categoryId, name FROM category")
        List<CategoryIdNameEntity> getAllCategories();

        @Insert("INSERT INTO board (title, writer, views, reg_date, update_date, category_id, content, password) "
                        + "VALUES (#{title}, #{writer}, #{views}, NOW(), NOW(), #{categoryId}, #{content}, #{password})")
        @Options(useGeneratedKeys = true, keyProperty = "boardId")
        void insertBoard(BoardCreateEntity board);

        @Insert("INSERT INTO files (board_id, attach_type, byte_size, uuid_name, org_name, file_dir) "
                        + "VALUES (#{boardId}, #{attachType}, #{byteSize}, #{uuidName}, #{orgName}, #{fileDir})")
        void insertFile(FileEntity file);
}
