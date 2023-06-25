package com.example.boardpractice.auth.entity;

import com.example.boardpractice.board.entity.Board;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer id;

    @Column(unique = true)
    private String username;
    private String password;
    private String email;
    private String role;

    @CreationTimestamp
    private Timestamp createdAt;

    @OneToMany(mappedBy="user", cascade = CascadeType.ALL)
    private List<Board> board;

    public User(Integer id) {
        this.id = id;
    }


}
