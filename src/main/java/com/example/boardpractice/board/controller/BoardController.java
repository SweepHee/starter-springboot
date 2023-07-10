package com.example.boardpractice.board.controller;

import com.example.boardpractice.auth.service.MemberDetails;
import com.example.boardpractice.board.entity.Board;
import com.example.boardpractice.board.model.dto.BoardDto;
import com.example.boardpractice.board.model.dto.BoardMapperDto;
import com.example.boardpractice.board.model.dto.PaginationDto;
import com.example.boardpractice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String index(Model model, @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<Board> boards = boardService.get(pageable);
        System.out.println(boards);
        Page<BoardDto.Create> boardsToMap = boards.map(board -> new BoardDto.Create().EntityToDto(board));
        PaginationDto pagination = PaginationDto.pageToDto(boards);

        model.addAttribute("posts", boardsToMap);
        model.addAttribute("pagination", pagination);
        return "/board/index";

    }

    @GetMapping("/index2")
    public String index2(Model model, @PageableDefault(size = 10, page = 0) Pageable pageable) {

        Page<Board> boards = boardService.get(pageable);
        Page<BoardDto.Create> boardsToMap = boards.map(board -> new BoardDto.Create().EntityToDto(board));
        PaginationDto pagination = PaginationDto.pageToDto(boards);

        model.addAttribute("posts", boardsToMap);
        model.addAttribute("pagination", pagination);
        return "/board/index2";

    }

    @ResponseBody
    @GetMapping("/dataSend")
    public HashMap<String, Object> dataSend(@PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<Board> boards = boardService.get(pageable);
        Page<BoardDto.Create> boardsToMap = boards.map(board -> new BoardDto.Create().EntityToDto(board));
        PaginationDto pagination = PaginationDto.pageToDto(boards);

        HashMap<String, Object> map = new HashMap<>();
        map.put("posts", boardsToMap);
        map.put("pagination", pagination);
        return map;
    }


    @GetMapping("/ajaxIndex")
    public String ajaxIndex() {
        return "/board/ajaxIndex";
    }

    @GetMapping("/list")
    public String listAjax(Model model, @PageableDefault(size = 10, page = 0) Pageable pageable) {
        Page<Board> boards = boardService.get(pageable);
        Page<BoardDto.Create> boardsToMap = boards.map(board -> new BoardDto.Create().EntityToDto(board));
        PaginationDto pagination = PaginationDto.pageToDto(boards);
        model.addAttribute("posts", boardsToMap);
        model.addAttribute("pagination", pagination);
        return "/board/ajaxBoardData";
    }



    @GetMapping("/create")
    public String create() {
        return "/board/create";
    }

    @PostMapping
    public String store(BoardDto.Request boardDto) {
        Board board = boardService.create(boardDto);
        return "redirect:/board/"+board.getId();
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        Board board = boardService.findById(id);
        BoardDto.Create boardDto = BoardDto.Create.EntityToDto(board);
        model.addAttribute("board", boardDto);
        return "/board/show";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model, Authentication authentication) throws AccessDeniedException {
        Board board = boardService.findById(id);
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        if (memberDetails.getId() != board.getUser().getId()) {
            throw new AccessDeniedException("권한없음");
        }
        BoardDto.Create boardDto = BoardDto.Create.EntityToDto(board);
        model.addAttribute("board", boardDto);
        return "/board/create";
    }

    @PutMapping("/{id}")
    public String update(BoardDto.Request boardDto, @PathVariable("id") int id, Authentication authentication)
            throws AccessDeniedException {
        Board board = boardService.findById(id);
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        if (memberDetails.getId() != board.getUser().getId()) {
            throw new AccessDeniedException("권한없음");
        }
        boardService.create(boardDto);

        return "redirect:/board/{id}";
    }

    @DeleteMapping("/delete/{id}")
    public String destroy(@PathVariable("id") int id, Authentication authentication)
            throws AccessDeniedException {
        Board board = boardService.findById(id);
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        if (memberDetails.getId() != board.getUser().getId()) {
            throw new AccessDeniedException("권한없음");
        }
        boardService.deleteById(id);
        return "redirect:/board";
    }


}
