package com.example.boardpractice.board.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchDto {

    private String type;
    private String keyword;
    private Integer page = 1;
    private Integer size = 10; // 페이지 당 게시글 수
    private String sort;

}
