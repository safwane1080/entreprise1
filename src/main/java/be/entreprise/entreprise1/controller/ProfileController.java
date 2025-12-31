package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.security.Principal;

@Controller
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        model.addAttribute("user", user);
        return "profile";
    }
    @PostMapping("/admin/change-role")
    @PreAuthorize("hasRole('ADMIN')")
    public String changeUserRole(
            @RequestParam Long userId,
            @RequestParam String role
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        user.setRole(role);
        userRepository.save(user);

        return "redirect:/profile";
    }

}
