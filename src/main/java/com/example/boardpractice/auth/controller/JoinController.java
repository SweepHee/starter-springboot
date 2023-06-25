package com.example.boardpractice.auth.controller;


import com.example.boardpractice.auth.entity.User;
import com.example.boardpractice.auth.repository.UserRepository;
import com.example.boardpractice.auth.service.MemberDetails;
import com.example.boardpractice.auth.service.MemberDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/join")
@RequiredArgsConstructor
public class JoinController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    private final MemberDetailsService memberDetailsService;

    @GetMapping
    public String index() {
        return "auth/join";
    }

    @PostMapping
    public String join(User user) throws Exception {

        if (memberDetailsService.loadUserByUsername(user.getUsername()) != null) {
            return "redirect:/join?error=2&username="+user.getUsername();
        }

        String rawPassword = user.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        user.setPassword(encPassword);
        user.setRole("ROLE_USER");

        userRepository.save(user);

        return "redirect:/";

    }

    /* 아이디 중복검사 AJAX */
    @GetMapping("/idDuplicateCheck")
    @ResponseBody
    public String idDuplicateCheck(String username) throws Exception {
        if (memberDetailsService.loadUserByUsername(username) != null) {
            throw new DuplicateKeyException("이미 존재하는 아이디입니다");
        }
        return "";
    }

}