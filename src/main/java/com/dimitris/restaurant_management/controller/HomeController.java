package com.dimitris.restaurant_management.controller;

import com.dimitris.restaurant_management.entities.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal User user, Model model, HttpSession session) {
        if (session.getAttribute("username") == null) {
            session.setAttribute("username", user.getUsername());
        }
        model.addAttribute("message",user.getId() + " " + session.getAttribute("username"));
        return "hello";
    }
}
