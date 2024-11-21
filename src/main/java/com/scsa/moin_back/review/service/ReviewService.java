package com.scsa.moin_back.review.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewGroupDTO;
import com.scsa.moin_back.review.exception.AddReviewException;
import com.scsa.moin_back.review.exception.ModifyReviewException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ReviewService {
    /**
     * 리뷰 메인페이지 리스트 조회
     * @param map
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageDTO<ReviewDTO> getReviewList(Map<String, Object> map, int currentPage, int pageSize);

    /**
     * 검색 파라미터 자동 map에 담기
     * @param obj
     * @return
     * @throws Exception
     */
    Map<String, Object> convertToMap(Object obj) throws Exception;

    /**
     * 작성할 리뷰 그룹 찾기
     * @param id
     * @param cp
     * @param ps
     * @return
     */
    PageDTO<ReviewGroupDTO> getReviewGroup(String id, int cp, int ps);

    /**
     * 유효한 그룹 여부 확인
     *
     * @param id
     * @param groupId
     * @return
     */
    String chkValidGroup(String id, int groupId);

    /**
     * 리뷰 추가
     *
     * @param reviewDTO
     * @return
     * @throws AddReviewException
     */
    ResponseEntity<Object> addReview(ReviewDTO reviewDTO, MultipartFile reviewImg) throws AddReviewException;

    /**
     * 리뷰 수정
     *
     * @param reviewDTO
     * @return
     */
    ResponseEntity<Object> modifyReview(ReviewDTO reviewDTO, MultipartFile reviewImg) throws ModifyReviewException;

    /**
     * 마이페이지 리뷰 조회
     * @param curPage
     * @param pageSize
     * @return
     */
    PageDTO<ReviewDTO> getMyPageReviewList(String id, Integer curPage, Integer pageSize);

    /**
     * 수정페이지 불러오기
     * @param id
     * @param reviewId
     * @return
     */
    ReviewDetailDTO getReviewModify(String id, int reviewId);

}
