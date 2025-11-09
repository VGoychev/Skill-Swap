package com.skillswapplatform.utils;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

public class MessageUtil {

    private MessageUtil() {} // dont be able to instantiate

    /**
     * Adds login-related messages to the ModelAndView.
     */
    public static void addLoginMessages(ModelAndView modelAndView,
                                        HttpSession session,
                                        String message,
                                        String errorMessage) {
        modelAndView.addObject("loginAttemptMessage", message);
        String inactiveUserMessage = (String) session.getAttribute("inactiveUserMessage");

        if (inactiveUserMessage != null) {
            modelAndView.addObject("inactiveAccountMessage", inactiveUserMessage);
            session.removeAttribute("inactiveUserMessage"); // clear after showing
        } else if (errorMessage != null) {
            modelAndView.addObject("errorMessage", "Invalid username or password");
        }
    }
}
