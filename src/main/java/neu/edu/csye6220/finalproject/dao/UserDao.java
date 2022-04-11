package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.User;

public interface UserDao
{
    void add(User user);

    void delete(Long id);

    User get(Long id);

    void update(User user);

    User getByUsername(String username);
}
