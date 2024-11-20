package com.scsa.moin_back.review.mapper;

import com.scsa.moin_back.review.dto.ReviewDTO;
import com.scsa.moin_back.review.dto.ReviewGroupDTO;
import com.scsa.moin_back.review.vo.ReviewImgVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Mapper
public interface ReviewMainMapper {
    List<ReviewDTO> getReviewList(Map<String,Object> searchParam);

    int getReviewListCnt(Map<String,Object> searchParam);

    List<ReviewGroupDTO> getReviewGroup(HashMap<String, Object> map);

    int getReviewGroupCnt(HashMap<String, Object> map);

    void insertReview(ReviewDTO reviewDTO);

    void insertReviewImgs(ReviewImgVO reviewImg);

    void updateReview(ReviewDTO reviewDTO);

    List<ReviewDTO> getMyReviewList(HashMap<String, Object> map);

    int getMyReviewListCnt(HashMap<String, Object> map);

    int chkDupReview(ReviewDTO reviewDTO);

    String getReviewGroupName(int groupId);
}
