package neu.edu.csye6220.finalproject.controller.API;

import com.fasterxml.jackson.databind.ser.std.JsonValueSerializer;
import com.google.gson.Gson;
import com.mysql.cj.xdevapi.JsonArray;
import neu.edu.csye6220.finalproject.model.User;
import neu.edu.csye6220.finalproject.service.UserService;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AdminApiController
{
    public final static int NON_SUPERUSER_LEVEL = 2;
    @Autowired
    UserService userService;

    @PostMapping("/api/admin/{username}")
    public String addAdmin(
            @PathVariable("username") String username,
            HttpServletResponse response
    )
    {
        User user = userService.getUserByUsername(username);
        String errMsg;
        if (user != null)
            errMsg = userService.addAdmin(user.getId(), NON_SUPERUSER_LEVEL);
        else
            errMsg = "User does not exist!";

        if (errMsg != null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return errMsg;
        }

        return "success!";
    }

    @DeleteMapping("/api/admin/{userId}")
    public String deleteAdmin(
            @PathVariable("userId") long userId,
            HttpServletResponse response
    )
    {
        String errMsg = userService.deleteAdmin(userId);
        if (errMsg != null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return errMsg;
        }

        return "success!";
    }

    @GetMapping("/api/admin/users")
    public String listUserByKeyword(
            @RequestParam("keyword") String keyword,
            HttpServletResponse response
    )
    {
        List<User> users = userService.searchUsersByKeyword(keyword);
        if (users.isEmpty())
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return null;
        }
        List<String> usernames = users.stream().map(User::getName).collect(Collectors.toList());
        // remove users that are already admins
        usernames.removeAll(userService.getAllAdmins().stream().map(User::getName).collect(Collectors.toList()));

        return new Gson().toJson(usernames);
    }
}
