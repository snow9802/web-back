package com.scsa.moin_back.review.controller;

import com.scsa.moin_back.member.config.SecurityUtil;
import com.scsa.moin_back.review.advice.ReviewExceptionHandler;
import com.scsa.moin_back.review.dto.ReviewCommentDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewRecommentDTO;
import com.scsa.moin_back.review.exception.FindReviewException;
import com.scsa.moin_back.review.exception.RemoveReviewException;
import com.scsa.moin_back.review.service.ReviewDetailService;
import com.scsa.moin_back.review.vo.ReviewCommentVO;
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
    private final SecurityUtil securityUtil;


     /** 리뷰 상세 페이지 이동
     * @param reviewId
     * @return
     */
    @GetMapping(value = {"/{reviewId}"})
    public ResponseEntity<ReviewDetailDTO> getReviewDetail(
            @PathVariable int reviewId
    ) throws FindReviewException {
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
    ) throws RemoveReviewException, FindReviewException {
        String id = securityUtil.getCurrentMemberId();
        if(id == null || "anonymousUser".equals(id)) {
            reviewExceptionHandler.checkLogin(httpSession);
        }
        // 접속자 id 권한 확인
        ReviewDetailDTO reviewDetail = reviewDetailService.getReviewDetail(reviewId);
        if (!id.equals(reviewDetail.getReviewWriterId())) {
            System.out.println("작성자가 사용자와 다릅니다.");
            return ResponseEntity.status(405).build();
        }

        reviewDetailService.removeReview(reviewId);
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
        String id = securityUtil.getCurrentMemberId();
        if(id == null || "anonymousUser".equals(id)){
            reviewExceptionHandler.checkLogin(httpSession);
        }
        ReviewCommentDTO reviewCommentDTO = new ReviewCommentDTO();
        reviewCommentDTO.setReviewId(reviewId);
        reviewCommentDTO.setCommentContent(commentContent);
        reviewCommentDTO.setRcWriterId(id);
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
            , HttpSession httpSession    ) throws FindReviewException {
        String id = securityUtil.getCurrentMemberId();
        if(id == null || "anonymousUser".equals(id)){
            reviewExceptionHandler.checkLogin(httpSession);
        }

        // 접속자 id 권한 확인
        ReviewCommentVO reviewCommentVO = reviewDetailService.getReviewCommentOne(reviewCommentId);

        System.out.println("접속자 id : " + id);
        System.out.println("작성자 id : " + reviewCommentVO.getId());

        if (!id.equals(reviewCommentVO.getId())) {
            System.out.println("작성자가 사용자와 다릅니다.");
            return ResponseEntity.status(405).build();
        }

        ReviewCommentDTO reviewCommentDTO = new ReviewCommentDTO();
        reviewCommentDTO.setReviewCommentId(reviewCommentId);
        reviewCommentDTO.setCommentContent(commentContent);
        reviewCommentDTO.setRcWriterId(id);
        reviewDetailService.modifyReviewComment(reviewCommentDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 리뷰 댓글 삭제
     * @return
     */
    @DeleteMapping(value={"/comment/{reviewCommentId}"})
    public ResponseEntity removeReviewComment(
            @PathVariable int reviewCommentId,
            HttpSession httpSession
    ) throws RemoveReviewException, FindReviewException {

        String id = securityUtil.getCurrentMemberId();
        if(id == null || "anonymousUser".equals(id)){
            reviewExceptionHandler.checkLogin(httpSession);
        }

        // 접속자 id 권한 확인
        ReviewCommentVO reviewCommentVO = reviewDetailService.getReviewCommentOne(reviewCommentId);

        System.out.println("접속자 id : " + id);
        System.out.println("작성자 id : " + reviewCommentVO.getId());

        if (!id.equals(reviewCommentVO.getId())) {
            System.out.println("작성자가 사용자와 다릅니다.");
            return ResponseEntity.status(405).build();
        }

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
        String id = securityUtil.getCurrentMemberId();
        if(id == null || "anonymousUser".equals(id)){
            reviewExceptionHandler.checkLogin(httpSession);
        }
        ReviewRecommentDTO reviewRecommentDTO = new ReviewRecommentDTO();
        reviewRecommentDTO.setReviewCommentId(reviewCommentId);
        reviewRecommentDTO.setRecommentContent(recommentContent);
        reviewRecommentDTO.setRrcWriterId(id);

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
        String id = securityUtil.getCurrentMemberId();
        if(id == null || "anonymousUser".equals(id)){
            reviewExceptionHandler.checkLogin(httpSession);
        }
        ReviewRecommentDTO reviewRecommentDTO = new ReviewRecommentDTO();
        reviewRecommentDTO.setReviewCommentId(reviewRecommentId);
        reviewRecommentDTO.setRecommentContent(recommentContent);
        reviewRecommentDTO.setRrcWriterId(id);
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
    ) throws RemoveReviewException {
        reviewDetailService.removeReviewRecomment(reviewRecommentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}

