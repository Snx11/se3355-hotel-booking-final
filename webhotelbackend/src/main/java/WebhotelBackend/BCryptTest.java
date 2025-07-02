package WebhotelBackend;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptTest {
    public static void main(String[] args) {
        String raw = "1234";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashed = encoder.encode(raw);
        System.out.println("Hashlenmiş şifre: " + hashed);
    }
}
