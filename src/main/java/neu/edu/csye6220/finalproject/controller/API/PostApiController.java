package neu.edu.csye6220.finalproject.controller.API;

import neu.edu.csye6220.finalproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class PostApiController
{
    @Autowired
    PostService postService;

    @DeleteMapping("/api/post/{postId}")
    public String deletePost(
            @PathVariable("postId") long postId,
            Authentication authentication,
            HttpServletResponse response
    )
    {
        String errMsg = postService.deletePost(postId, authentication);
        if (errMsg != null)
        {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return errMsg;
        }

        return "success!";
    }
}
