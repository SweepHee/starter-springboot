package com.example.boardpractice.board.controller;

import com.example.boardpractice.auth.entity.User;
import com.example.boardpractice.auth.service.MemberDetails;
import com.example.boardpractice.board.entity.Board;
import com.example.boardpractice.board.model.BoardDto;
import com.example.boardpractice.board.model.BoardMapperDto;
import com.example.boardpractice.board.model.PaginationDto;
import com.example.boardpractice.board.service.BoardService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String index(Model model, @PageableDefault(size = 1, page = 0) Pageable pageable) {

        Page<Board> boards = boardService.get(pageable);
        Page<BoardDto.Create> boardsToMap = boards.map(board -> new BoardDto.Create().EntityToDto(board));
        PaginationDto pagination = PaginationDto.pageToDto(boards);

//        List<BoardMapperDto.Create> board2 = boardService.getAllByMapper();
//        board2.forEach(System.out::println);

        model.addAttribute("posts", boardsToMap);
        model.addAttribute("pagination", pagination);
        return "/board/index";

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
