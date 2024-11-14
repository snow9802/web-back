package com.scsa.moin_back.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor  @AllArgsConstructor
@Setter
@Getter
public class PageDTO<T> {
    private List<T> list;
    private int currentPage; //현재페이지
    private int totalPages; //총페이지수

    private int startPage; //페이지그룹의 시작페이지
    private int endPage;//페이지그룹의 끝페이지
    private boolean hasPrevious; //이전페이지 여부
    private boolean hasNext; //다음페이지 여부

    private int pageSize;
    private int pageGroupSize;

    public PageDTO(int pageSize, int pageGroupSize, int currentPage, int totalCnt, List<T> list)  {
        this.list = list;
        this.pageSize = pageSize;
        this.pageGroupSize = pageGroupSize;
        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) totalCnt / pageSize);//총페이지


        this.startPage = (currentPage-1)/pageGroupSize*pageGroupSize+1;//페이지그룹시작페이지
        this.endPage = startPage + pageGroupSize -1 ;//페이지그룹끝페이지
        if(endPage > totalPages){
            endPage = totalPages;
        }
        this.hasPrevious = startPage>1; //prev여부
        this.hasNext = totalPages>endPage;  //next여부
    }
}
