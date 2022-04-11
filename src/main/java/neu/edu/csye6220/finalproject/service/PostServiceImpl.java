package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.PostDao;
import neu.edu.csye6220.finalproject.dao.PostTypeDao;
import neu.edu.csye6220.finalproject.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService
{
    @Autowired
    PostDao postDao;

    @Autowired
    PostTypeDao postTypeDao;

    @Override
    public Post getPostById(Long id)
    {
        return postDao.get(id);
    }

    @Override
    public List<Post> list(int nums, int offset)
    {
        return postDao.listPosts(nums, offset);
    }
}
