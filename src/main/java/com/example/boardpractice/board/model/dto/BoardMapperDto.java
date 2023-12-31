package com.example.boardpractice.board.model.dto;

import com.example.boardpractice.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardMapperDto {

    private int boardId;
    private String subject;
    private String content;
    private int userId;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private Long view;


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {

        private int boardId;
        private String subject;
        private String content;
        private int userId;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private Long view;

        public static BoardDto.Create EntityToDto(Board board) {
            return BoardDto.Create.builder()
                    .id(board.getId())
                    .subject(board.getSubject())
                    .content(board.getContent())
                    .user(board.getUser())
                    .createdAt(board.getCreatedAt())
                    .updatedAt(board.getUpdatedAt())
                    .view(board.getView())
                    .build();
        }
    }


}
