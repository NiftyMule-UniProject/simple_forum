package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public boolean register(String username, String password)
    {
        User user = new User();
        user.setName(username);
        user.setPwd(password);
        return register(user);
    }

    @Override
    public boolean register(User user)
    {
        if (validateUsername(user.getName())
            && validatePassword(user.getPwd()))
        {
            user.setPwd(encoder.encode(user.getPwd()));
            userDao.add(user);
            return true;
        }
        return false;
    }

    private boolean validateUsername(String username)
    {
        if (username.length() < 6) return false;
        User user = userDao.getByUsername(username);
        return user == null;
    }

    private boolean validatePassword(String password)
    {
        if (password.length() < 6) return false;
        return password.matches("^[a-zA-Z0-9]{6,}$");
    }
}
