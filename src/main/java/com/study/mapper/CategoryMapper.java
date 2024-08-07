/**
 * // TODO
 * 
 * this code is generated from chat-gpt
 */


package com.study.mapper;

import com.study.domain.Category;
import org.apache.ibatis.annotations.*;

public interface CategoryMapper {

    @Select("SELECT * FROM category WHERE category_id = #{categoryId}")
    Category selectCategoryById(int categoryId);

    @Insert("INSERT INTO category(name) VALUES(#{name})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId")
    void insertCategory(Category category);

    @Update("UPDATE category SET name=#{name} WHERE category_id=#{categoryId}")
    void updateCategory(Category category);

    @Delete("DELETE FROM category WHERE category_id=#{categoryId}")
    void deleteCategory(int categoryId);
}
