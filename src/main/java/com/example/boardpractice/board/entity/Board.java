package com.example.boardpractice.board.entity;

import com.example.boardpractice.auth.entity.User;
import com.example.boardpractice.board.model.BoardDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private int id;

    @Column(nullable = false)
    private String subject;

    @Column(nullable = false)
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp createdAt;
    @UpdateTimestamp
    private Timestamp updatedAt;

    private Long view;

    @Builder
    public Board(String subject, String content, User user) {
        this.subject = subject;
        this.content = content;
        this.user = user;
    }

    public void update(BoardDto.Request dto) {
        this.subject = dto.getSubject();
        this.content = dto.getContent();
    }

    public BoardDto.Create EntityToDto(Board board) {

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
