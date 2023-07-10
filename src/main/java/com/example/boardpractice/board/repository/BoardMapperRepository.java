package com.example.boardpractice.board.repository;

import com.example.boardpractice.board.model.dto.BoardMapperDto;
import com.example.boardpractice.board.model.dto.PaginationDto;
import com.example.boardpractice.board.model.dto.SearchDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapperRepository {

    List<BoardMapperDto.Create> findAll();

    PaginationDto.Total paginate(SearchDto searchDto);

    List<BoardMapperDto.Create> findBySearchDto(SearchDto searchDto);
}
