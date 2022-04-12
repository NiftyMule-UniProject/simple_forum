package neu.edu.csye6220.finalproject.service;

import neu.edu.csye6220.finalproject.dao.PostTypeDao;
import neu.edu.csye6220.finalproject.model.PostType;
import org.springframework.beans.factory.annotation.Autowired;
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
        return postTypeDao.list();
    }
}
