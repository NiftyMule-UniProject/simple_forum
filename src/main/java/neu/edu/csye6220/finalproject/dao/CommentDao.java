package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentDao
{
    void add(Comment comment);

    void delete(Long id);

    Comment get(Long id);

    List<Comment> getCommentsByPostId(Long postId);

    void update(Comment comment);
}
