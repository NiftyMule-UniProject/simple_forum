package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.Comment;

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
}
