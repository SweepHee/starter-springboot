package com.example.boardpractice.board.model.dto;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Builder
@Data
public class PaginationDto {

    private int page;
    private int totalPage;
    private Long totalElement;
    private int currentPage;
    private int currentElement;

    private Boolean isFirst;
    private Boolean isLast;
    private Boolean hasPrevious;
    private Boolean hasNext;

    @Builder.Default
    private int pageLength = 5;
    private int size;

    private List<Integer> pageRanges = new ArrayList<Integer>();


    /* 페이지 범위 만들어주기 */
    private void calcPageRanges() {
        List<Integer> pageRange = new ArrayList<>();

        // 페이지네이션에서 0,1,2,3 페이지로 쓸 수 없으므로 +1해줬음
        int currentPage = this.currentPage+1;
        int pageLength = this.pageLength;
        pageRange.add(currentPage);
        while (pageLength > 0) {
            int prev = currentPage - pageLength;
            if (prev > 0) pageRange.add(prev);

            int next = currentPage + pageLength;
            if (next <= this.totalPage) pageRange.add(next);

            pageLength--;
        }
        Collections.sort(pageRange);
        this.pageRanges = pageRange;
    }

    public static PaginationDto pageToDto(Page page) {
        PaginationDto paginationDto =  PaginationDto.builder()
                .page(page.getPageable().getPageNumber())
                .totalPage(page.getTotalPages())
                .totalElement(page.getTotalElements())
                .currentPage(page.getPageable().getPageNumber())
                .currentElement(page.getNumberOfElements())
                .isFirst(page.isFirst())
                .isLast(page.isLast())
                .hasPrevious(page.hasPrevious())
                .hasNext(page.hasNext())
                .size(page.getPageable().getPageSize())
                .build();
        paginationDto.calcPageRanges();
        return paginationDto;

    }

    @Builder
    @Data
    public static class Total {

        private int totalPage;
        private Long totalElement;

    }


}
