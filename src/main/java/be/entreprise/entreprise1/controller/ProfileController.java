package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
public class ProfileController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ProfileController(UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/profile")
    public String profile(Principal principal, Model model) {

        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        model.addAttribute("user", user);
        return "profile";
    }

    // ===== NAAM AANPASSEN =====
    @PostMapping("/profile/update")
    public String updateProfile(
            Principal principal,
            @RequestParam String firstName,
            @RequestParam String lastName
    ) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        user.setFirstName(firstName);
        user.setLastName(lastName);
        userRepository.save(user);

        return "redirect:/profile?updated=true";
    }

    // ===== WACHTWOORD AANPASSEN =====
    @PostMapping("/profile/change-password")
    public String changePassword(
            Principal principal,
            @RequestParam String currentPassword,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword
    ) {
        User user = userRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User niet gevonden"));

        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            return "redirect:/profile?error=wrongPassword";
        }

        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/profile?error=passwordMismatch";
        }

        if (!isStrongPassword(newPassword)) {
            return "redirect:/profile?error=weakPassword";
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return "redirect:/profile?passwordChanged=true";
    }

    private boolean isStrongPassword(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasDigit = password.matches(".*[0-9].*");

        return hasUpper && hasLower && hasDigit;
    }

    // ===== ADMIN ROLE CHANGE =====
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
