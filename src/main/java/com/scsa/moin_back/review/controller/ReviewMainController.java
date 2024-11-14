package com.scsa.moin_back.review.controller;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewMainController {
    private final ReviewServiceImpl reviewMainServiceImpl;

    @GetMapping(value = {"/main/{category}/{currentPage}/{pageSize}", "/main"})
    public ResponseEntity<PageDTO<ReviewDTO>> getReviewList(
            @PathVariable Optional<Integer> currentPage,
            @PathVariable Optional<Integer> pageSize,
            @PathVariable Optional<String> category,
            @RequestParam(required = false, defaultValue = "") String searchParam,
            @RequestParam(required = false, defaultValue = "all") String city,
            @RequestParam(required = false, defaultValue = "all") String district,
            HttpSession session
    ) {
        Map<String, Object> searchParamMap = new HashMap<>();
        searchParamMap.put("city", city);
        searchParamMap.put("district", district);
        searchParamMap.put("searchParam", searchParam);
        searchParamMap.put("categoryName", category.orElse("all"));

        int cp = 1;
        if (currentPage.isPresent()) {
            cp = currentPage.get();
        }

        int ps = pageSize.orElse(5); //한 화면에 보여줄 페이지수 5
        PageDTO<ReviewDTO> pageDTO = reviewMainServiceImpl.getReviewList(searchParamMap, cp, ps);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping(value = {"/detail/{reviewId}"})
    public ResponseEntity<ReviewDetailDTO> getReviewDetail(
        @PathVariable int reviewId
    ){
        reviewId = 1;
        ReviewDetailDTO reviewDetail = reviewMainServiceImpl.getReviewDetail(reviewId);
        return ResponseEntity.ok(reviewDetail);
    }

}
