package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.AdminDao;
import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.Admin;
import neu.edu.csye6220.finalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    AdminDao adminDao;

    @Override
    public User getUserByUsername(String username)
    {
        return userDao.getByUsername(username);
    }

    @Override
    public List<User> searchUsersByKeyword(String keyword)
    {
        return userDao.searchUsersByKeyword(keyword);
    }

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

    @Override
    public List<User> getAllAdmins()
    {
        List<Admin> adminList = adminDao.list();
        List<Long> userIds = new ArrayList<>();
        for (Admin admin : adminList)
            userIds.add(admin.getUserId());

        return userDao.getUserListByIds(userIds);
    }

    @Override
    public List<User> getAllSuperUsers()
    {
        List<Admin> adminList = adminDao.list();
        List<Long> userIds = new ArrayList<>();
        for (Admin admin : adminList)
            if (admin.getLevel() == CustomUserDetailService.SUPERUSER_LEVEL) userIds.add(admin.getUserId());

        return userDao.getUserListByIds(userIds);
    }

    @Override
    public String addAdmin(long userId, int level)
    {
        User user = userDao.get(userId);
        if (user == null) return "User does not exist!";

        Admin admin = adminDao.getByUserId(userId);
        if (admin != null)
        {
            admin.setLevel(level);
            adminDao.update(admin);

            return null;
        }
        else
        {
            Admin newAdmin = new Admin();
            newAdmin.setUserId(userId);
            newAdmin.setLevel(level);
            adminDao.add(newAdmin);

            return null;
        }
    }

    @Override
    public String deleteAdmin(long userId)
    {
        User user = userDao.get(userId);
        if (user == null) return "User does not exist!";

        Admin admin = adminDao.getByUserId(userId);
        if (admin == null) return "User is not admin!";

        adminDao.delete(admin.getId());
        return null;
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
