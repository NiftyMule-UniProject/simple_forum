package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.PostTypeDao;
import neu.edu.csye6220.finalproject.model.PostType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostTypeServiceImpl implements PostTypeService
{
    @Autowired
    PostTypeDao postTypeDao;

    @Override
    public List<PostType> getAllTypes()
    {
        return postTypeDao.list(false);
    }

    @Override
    public String addPostType(
            String postTypeName,
            Authentication authentication
    )
    {
        if (postTypeName.isEmpty())
            return "Invalid post type name!";
        if (postTypeDao.getPostTypeByName(postTypeName) != null)
            return "Post type already exists!";

        if (!hasPermission(authentication)) return "Not authorized!";

        PostType postType = new PostType();
        postType.setTypeName(postTypeName);
        postTypeDao.add(postType);

        // refresh DAO cache
        postTypeDao.list(true);

        return null;
    }

    @Override
    public String deletePostType(
            long postTypeId,
            Authentication authentication)
    {
        if (postTypeDao.get(postTypeId) == null)
            return "Inavlid post type ID!";

        if (!hasPermission(authentication)) return "Not authorized!";

        postTypeDao.delete(postTypeId);
        return null;
    }

    private boolean hasPermission(Authentication authentication)
    {
        for (var auth : authentication.getAuthorities())
            if (auth.getAuthority().equals("superuser")) return true;
        return false;
    }
}
