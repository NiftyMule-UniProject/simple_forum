package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.Post;

import java.util.List;

public interface PostDao
{
    void add(Post post);

    void delete(Long id);

    Post get(Long id);

    void update(Post post);

    List<Post> listPosts(int nums, int offset);

    long getTotalPostsNum();
}
