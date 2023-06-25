package com.example.boardpractice.board.repository;

import com.example.boardpractice.board.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface BoardRepository extends JpaRepository<Board, Integer> {

    Page<Board> findAll(Pageable pageable);

    @Query(value = "select b from Board b left join b.user",
            countQuery = "select count(b) from Board b")
    Page<Board> findALlCustomQuery(Pageable pageable);

}
