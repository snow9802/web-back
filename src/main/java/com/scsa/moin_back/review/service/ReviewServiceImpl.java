package com.scsa.moin_back.review.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.group.dto.GroupDTO;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewSearchDTO;
import com.scsa.moin_back.review.exception.AddReviewException;
import com.scsa.moin_back.review.mapper.ReviewDetailMapper;
import com.scsa.moin_back.review.mapper.ReviewMainMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    final private ReviewMainMapper reviewMainMapper;
    private final ReviewDetailMapper reviewDetailMapper;

    @Override
    public PageDTO<ReviewDTO> getReviewList(Map<String, Object> map, int currentPage, int pageSize) {

        int startRow = (currentPage - 1) * pageSize + 1;
        int endRow = currentPage * pageSize;
        map.put("startRow", startRow);
        map.put("endRow", endRow);

        List<ReviewDTO> list = reviewMainMapper.getReviewList(map);
        int totalCnt = reviewMainMapper.getReviewListCnt(map);

        int pageGroupSize = 5; //페이지그룹의 페이지 수 ex)현재페이지 1인경우 1 2 3
        return new PageDTO<ReviewDTO>(pageSize, pageGroupSize, currentPage, totalCnt, list);
    }

    @Override
    public PageDTO<GroupDTO> getReviewGroup(String id, int cp, int ps) {
        return null;
    }

    @Override
    public void chkValidGroup(String id, int groupId) {

    }

    @Override
    @Transactional(rollbackFor = AddReviewException.class)
    public void addReview(ReviewDTO reviewDTO) throws AddReviewException {
        ReviewDTO reviewDto = new ReviewDTO();
        try {

        } catch (Exception e) {
            if (e instanceof SQLException) {
            }
            throw new AddReviewException(e.getMessage());
        }
    }

    @Override
    public ReviewDTO chkValidReview(String id, int reviewId) {
        return null;
    }

    @Override
    public void modifyReview(ReviewDTO reviewDTO) {

    }

    @Override
    public Map<String, Object> convertToMap(Object obj) throws Exception {
        Map<String, Object> resultMap = new HashMap<>();

        // 객체의 모든 필드를 가져와서 Map에 담기
        for (Field field : obj.getClass().getDeclaredFields()) {
            // 필드 이름을 기반으로 getter 메서드 찾기
            String fieldName = field.getName();
            // 필드명에 해당하는 getter 메서드 이름을 생성 (예: field -> getField)
            String getterMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method getterMethod = obj.getClass().getMethod(getterMethodName);

            // getter 메서드를 호출하여 값을 얻고 Map에 추가
            Object fieldValue = getterMethod.invoke(obj);
            resultMap.put(fieldName, fieldValue);

        }

        return resultMap;
    }


}
