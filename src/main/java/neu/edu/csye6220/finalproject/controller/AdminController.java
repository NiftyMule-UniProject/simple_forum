package neu.edu.csye6220.finalproject.controller;

import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.PostType;
import neu.edu.csye6220.finalproject.model.User;
import neu.edu.csye6220.finalproject.service.PostTypeService;
import neu.edu.csye6220.finalproject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController
{
    @Autowired
    UserService userService;

    @Autowired
    PostTypeService postTypeService;

    @GetMapping("/admin")
    public ModelAndView adminPage()
    {
        ModelAndView view = new ModelAndView("admin");
        List<PostType> postTypes = postTypeService.getAllTypes();
        List<User> admins = userService.getAllAdmins();
        List<User> superusers = userService.getAllSuperUsers();

        view.addObject("postTypes", postTypes);
        view.addObject("admins", admins);
        view.addObject("superusers", superusers);

        return view;
    }
}
