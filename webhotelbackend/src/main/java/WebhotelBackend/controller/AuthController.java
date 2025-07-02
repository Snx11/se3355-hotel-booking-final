package WebhotelBackend.controller;

import WebhotelBackend.dto.LoginRequest;
import WebhotelBackend.model.User;
import WebhotelBackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * POST /api/auth/login
     * Body: { "email": "...", "password": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                // Token yok, sadece kullanıcıyı döneriz
                return ResponseEntity.ok(user);  // Ad, soyad, e-posta döner
            } else {
                return ResponseEntity.status(401).body("Hatalı şifre!");
            }
        } else {
            return ResponseEntity.status(401).body("Kullanıcı bulunamadı!");
        }
    }

}
