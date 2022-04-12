package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.CommentDao;
import neu.edu.csye6220.finalproject.dao.PostDao;
import neu.edu.csye6220.finalproject.dao.PostTypeDao;
import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.Comment;
import neu.edu.csye6220.finalproject.model.Post;
import neu.edu.csye6220.finalproject.model.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
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

    @Autowired
    CommentDao commentDao;

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

    @Override
    public String deletePost(
            long postId,
            Authentication authentication
    )
    {
        Post post = postDao.get(postId);
        if (post == null) return "Invalid post ID!";

        boolean isAdmin = false;
        for (var auth : authentication.getAuthorities())
            if (auth.getAuthority().equals("admin")) isAdmin = true;

        if (!post.getUsername().equals(authentication.getName()) && !isAdmin)
            return "Unauthorized action";

        postDao.delete(postId);
        for (Comment comment : commentDao.getCommentsByPostId(postId))
            commentDao.delete(comment.getId());

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
