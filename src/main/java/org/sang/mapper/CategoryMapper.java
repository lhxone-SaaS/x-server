package org.sang.mapper;

import org.apache.ibatis.annotations.Param;
import org.sang.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sang on 2017/12/19.
 */
@Repository
public interface CategoryMapper {
    List<Category> getAllCategories();

    int deleteCategoryByIds(@Param("ids") String[] ids);

    int updateCategoryById(Category category);

    int addCategory(Category category);


}
