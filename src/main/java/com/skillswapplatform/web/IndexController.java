package com.skillswapplatform.web;

import com.skillswapplatform.user.service.UserService;
import com.skillswapplatform.utils.MessageUtil;
import com.skillswapplatform.web.dto.LoginRequest;
import com.skillswapplatform.web.dto.RegisterRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public ModelAndView getLoginPage(@RequestParam(name = "loginAttemptMessage", required = false) String message,
                                     @RequestParam(name = "error", required = false) String errorMessage, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("loginRequest", new LoginRequest());
        MessageUtil.addLoginMessages(modelAndView, session, message, errorMessage);

        return modelAndView;
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
            bindingResult.reject("registrationError", e.getMessage());
            return new ModelAndView("register");
        }
        return new ModelAndView("redirect:/login");
    }

    @GetMapping("/home")
    public String getHomePage() {
        return "home";
    }
}
