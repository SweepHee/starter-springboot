package com.example.boardpractice.board.model.dto;

import com.example.boardpractice.auth.entity.User;
import com.example.boardpractice.board.entity.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

public class BoardDto {


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Request {

        private Integer id;
        private String subject;
        private String content;
        private Integer userId;

    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Create {

        private int id;
        private String subject;
        private String content;
        private User user;
        private Timestamp createdAt;
        private Timestamp updatedAt;
        private Long view;

        public static Create EntityToDto(Board board) {
            return Create.builder()
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
