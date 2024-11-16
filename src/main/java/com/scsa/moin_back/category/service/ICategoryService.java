package com.scsa.moin_back.category.service;

import com.scsa.moin_back.category.vo.CategoryVO;

import java.util.List;

public interface ICategoryService {
    List<CategoryVO> getCategories();
}
