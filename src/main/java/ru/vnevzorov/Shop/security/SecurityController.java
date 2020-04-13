package ru.vnevzorov.Shop.security;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String showLoginPage() {
        return "jsp/login.jsp";
    }

}
