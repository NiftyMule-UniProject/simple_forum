package neu.edu.csye6220.finalproject.controller;

import neu.edu.csye6220.finalproject.dao.CommentDao;
import neu.edu.csye6220.finalproject.dao.PostDao;
import neu.edu.csye6220.finalproject.dao.UserDao;
import neu.edu.csye6220.finalproject.model.Comment;
import neu.edu.csye6220.finalproject.model.Post;
import neu.edu.csye6220.finalproject.model.PostType;
import neu.edu.csye6220.finalproject.model.User;
import neu.edu.csye6220.finalproject.service.CommentService;
import neu.edu.csye6220.finalproject.service.PostService;
import neu.edu.csye6220.finalproject.service.PostTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class PostController
{
    public static final int POST_PER_PAGE = 10;
    @Autowired
    PostService postService;

    @Autowired
    CommentService commentService;

    @Autowired
    PostTypeService postTypeService;

    @Autowired
    UserDao userDao;

    @GetMapping({"/posts", "/posts/{pageNum}"})
    public ModelAndView posts(
            @PathVariable(value = "pageNum", required = false) Integer pageNum,
            Principal principal
    )
    {
        ModelAndView view = new ModelAndView("posts");
        List<Post> posts;
        User user = userDao.getByUsername(principal.getName());

        if (pageNum == null)
            posts = postService.list(POST_PER_PAGE, 0);
        else
            posts = postService.list(POST_PER_PAGE, (pageNum - 1) * POST_PER_PAGE);

        long totalPost = postService.getTotalPostsNum();
        long totalPages = (totalPost + POST_PER_PAGE - 1) / POST_PER_PAGE;

        Map<Long, Boolean> upvoteMap = new HashMap<>();
        for (Post post : posts)
        {
            if (postService.checkUpvoteExist(post.getId(), user.getId()))
                upvoteMap.put(post.getId(), true);
            else
                upvoteMap.put(post.getId(), false);
        }

        view.addObject("posts", posts);
        view.addObject("upvoteMap", upvoteMap);
        view.addObject("currentPage", pageNum);
        view.addObject("totalPages", totalPages);
        return view;
    }

    @GetMapping("/post/detail/{postId}")
    public ModelAndView postDetail(
            @PathVariable(value="postId") Long postId,
            Principal principal
    )
    {
        ModelAndView view = new ModelAndView("postDetail");

        User user = userDao.getByUsername(principal.getName());
        Post post = postService.getPostById(postId);
        List<Comment> commentList = commentService.getCommentsByPostId(postId);
        boolean postUpvote = postService.checkUpvoteExist(postId, user.getId());

        Map<Long, Boolean> upvoteMap = new HashMap<>();
        for (Comment comment : commentList)
        {
            if (commentService.checkUpvoteExist(comment.getId(), user.getId()))
                upvoteMap.put(comment.getId(), true);
            else
                upvoteMap.put(comment.getId(), false);
        }

        view.addObject("postUpvote", postUpvote);
        view.addObject("post", post);
        view.addObject("comments", commentList);
        view.addObject("upvoteMap", upvoteMap);
        return view;
    }

    @GetMapping("/create_post")
    public ModelAndView postCreationForm()
    {
        List<PostType> postTypes = postTypeService.getAllTypes();
        return new ModelAndView("postCreation", "types", postTypes);
    }

    @PostMapping("/post")
    public ModelAndView postCreation(
            @RequestParam("title") String title,
            @RequestParam("type") int type,
            @RequestParam("content") String content,
            Principal principal
    )
    {
        ModelAndView view = new ModelAndView();
        List<PostType> postTypes = postTypeService.getAllTypes();
        String errMsg = postService.createPost(title, type, content, principal);
        if (errMsg == null)
        {
            view.setViewName("redirect:/posts");
            return view;
        }
        view.setViewName("postCreation");
        view.addObject("title", title);
        view.addObject("postType", type);
        view.addObject("postContent", content);
        view.addObject("types", postTypes);
        view.addObject("errMsg", errMsg);
        return view;
    }


    // TODO - To be deleted
    @Autowired
    PostDao postDao;
    @Autowired
    CommentDao commentDao;
    @GetMapping("/populate_posts")
    @ResponseBody
    public String populatePosts(Post post)
    {
        post.setTitle("This is a title");
        post.setUsername("username");
        post.setContent("This is a test content!");
        post.setCreationTime(Timestamp.from(Instant.now()));
        post.setUpvote(5);
        post.setLastCommentTime(post.getCreationTime());
        post.setTypeId(1L);

        postDao.add(post);

        Comment comment1 = new Comment();

        comment1.setUsername("username");
        comment1.setContent("Test content!");
        comment1.setPostId(post.getId());
        comment1.setCreationTime(Timestamp.from(Instant.now()));
        comment1.setUpvote(10);

        commentDao.add(comment1);

        Comment comment2 = new Comment();

        comment2.setUsername("username");
        comment2.setContent("Test content!");
        comment2.setPostId(post.getId());
        comment2.setCreationTime(Timestamp.from(Instant.now()));
        comment2.setUpvote(10);

        commentDao.add(comment2);

        return "populate successful!";
    }
}
