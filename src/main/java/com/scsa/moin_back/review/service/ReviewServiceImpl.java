package com.scsa.moin_back.review.service;

import com.scsa.moin_back.common.dto.PageDTO;
import com.scsa.moin_back.common.service.FileUploader;
import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewDetailDTO;
import com.scsa.moin_back.review.dto.ReviewGroupDTO;
import com.scsa.moin_back.review.dto.ReviewImgDTO;
import com.scsa.moin_back.review.exception.AddReviewException;
import com.scsa.moin_back.review.exception.ModifyReviewException;
import com.scsa.moin_back.review.mapper.ReviewDetailMapper;
import com.scsa.moin_back.review.mapper.ReviewMainMapper;
import com.scsa.moin_back.review.vo.ReviewImgVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewServiceImpl implements ReviewService {

    final private ReviewMainMapper reviewMainMapper;
    private final ReviewDetailMapper reviewDetailMapper;
    private final FileUploader fileUploader;

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
    public PageDTO<ReviewGroupDTO> getReviewGroup(String id, int currentPage, int pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        int startRow = (currentPage - 1) * pageSize + 1;
        int endRow = currentPage * pageSize;
        int pageGroupSize = 5; //페이지그룹의 페이지 수 ex)현재페이지 1인경우 1 2 3

        map.put("startRow", startRow);
        map.put("endRow", endRow);
        map.put("id", id);
        List<ReviewGroupDTO> list = reviewMainMapper.getReviewGroup(map);
        int totalCnt = reviewMainMapper.getReviewGroupCnt(map);

        return new PageDTO<ReviewGroupDTO>(pageSize, pageGroupSize, currentPage, totalCnt, list);
    }

    @Override
    public String chkValidGroup(String id, int groupId) {
        return reviewMainMapper.getReviewGroupName(groupId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public ResponseEntity<Object> addReview(ReviewDTO reviewDTO, MultipartFile reviewImg) throws AddReviewException {
        /* 리뷰DTO 유효성검사 */
        if (reviewDTO.getReviewContent() == null ||
                reviewDTO.getReviewTitle() == null ||
                reviewDTO.getReviewGroupId() == 0 ||
                reviewDTO.getId() == null ||
                reviewMainMapper.chkDupReview(reviewDTO) > 0) {
            throw new AddReviewException("유효하지 않은 데이터 혹은 중복된 데이터가 입력되었습니다");
        }

        String fileUrl = "default url";
        /* img 파일 업로드 */
        if (reviewImg != null){
            try{
                fileUrl = fileUploader.uploadFile(reviewImg);
            } catch (Exception e){
                return ResponseEntity.status(400).build(); // 파일 업로드 실패
            }
        }

        reviewDTO.setReviewImgUrl(fileUrl);

        try {
            reviewMainMapper.insertReview(reviewDTO);
        } catch (Exception e) {
            throw new AddReviewException(e.getMessage());
        }

        return ResponseEntity.ok().build();
    }

    @Override
    @Transactional(rollbackFor = ModifyReviewException.class)
    public void modifyReview(ReviewDTO reviewDTO) throws ModifyReviewException {
        ReviewDTO reviewDto = new ReviewDTO();
        try {
            reviewMainMapper.updateReview(reviewDTO);
        } catch (Exception e) {
            throw new ModifyReviewException("리뷰  수정 중 에러가 발생했습니다");
        }
    }

    @Override
    public PageDTO<ReviewDTO> getMyPageReviewList(String id, Integer currentPage, Integer pageSize) {
        HashMap<String, Object> map = new HashMap<>();
        int startRow = (currentPage - 1) * pageSize + 1;
        int endRow = currentPage * pageSize;
        map.put("startRow", startRow);
        map.put("endRow", endRow);
        map.put("id", id);
        List<ReviewDTO> list = reviewMainMapper.getMyReviewList(map);
        int totalCnt = reviewMainMapper.getMyReviewListCnt(map);

        int pageGroupSize = 5; //페이지그룹의 페이지 수 ex)현재페이지 1인경우 1 2 3
        return new PageDTO<ReviewDTO>(pageSize, pageGroupSize, currentPage, totalCnt, list);

    }

    @Override
    public ReviewDetailDTO getReviewModify(String id, int reviewId) {
        ReviewDetailDTO reviewDetailDTO = reviewDetailMapper.getReviewDetail(reviewId);
        List<ReviewImgDTO> reviewImgList = reviewDetailMapper.getReviewImages(reviewId);
        reviewDetailDTO.setReviewImgList(reviewImgList);
        return reviewDetailDTO;
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
