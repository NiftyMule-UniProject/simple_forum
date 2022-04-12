package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.User;

public interface UserService
{
    boolean register(String username, String password);

    boolean register(User user);
}
