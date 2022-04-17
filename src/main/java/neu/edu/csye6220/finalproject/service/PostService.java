package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.Post;
import org.springframework.security.core.Authentication;

import java.security.Principal;
import java.util.List;

public interface PostService
{
    Post getPostById(Long id);

    List<Post> list(int nums, int offset);

    String createPost(
            String title,
            int postTypeId,
            String content,
            Principal principal
    );

    String deletePost(
            long postId,
            Authentication authentication
    );

    long getTotalPostsNum();

    boolean checkUpvoteExist(long postId, long userId);

    String upvotePost(
            long postId,
            Authentication authentication
    );

    String cancelUpvote(long postId, Authentication authentication);
}
