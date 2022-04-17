package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.*;
import neu.edu.csye6220.finalproject.model.*;
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

    @Autowired
    PostUpvoteDao postUpvoteDao;

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

        if (!hasPermission(post, authentication))
            return "Unauthorized action";

        postDao.delete(postId);
        for (Comment comment : commentDao.getCommentsByPostId(postId))
            commentDao.delete(comment.getId());

        return null;
    }

    @Override
    public long getTotalPostsNum()
    {
        return postDao.getTotalPostsNum();
    }

    @Override
    public boolean checkUpvoteExist(long postId, long userId)
    {
        return postUpvoteDao.getPostUpvote(postId, userId) != null;
    }

    @Override
    public String upvotePost(long postId, Authentication authentication)
    {
        Post post = postDao.get(postId);
        if (post == null) return "Invalid post ID!";

        User user = userDao.getByUsername(authentication.getName());
        if (checkUpvoteExist(postId, user.getId()))
            return "Cannot upvote twice!";

        post.setUpvote(post.getUpvote() + 1);
        postDao.update(post);

        PostUpvote postUpvote = new PostUpvote();
        postUpvote.setPostId(postId);
        postUpvote.setUserId(user.getId());
        postUpvoteDao.add(postUpvote);

        return null;
    }

    @Override
    public String cancelUpvote(long postId, Authentication authentication)
    {
        Post post = postDao.get(postId);
        if (post == null) return "Invalid post ID!";

        User user = userDao.getByUsername(authentication.getName());
        PostUpvote postUpvote = postUpvoteDao.getPostUpvote(postId, user.getId());
        if (postUpvote == null)
            return "Upvote does not exist!";

        post.setUpvote(post.getUpvote() - 1);
        postDao.update(post);

        postUpvoteDao.delete(postUpvote.getId());

        return null;
    }

    private boolean validatePostType(int postTypeId)
    {
        List<PostType> types = postTypeDao.list(false);
        for (PostType type : types)
        {
            if (type.getId() == postTypeId) return true;
        }
        return false;
    }

    private boolean hasPermission(Post post, Authentication authentication)
    {
        boolean isAdmin = false;
        for (var auth : authentication.getAuthorities())
            if (auth.getAuthority().equals("admin")) isAdmin = true;

        return post.getUsername().equals(authentication.getName()) || isAdmin;
    }
}
