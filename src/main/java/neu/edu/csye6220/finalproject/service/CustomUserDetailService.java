package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.AdminDao;
import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.Admin;
import neu.edu.csye6220.finalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class CustomUserDetailService implements UserDetailsService
{
    public final static int SUPERUSER_LEVEL = 1;
    @Autowired
    UserDao userDao;

    @Autowired
    AdminDao adminDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User user = userDao.getByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("No user found with username: " + username);

        boolean enabled = true;
        boolean accountNonExpired = true;
        boolean credentialsNonExpired = true;
        boolean accountNonLocked = true;
        List<GrantedAuthority> authorities = new ArrayList<>();
        Admin admin = adminDao.getByUserId(user.getId());
        if (admin != null)
        {
            authorities.add(new SimpleGrantedAuthority("admin"));
            if (admin.getLevel() == SUPERUSER_LEVEL)
                authorities.add(new SimpleGrantedAuthority("superuser"));
        }

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPwd(),
                enabled,
                accountNonExpired,
                credentialsNonExpired,
                accountNonLocked,
                authorities
        );
    }
}
