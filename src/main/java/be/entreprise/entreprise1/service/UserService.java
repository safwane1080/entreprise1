package be.entreprise.entreprise1.service;

import be.entreprise.entreprise1.model.User;
import be.entreprise.entreprise1.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getDefaultUser() {

        return userRepository.findByEmail("demo@ehb.be")
                .orElseGet(() -> {
                    User user = new User();
                    user.setEmail("demo@ehb.be");
                    user.setPassword("demo");   // ⬅ gebruik bestaand veld
                    user.setRole("USER");
                    return userRepository.save(user);
                });
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
