package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.Comment;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface CommentService
{
    List<Comment> getCommentsByPostId(Long postId);

    String addComment(
            long postId,
            String referToCommentId,
            String content,
            Principal principal
    );

    String deleteComment(
            long commentId,
            Authentication authentication
    );

    boolean checkUpvoteExist(long commentId, long userId);

    String upvoteComment(long commentId, Authentication authentication);

    String cancelUpvote(long commentId, Authentication authentication);
}
