package com.scsa.moin_back.review.controller;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.review.advice.ReviewExceptionHandler;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@RestController
@RequestMapping("/my")
@RequiredArgsConstructor
public class ReviewMypageController {
        private final ReviewService reviewService;
    private final ReviewExceptionHandler reviewExceptionHandler;

    /**
     * 마이페이지 내 리뷰 조회
     *
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping(value = {"/review/{currentPage}/{pageSize}", "/review"})
    public ResponseEntity<PageDTO<ReviewDTO>> getReviewList(
            @PathVariable Optional<Integer> currentPage,
            @PathVariable Optional<Integer> pageSize,
            HttpSession httpSession
    ) throws Exception {
        String id = (String)httpSession.getAttribute("id");
        id="user01";
        if(id == null){
            reviewExceptionHandler.checkLogin(httpSession);
        }
        PageDTO<ReviewDTO> pageDTO = reviewService.getMyPageReviewList(id, currentPage.orElse(1), pageSize.orElse(5));
        return ResponseEntity.ok(pageDTO);
    }


}
