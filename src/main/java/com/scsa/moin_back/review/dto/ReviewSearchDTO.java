package com.scsa.moin_back.review.dto;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.review.exception.FindReviewException;
import com.scsa.moin_back.review.vo.ReviewCommentVO;
import com.scsa.moin_back.review.vo.ReviewImgVO;
import com.scsa.moin_back.review.vo.ReviewRecommentVO;
import lombok.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class ReviewSearchDTO {
    String searchParam = "";
    String city = "all";
    String district = "all";
}