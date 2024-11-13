package com.scsa.moin_back.review.controller;

import com.scsa.moin_back.review.service.ReviewMainService;
import com.scsa.moin_back.review.vo.ReviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewMainController {
    private final ReviewMainService reviewMainService;

    @RequestMapping("/main")
    public ResponseEntity<List<ReviewVO>> test(
           // @PathVariable("page") int page
    ){
        reviewMainService.test();
        System.out.println("오잉...?123132");
        return ResponseEntity.ok(reviewMainService.test());
    }


}
