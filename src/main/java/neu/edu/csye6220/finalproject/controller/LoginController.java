package neu.edu.csye6220.finalproject.controller;

import neu.edu.csye6220.finalproject.service.UserService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController
{
    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String loginPage()
    {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage()
    {
        return "register";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(
            String username,
            String password,
            String password_confirm)
    {
        ModelAndView ret = new ModelAndView();
        if (!password.equals(password_confirm))
        {
            ret.setViewName("register");
            ret.addObject("err_message", "Password confirmation is incorrect!");
        }
        if (!userService.register(username, password))
        {
            ret.setViewName("register");
            ret.addObject("err_message", "Account registration failed!" +
                            " Please check again your username & password, or change a username");
        }
        else
        {
            ret.setViewName("redirect://");
        }
        return ret;
    }
}
