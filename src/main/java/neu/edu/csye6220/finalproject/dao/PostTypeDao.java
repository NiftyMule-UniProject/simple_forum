package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.PostType;

import java.util.List;

public interface PostTypeDao
{
    void add(PostType postType);

    void delete(Long id);

    PostType get(Long id);

    void update(PostType postType);

    PostType getPostTypeByName(String postTypeName);

    List<PostType> list(boolean refreshCache);
}
