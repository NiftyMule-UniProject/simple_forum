package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService
{
    boolean register(String username, String password);

    boolean register(User user);
}
