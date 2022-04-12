package neu.edu.csye6220.finalproject.controller.API;

import neu.edu.csye6220.finalproject.service.CommentService;
import neu.edu.csye6220.finalproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@RestController
public class CommentApiController
{
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @PostMapping("/api/post/{postId}/comment")
    public String addComment(
        @PathVariable("postId") long postId,
        @RequestParam("referTo") String referTo,
        @RequestParam("content") String content,
        Principal principal,
        HttpServletResponse response
    )
    {
        String errMsg = commentService.addComment(postId, referTo, content, principal);
        if (errMsg != null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return errMsg;
        }
        return "success!";
    }
}
