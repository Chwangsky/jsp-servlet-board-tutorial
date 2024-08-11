package com.study.mapper;

import org.apache.ibatis.annotations.Select;
import com.study.entity.FileDownloadEntity;

public interface DownloadMapper {

    @Select("SELECT file_dir as fileDir, attach_type as attachType, uuid_name as uuidName from files where files_id = #{fileId}")
    FileDownloadEntity selectFileById(int fileId);

}
