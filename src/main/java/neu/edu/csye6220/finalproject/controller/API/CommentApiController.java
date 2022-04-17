package neu.edu.csye6220.finalproject.controller.API;

import neu.edu.csye6220.finalproject.service.CommentService;
import neu.edu.csye6220.finalproject.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

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

    @DeleteMapping("/api/post/{postId}/comment/{commentId}")
    public String deleteComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            Authentication authentication,
            HttpServletResponse response
    )
    {
        String errMsg = commentService.deleteComment(commentId, authentication);
        if (errMsg != null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return errMsg;
        }

        return "success!";
    }

    @PostMapping("/api/post/{postId}/comment/{commentId}/upvote")
    public String upvoteComment(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            Authentication authentication,
            HttpServletResponse response
    )
    {
        String errMsg = commentService.upvoteComment(commentId, authentication);
        if (errMsg != null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return errMsg;
        }
        return "success!";
    }

    @DeleteMapping("/api/post/{postId}/comment/{commentId}/upvote")
    public String cancelUpvote(
            @PathVariable("postId") long postId,
            @PathVariable("commentId") long commentId,
            Authentication authentication,
            HttpServletResponse response
    )
    {
        String errMsg = commentService.cancelUpvote(commentId, authentication);
        if (errMsg != null)
        {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return errMsg;
        }
        return "success!";
    }
}
