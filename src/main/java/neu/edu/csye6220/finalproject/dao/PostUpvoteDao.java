package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.PostUpvote;

public interface PostUpvoteDao
{
    void add(PostUpvote postUpvote);

    void delete(Long id);

    PostUpvote get(Long id);

    void update(PostUpvote postUpvote);

    PostUpvote getPostUpvote(long postId, long userId);
}
