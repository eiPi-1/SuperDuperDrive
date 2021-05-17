package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller()
@RequestMapping("/signup")
public class SignupController {

    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String signupView() {
        return "signup";
    }

    @PostMapping()
    public String signupUser(@ModelAttribute User user, RedirectAttributes redirectAttributes) {
        String signupErrorMessage = null;

        if (!userService.isUsernameAvailable(user.getUsername())) {
            signupErrorMessage = "The username already exists.";
        }

        if (signupErrorMessage == null) {
            int rowsAdded = userService.createUser(user);
            if (rowsAdded < 0) {
                signupErrorMessage = "There was an error signing you up. Please try again.";
            }
        }

        if (signupErrorMessage == null) {
            redirectAttributes.addFlashAttribute("signupSuccess",true);
            redirectAttributes.addFlashAttribute("signupSuccessMessage", "New user was successfully created!");
            return "redirect:/login";
        } else {
            redirectAttributes.addFlashAttribute("signupError",true);
            redirectAttributes.addFlashAttribute("signupErrorMessage", "Error creating a new user!");
        }

        return "signup";
    }
}
