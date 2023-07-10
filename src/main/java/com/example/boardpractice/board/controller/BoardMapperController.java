package com.example.boardpractice.board.controller;

import com.example.boardpractice.board.model.dto.BoardMapperDto;
import com.example.boardpractice.board.model.dto.PaginationDto;
import com.example.boardpractice.board.model.dto.SearchDto;
import com.example.boardpractice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/mapper/board")
@RequiredArgsConstructor
public class BoardMapperController {

    private final BoardService boardService;

    @GetMapping
    public String mapperIndex(Model model, SearchDto searchDto) {

        List<BoardMapperDto.Create> board = boardService.findBySearchDto(searchDto);
        PaginationDto pagination = boardService.paginate(searchDto);

        model.addAttribute("posts", board);
        model.addAttribute("pagination", pagination);
        return "/board/index2";
    }

}
