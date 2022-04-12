package neu.edu.csye6220.finalproject.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController implements ErrorController
{
    @GetMapping("/")
    public String homepage()
    {
        return "index";
    }

    @GetMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {
        String msg = "Oh oh! Something went into trouble... ";
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if(statusCode == HttpStatus.NOT_FOUND.value()) {
                msg += "error-404";
            }
            else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                msg +=  "error-500";
            }
        }

        return msg;
    }
}
