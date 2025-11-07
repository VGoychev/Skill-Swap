package com.skillswapplatform.web;

import com.skillswapplatform.user.service.UserService;
import com.skillswapplatform.web.dto.RegisterRequest;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {
    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public ModelAndView getRegisterPage() {
        ModelAndView modelAndView = new ModelAndView("register");
        modelAndView.addObject("registerRequest", new RegisterRequest());
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@Valid RegisterRequest registerRequest, BindingResult bindingResult, org.springframework.ui.Model model) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("register");
        }
        try {
            userService.registerUser(registerRequest);
        } catch (IllegalArgumentException e) {
            // Surface service validation as a global error
            bindingResult.reject("registrationError", e.getMessage());
            return new ModelAndView("register");
        }
        return new ModelAndView("redirect:/login");
    }
}
