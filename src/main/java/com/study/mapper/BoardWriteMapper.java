package com.study.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Select;

public interface BoardWriteMapper {

    @Select("SELECT name FROM category")
    List<String> getAllCategory();
}
