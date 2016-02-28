package be.kdg.kandoe.backend.service.api;

import be.kdg.kandoe.backend.model.users.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    User addUser(User user);
    User getUserByUsername(String username);
    User getUserByUserId(int userId);
    User updateUser(User user);
    void checkLogin(int username, String verifyPassword);
    void isUsernameAvailable(String username);
}
