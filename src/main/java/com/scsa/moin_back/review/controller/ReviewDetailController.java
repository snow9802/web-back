package com.scsa.moin_back.review.controller;

import com.scsa.moin_back.review.advice.ReviewExceptionHandler;
import com.scsa.moin_back.review.dto.ReviewCommentDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewRecommentDTO;
import com.scsa.moin_back.review.exception.RemoveReviewException;
import com.scsa.moin_back.review.service.ReviewDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestControllerAdvice
@RequiredArgsConstructor
@RequestMapping("/review/detail/*")
public class ReviewDetailController {

    private final ReviewDetailService reviewDetailService;
    private final ReviewExceptionHandler reviewExceptionHandler;


     /** 리뷰 상세 페이지 이동
     * @param reviewId
     * @return
     */
    @GetMapping(value = {"/{reviewId}"})
    public ResponseEntity<ReviewDetailDTO> getReviewDetail(
            @PathVariable int reviewId
    ) {
        ReviewDetailDTO reviewDetail = reviewDetailService.getReviewDetail(reviewId);
        return ResponseEntity.ok(reviewDetail);
    }

    /**
     * 리뷰 삭제
     * @param reviewId
     * @return
     */
    @DeleteMapping(value = {"/{reviewId}"})
    public ResponseEntity removeReview(
            HttpSession httpSession,
            @PathVariable int reviewId
    ) throws RemoveReviewException {
        /*로그인한 사용자 아니면 뱉음*/
        //reviewExceptionHandler.checkLogin(httpSession);
        /*작성자가 사용자가 아니면 뱉음*/

        reviewDetailService.removeReview(reviewId);

        /*리뷰 아이디 파라미터로 받아서 delete구문*/
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 리뷰 댓글 등록
     * @return
     */
    @PostMapping(value={"/comment/{reviewId}"})
    public ResponseEntity addReviewComment(
            @PathVariable int reviewId,
            @RequestParam("commentContent") String commentContent
            , HttpSession httpSession
    ){
        ReviewCommentDTO reviewCommentDTO = new ReviewCommentDTO();
        reviewCommentDTO.setReviewId(reviewId);
        reviewCommentDTO.setCommentContent(commentContent);
        reviewCommentDTO.setRcWriterId(httpSession.getAttribute("id").toString());
        reviewDetailService.registReviewComment(reviewCommentDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 리뷰 댓글 수정
     * @return
     */
    @PutMapping(value={"/comment/{reviewCommentId}"})
    public ResponseEntity modifyReviewComment(
            @PathVariable int reviewCommentId,
            @RequestParam("commentContent") String commentContent
            , HttpSession httpSession    ){

        ReviewCommentDTO reviewCommentDTO = new ReviewCommentDTO();
        reviewCommentDTO.setReviewCommentId(reviewCommentId);
        reviewCommentDTO.setCommentContent(commentContent);
        reviewCommentDTO.setRcWriterId(httpSession.getAttribute("id").toString());
        reviewDetailService.modifyReviewComment(reviewCommentDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 리뷰 댓글 삭제
     * @return
     */
    @DeleteMapping(value={"/comment/{reviewCommentId}"})
    public ResponseEntity removeReviewComment(
            @PathVariable int reviewCommentId
    ){

        reviewDetailService.removeReviewComment(reviewCommentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 리뷰 대댓글 등록
     * @return
     */
    @PostMapping(value={"/recomment/{reviewCommentId}"})
    public ResponseEntity addReviewRecomment(
            @PathVariable int reviewCommentId,
            @RequestParam("recommentContent") String recommentContent,
            HttpSession httpSession
    ){
        ReviewRecommentDTO reviewRecommentDTO = new ReviewRecommentDTO();
        reviewRecommentDTO.setReviewCommentId(reviewCommentId);
        reviewRecommentDTO.setRecommentContent(recommentContent);
        reviewRecommentDTO.setRrcWriterId(httpSession.getAttribute("id").toString());

        reviewDetailService.registReviewRecomment(reviewRecommentDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 리뷰 대댓글 수정
     * @return
     */
    @PutMapping(value={"/recomment/{reviewRecommentId}"})
    public ResponseEntity modifyReviewRecomment(
            @PathVariable int reviewRecommentId,
            @RequestParam("recommentContent") String recommentContent,
            HttpSession httpSession
    ){
        ReviewRecommentDTO reviewRecommentDTO = new ReviewRecommentDTO();
        reviewRecommentDTO.setReviewCommentId(reviewRecommentId);
        reviewRecommentDTO.setRecommentContent(recommentContent);
        reviewRecommentDTO.setRrcWriterId(httpSession.getAttribute("id").toString());
        reviewDetailService.modifyReviewRecomment(reviewRecommentDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 리뷰 대댓글 삭제
     * @return
     */
    @DeleteMapping(value={"/recomment/{reviewRecommentId}"})
    public ResponseEntity removeReviewRecomment(
            @PathVariable int reviewRecommentId
    ){
        reviewDetailService.removeReviewRecomment(reviewRecommentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

