package com.example.boardpractice.controller;

import com.example.boardpractice.auth.service.MemberDetails;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;


@Controller
public class MainController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/isLoggedIn")
    public String isLoggedIn(Authentication authentication, Principal principal) {
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        System.out.println("=========userdetails=========");
        System.out.println(memberDetails);
        System.out.println(memberDetails.getUsername());
        System.out.println(memberDetails.getId());
        System.out.println("=========userdetails=========");
        System.out.println("=========userdetails=========");

        System.out.println(principal.getName());

        return "index";
    }

}
