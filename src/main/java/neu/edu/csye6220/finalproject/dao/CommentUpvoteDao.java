package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.CommentUpvote;

public interface CommentUpvoteDao
{
    void add(CommentUpvote commentUpvote);

    void delete(Long id);

    CommentUpvote get(Long id);

    void update(CommentUpvote commentUpvote);

    CommentUpvote getCommentUpvote(long commentId, long userId);
}
