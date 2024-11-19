package com.scsa.moin_back.review.advice;

import com.scsa.moin_back.review.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ReviewExceptionHandler {

    /**
     * 추가오류
     * @param e
     * @return
     */
    @ExceptionHandler(AddReviewException.class)
    public ResponseEntity<Map<String, String>> handleAddException(AddReviewException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "INTERNAL_SERVER_ERROR");
        response.put("message", "리뷰 데이터 등록 중 에러가 발생했습니다. 등록이 취소되었습니다.\n"+e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * 조회오류
     * @param e
     * @return
     */
    @ExceptionHandler(FindReviewException.class)
    public ResponseEntity<Map<String, String>> handleFindException(FindReviewException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "BAD_REQUEST");
        response.put("message", "리뷰 데이터를 가져오는데 문제가 발생했습니다.\n"+e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 수정오류
     * @param e
     * @return
     */
    @ExceptionHandler(ModifyReviewException.class)
    public ResponseEntity<Map<String, String>> handleFindException(ModifyReviewException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "BAD_REQUEST");
        response.put("message", "리뷰 데이터 수정 중 문제가 발생했습니다. 수정이 취소되었습니다.\n"+e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    /**
     * 삭제오류
     * @param e
     * @return
     */
    @ExceptionHandler(RemoveReviewException.class)
    public ResponseEntity<Map<String, String>> handleFindException(RemoveReviewException e) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "BAD_REQUEST");
        response.put("message", "리뷰 데이터 삭제 중 문제가 발생했습니다. 삭제가 취소되었습니다.\n"+e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    /**
     * 권한 없는 사용자
     * @param ex
     * @return
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUnauthorizedException(UnauthorizedException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Unauthorized");
        response.put("message", ex.getMessage());

        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

    /**
     * 권한없는 사용자
     * @param session
     */
    public void checkLogin(HttpSession session) {
        if (session.getAttribute("id") == null) {
            throw new UnauthorizedException("로그인 하지 않은 사용자입니다. 로그인 페이지로 이동합니다.");
        }
    }


}
