package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.User;

import java.util.List;

public interface UserService
{
    User getUserByUsername(String username);

    List<User> searchUsersByKeyword(String keyword);

    boolean register(String username, String password);

    boolean register(User user);

    List<User> getAllAdmins();

    List<User> getAllSuperUsers();

    String addAdmin(long userId, int level);

    String deleteAdmin(long userId);
}
