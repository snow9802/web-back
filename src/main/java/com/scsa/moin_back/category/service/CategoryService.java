package com.scsa.moin_back.category.service;

import com.scsa.moin_back.category.mapper.CategoryMapper;
import com.scsa.moin_back.category.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryVO> getCategories() {
        List<CategoryVO> categoryVOList = categoryMapper.getCategories();
        return categoryVOList;
    }
}
