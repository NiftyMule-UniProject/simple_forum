package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.PostDao;
import neu.edu.csye6220.finalproject.dao.PostTypeDao;
import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.Post;
import neu.edu.csye6220.finalproject.model.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@Service
public class PostServiceImpl implements PostService
{
    @Autowired
    PostDao postDao;

    @Autowired
    PostTypeDao postTypeDao;

    @Autowired
    UserDao userDao;

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

    @Override
    public String createPost(
            String title,
            int postTypeId,
            String content,
            Principal principal
    )
    {
        if (title.length() > 250) return "Title too long! (max 250 characters)";
        if (!validatePostType(postTypeId)) return "Invalid post type!";

        Post post = new Post();
        post.setContent(content);
        post.setCreationTime(Timestamp.from(Instant.now()));
        post.setTitle(title);
        post.setTypeId((long) postTypeId);
        post.setUsername(principal.getName());
        post.setUpvote(0);

        postDao.add(post);
        return null;
    }

    private boolean validatePostType(int postTypeId)
    {
        List<PostType> types = postTypeDao.list();
        for (PostType type : types)
        {
            if (type.getId() == postTypeId) return true;
        }
        return false;
    }
}
