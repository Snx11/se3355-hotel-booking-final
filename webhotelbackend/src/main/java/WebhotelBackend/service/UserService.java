package WebhotelBackend.service;
import WebhotelBackend.model.User;
import java.util.List;

public interface UserService {
    User saveUser(User user);
    List<User> getAllUsers();
}