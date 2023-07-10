package com.example.boardpractice.board.service;

import com.example.boardpractice.auth.entity.User;
import com.example.boardpractice.board.entity.Board;
import com.example.boardpractice.board.model.dto.BoardDto;
import com.example.boardpractice.board.model.dto.BoardMapperDto;
import com.example.boardpractice.board.model.dto.PaginationDto;
import com.example.boardpractice.board.model.dto.SearchDto;
import com.example.boardpractice.board.repository.BoardMapperRepository;
import com.example.boardpractice.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapperRepository boardMapperRepository;


    public Page<Board> get(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(),
                pageable.getPageSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        return boardRepository.findAll(pageRequest);
    }

    public List<BoardMapperDto.Create> getAllByMapper() {
        return boardMapperRepository.findAll();
    }

    public List<BoardMapperDto.Create> findBySearchDto(SearchDto searchDto) {
        return boardMapperRepository.findBySearchDto(searchDto);
    }

    public PaginationDto paginate(SearchDto searchDto) {
        PaginationDto.Total total =  boardMapperRepository.paginate(searchDto);
        return PaginationDto.builder()
                .page(searchDto.getPage())
                .totalPage(total.getTotalPage())
                .totalElement(total.getTotalElement())
                .currentPage(searchDto.getPage())
                .isFirst(searchDto.getPage() == 1)
                .isLast(total.getTotalPage() == searchDto.getPage())
                .hasPrevious(searchDto.getPage() > 1)
                .hasNext(searchDto.getPage() < total.getTotalPage())
                .size(10)
                .build();
    }

    @Transactional
    public Board create(BoardDto.Request boardDto) {

        if (boardDto.getId() == null) {
            User user = new User(boardDto.getUserId());
            Board board = Board.builder().subject(boardDto.getSubject())
                    .content(boardDto.getContent())
                    .user(user)
                    .build();
            boardRepository.save(board);
            return board;
        }

        Optional<Board> boardOptional = boardRepository.findById(boardDto.getId());
        Board board = boardOptional.orElseThrow(NoSuchElementException::new);
        board.update(boardDto);
        return board;
    }

    public Board findById(int id) {
        Optional<Board> board = boardRepository.findById(id);
        return board.orElseThrow(NoSuchElementException::new);
    }

    public void deleteById(int id) {
        boardRepository.deleteById(id);
    }
}
