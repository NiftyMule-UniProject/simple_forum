package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.Post;

import java.util.List;

public interface PostService
{
    Post getPostById(Long id);

    List<Post> list(int nums, int offset);
}
