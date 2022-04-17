package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.model.PostType;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface PostTypeService
{
    List<PostType> getAllTypes();

    String addPostType(
            String postTypeName,
            Authentication authentication
    );

    String deletePostType(
            long postTypeId,
            Authentication authentication
    );
}
