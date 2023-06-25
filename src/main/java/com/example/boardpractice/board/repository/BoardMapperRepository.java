package com.example.boardpractice.board.repository;

import com.example.boardpractice.board.model.BoardMapperDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapperRepository {

    List<Object> findAll();

}
