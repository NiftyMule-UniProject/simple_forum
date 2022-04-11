package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.CommentDao;
import neu.edu.csye6220.finalproject.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService
{
    @Autowired
    CommentDao commentDao;

    @Override
    public List<Comment> getCommentsByPostId(Long postId)
    {
        return commentDao.getCommentsByPostId(postId);
    }
}
