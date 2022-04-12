package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.CommentDao;
import neu.edu.csye6220.finalproject.dao.PostDao;
import neu.edu.csye6220.finalproject.model.Comment;
import neu.edu.csye6220.finalproject.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
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
}
