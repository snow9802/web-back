package com.scsa.moin_back.category.controller;


import com.scsa.moin_back.category.service.ICategoryService;
import com.scsa.moin_back.category.vo.CategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping
    public ResponseEntity<List<CategoryVO>> getCategories(){
        try{
            List<CategoryVO> categories = categoryService.getCategories();
            System.out.println(categories);
            return ResponseEntity.ok().body(categories);
        } catch (Exception e) {
            return ResponseEntity.status(400).build();
        }
    }
}
