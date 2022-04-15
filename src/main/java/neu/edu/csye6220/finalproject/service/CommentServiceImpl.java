package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.CommentDao;
import neu.edu.csye6220.finalproject.dao.CommentUpvoteDao;
import neu.edu.csye6220.finalproject.dao.PostDao;
import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.Comment;
import neu.edu.csye6220.finalproject.model.CommentUpvote;
import neu.edu.csye6220.finalproject.model.Post;
import neu.edu.csye6220.finalproject.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService
{
    @Autowired
    CommentDao commentDao;

    @Autowired
    PostDao postDao;

    @Autowired
    CommentUpvoteDao commentUpvoteDao;

    @Autowired
    UserDao userDao;

    @Override
    public List<Comment> getCommentsByPostId(Long postId)
    {
        return commentDao.getCommentsByPostId(postId);
    }

    @Override
    public String addComment(
            long postId,
            String referToCommentId,
            String content,
            Principal principal
    )
    {
        Post post = postDao.get(postId);
        if (post == null) return "Invalid post ID!";

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setCreationTime(Timestamp.from(Instant.now()));
        comment.setPostId(postId);

        if (!referToCommentId.isEmpty())
        {
            if (!referToCommentId.matches("[0-9]*")) return "Invalid reference comment ID!";
            Comment referTo = commentDao.get(Long.valueOf(referToCommentId));
            if (referTo == null) return "Reference comment not found!";
            comment.setReferToCommentId(Long.valueOf(referToCommentId));
        }

        comment.setUpvote(0);
        comment.setUsername(principal.getName());

        commentDao.add(comment);

        return null;
    }

    @Override
    public String deleteComment(
            long commentId,
            Authentication authentication
    )
    {
        Comment comment = commentDao.get(commentId);
        if (comment == null) return "Invalid comment ID!";

        boolean isAdmin = false;
        for (var auth : authentication.getAuthorities())
            if (auth.getAuthority().equals("admin")) isAdmin = true;

        if (!comment.getUsername().equals(authentication.getName()) && !isAdmin)
            return "Unauthorized action";

        commentDao.delete(commentId);
        return null;
    }

    @Override
    public boolean checkUpvoteExist(long commentId, long userId)
    {
        return commentUpvoteDao.getCommentUpvote(commentId, userId) != null;
    }

    @Override
    public String upvoteComment(long commentId, Authentication authentication)
    {
        Comment comment = commentDao.get(commentId);
        if (comment == null) return "Invalid comment ID!";

        User user = userDao.getByUsername(authentication.getName());
        if (checkUpvoteExist(commentId, user.getId()))
            return "Cannot upvote twice!";

        comment.setUpvote(comment.getUpvote() + 1);
        commentDao.update(comment);

        CommentUpvote commentUpvote = new CommentUpvote();
        commentUpvote.setCommentId(commentId);
        commentUpvote.setUserId(user.getId());
        commentUpvoteDao.add(commentUpvote);

        return null;
    }

    @Override
    public String cancelUpvote(long commentId, Authentication authentication)
    {
        Comment comment = commentDao.get(commentId);
        if (comment == null) return "Invalid comment ID!";

        User user = userDao.getByUsername(authentication.getName());
        CommentUpvote commentUpvote = commentUpvoteDao.getCommentUpvote(commentId, user.getId());
        if (commentUpvote == null)
            return "Upvote does not exist!";

        comment.setUpvote(comment.getUpvote() - 1);
        commentDao.update(comment);

        commentUpvoteDao.delete(commentUpvote.getId());

        return null;
    }
}
