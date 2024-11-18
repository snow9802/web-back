package com.scsa.moin_back.review.controller;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.review.advice.ReviewExceptionHandler;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewGroupDTO;
import com.scsa.moin_back.review.dto.ReviewSearchDTO;
import com.scsa.moin_back.review.exception.AddReviewException;
import com.scsa.moin_back.review.exception.FindReviewException;
import com.scsa.moin_back.review.exception.ModifyReviewException;
import com.scsa.moin_back.review.exception.RemoveReviewException;
import com.scsa.moin_back.review.service.ReviewService;
import com.scsa.moin_back.review.service.ReviewServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewMainController {
    private final ReviewService reviewService;
    private final ReviewExceptionHandler reviewExceptionHandler;
    /*insert:regist, delete:remove, update:modify, select:search/get*/
    /*
     * POST
     * 실패 400 유효하지 않은 데이터: ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
     * 성공 201 생성됨: ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
     * PUT
     * 실패 404 요청id가 존재하지 않음: ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
     * 성공 200 수정된 사용자 정보: ResponseEntity.ok(updatedUser)
     * */


    /**
     * 리뷰 메인 화면
     *
     * @param currentPage
     * @param pageSize
     * @param category
     * @return
     */
    @GetMapping(value = {"/main/{category}/{currentPage}/{pageSize}", "/main"})
    public ResponseEntity<PageDTO<ReviewDTO>> getReviewList(
            @PathVariable Optional<Integer> currentPage,
            @PathVariable Optional<Integer> pageSize,
            @PathVariable Optional<String> category,
            @ModelAttribute ReviewSearchDTO searchDto
    ) throws Exception {
        Map<String, Object> searchParamMap = reviewService.convertToMap(searchDto);
        searchParamMap.put("categoryName", category.orElse("all"));

        PageDTO<ReviewDTO> pageDTO = reviewService.getReviewList(searchParamMap, currentPage.orElse(1), pageSize.orElse(5));
        return ResponseEntity.ok(pageDTO);
    }

    /**
     * 참가한 모임 불러오기
     *
     * @param httpSession
     * @param currentPage
     * @param pageSize
     * @return
     */
    @GetMapping(value = {"/find/{currentPage}/{pageSize}"})
    public ResponseEntity<PageDTO<ReviewGroupDTO>> getReviewGroup(
            HttpSession httpSession
            , @PathVariable Optional<Integer> currentPage
            , @PathVariable Optional<Integer> pageSize) throws FindReviewException {

        int cp = currentPage.orElse(1);
        int ps = pageSize.orElse(5); //한 화면에 보여줄 페이지수 5
        /*로그인한 사용자 아니면 뱉음*/
        //reviewExceptionHandler.checkLogin(httpSession);
        String id = "user03"; //테스트(삭제예정)

        PageDTO<ReviewGroupDTO> pageDTO = reviewService.getReviewGroup(id, cp, ps);

        return ResponseEntity.ok(pageDTO);
    }


    /**
     * 그룹에 따른 리뷰 작성페이지 열기
     * 로그인한 사용자만 이용 가능
     * 삭제된 그룹이거나 유효하지 않은 그룹이면 한번 필터링
     * @param groupId
     * @return
     */
    @GetMapping(value = {"/regist/{groupId}"})
    public ResponseEntity getReviewGroup(@PathVariable int groupId, HttpSession httpSession) throws FindReviewException {
        /*로그인한 사용자 아니면 뱉음*/
        //reviewExceptionHandler.checkLogin(httpSession);

        String id = "user01"; //테스트(삭제예정)
        reviewService.chkValidGroup(id, groupId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 그룹에 따른 리뷰 작성페이지 등록
     *
     * @param reviewDTO
     * @return
     */
    @PostMapping(value = {"/regist/{grouopId}"})
    public ResponseEntity registReview(ReviewDTO reviewDTO, HttpSession httpSession) throws AddReviewException {
        /*로그인한 사용자 아니면 뱉음*/
        //reviewExceptionHandler.checkLogin(httpSession);

        reviewService.addReview(reviewDTO);
        /*리뷰 디테일에 들어가는 내용 파라미터로 받아서 insert구문*/
        return new ResponseEntity<>(HttpStatus.OK);
    }


    /**
     * 그룹에 따른 리뷰 수정페이지 이동
     * 로그인한 사용자&내글인 사용자만 이용 가능
     *
     * @param reviewId
     * @return
     */
    @GetMapping(value = {"/modify/{reviewId}"})
    public ResponseEntity<ReviewDTO> modifyReview(@PathVariable int reviewId, HttpSession httpSession) throws FindReviewException {
        /*로그인한 사용자 아니면 뱉음*/
        //reviewExceptionHandler.checkLogin(httpSession);
        String id = "user01"; //테스트(삭제예정)
        ReviewDTO reviewDTO = reviewService.chkValidReview(id, reviewId);

        return ResponseEntity.ok(reviewDTO);
    }

    /**
     * 리뷰 수정 등록
     * @param reviewDTO
     * @return
     */
    @PutMapping(value = {"/modify/{reviewId}"})
    public ResponseEntity modifyReview(ReviewDTO reviewDTO, HttpSession httpSession) throws ModifyReviewException {
        /*로그인한 사용자 아니면 뱉음*/
        //reviewExceptionHandler.checkLogin(httpSession);
        reviewService.modifyReview(reviewDTO);
        //리뷰 이미지도 같이 수정하는건지?? 어떻게 들어오는지 확인 후 이미지 수정 추가 필요

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
