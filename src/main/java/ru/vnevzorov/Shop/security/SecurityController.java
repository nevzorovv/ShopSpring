package ru.vnevzorov.Shop.security;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class SecurityController {

    @GetMapping("/login")
    public String showLoginPage(@RequestParam(value = "error", required = false) String error,
                                @RequestParam(value = "logout", required = false) String logout,
                                @RequestParam(value = "regsucc", required = false) String regsucc,
                                @RequestParam(value = "passSucChanged", required = false) String passSucChanged,
                                Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        model.addAttribute("regsucc", regsucc != null);
        model.addAttribute("passSucChanged", passSucChanged != null);

        return "jsp/login.jsp";
    }

}
