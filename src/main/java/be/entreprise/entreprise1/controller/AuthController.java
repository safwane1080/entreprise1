package be.entreprise.entreprise1.controller;

import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 🔐 min 8 chars, 1 hoofdletter, 1 cijfer
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[A-Z])(?=.*\\d).{8,}$");

    public AuthController(UserRepository userRepository,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    @PostMapping("/register")
    public String register(
            @RequestParam String email,
            @RequestParam String password,
            @RequestParam String confirmPassword,
            Model model
    ) {

        // 1️⃣ Email bestaat al
        if (userRepository.findByEmail(email).isPresent()) {
            model.addAttribute("error", "Deze email bestaat al.");
            return "register";
        }

        // 2️⃣ Wachtwoorden gelijk?
        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Wachtwoorden komen niet overeen.");
            return "register";
        }

        // 3️⃣ Wachtwoord sterkte
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            model.addAttribute("error",
                    "Wachtwoord moet minstens 8 tekens bevatten, "
                            + "1 hoofdletter en 1 cijfer.");
            return "register";
        }

        // 4️⃣ User opslaan
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole("USER");

        userRepository.save(user);

        return "redirect:/login";
    }
}
