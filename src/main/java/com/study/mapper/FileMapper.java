/**
 * // TODO
 * 
 * this code is generated from chat-gpt
 */


package com.study.mapper;

import com.study.domain.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface FileMapper {

    @Select("SELECT * FROM files WHERE board_id = #{boardId}")
    List<File> selectFilesByBoardId(int boardId);

    @Insert("INSERT INTO files(attach_type, byte_size, uuid_name, org_name, file_dir, board_id) "
            + "VALUES(#{attachType}, #{byteSize}, #{uuidName}, #{orgName}, #{fileDir}, #{boardId})")
    @Options(useGeneratedKeys = true, keyProperty = "filesId")
    void insertFile(File file);

    @Update("UPDATE files SET attach_type=#{attachType}, byte_size=#{byteSize}, uuid_name=#{uuidName}, "
            + "org_name=#{orgName}, file_dir=#{fileDir}, board_id=#{boardId} WHERE files_id=#{filesId}")
    void updateFile(File file);

    @Delete("DELETE FROM files WHERE files_id=#{filesId}")
    void deleteFile(int filesId);
}
