package com.scsa.moin_back.category.mapper;

import com.scsa.moin_back.category.vo.CategoryVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    List<CategoryVO> getCategories();
}
