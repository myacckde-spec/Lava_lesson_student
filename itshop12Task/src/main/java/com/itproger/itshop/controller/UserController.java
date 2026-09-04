package com.itproger.itshop.controller;

import com.itproger.itshop.entity.User;
import com.itproger.itshop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public String userCabinet(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
        
        model.addAttribute("user", user);
        model.addAttribute("roles", User.Role.values());
        return "user/cabinet";
    }

    @PostMapping("/user/update")
    public String updateUser(Principal principal,
                            @RequestParam String username,
                            @RequestParam String email,
                            @RequestParam(required = false) String password,
                            @RequestParam User.Role role,
                            RedirectAttributes redirectAttributes) {
        try {
            User user = userService.findByUsername(principal.getName())
                    .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));
            
            userService.updateUser(user.getId(), username, email, password, role);
            redirectAttributes.addFlashAttribute("success", "Данные обновлены успешно!");
            return "redirect:/user";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/user";
        }
    }
}
