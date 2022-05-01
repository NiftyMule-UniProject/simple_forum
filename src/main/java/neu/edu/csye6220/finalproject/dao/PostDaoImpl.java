package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostDaoImpl implements PostDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(Post post) {
        getSession().save(post);
    }

    @Override
    public void delete(Long id) {
        Post post = get(id);
        getSession().delete(post);
    }

    @Override
    public Post get(Long id) {
        return (Post) getSession().get(Post.class, id);
    }

    @Override
    public void update(Post post) {
        getSession().merge(post);
    }

    @Override
    public List<Post> listPosts(int nums, int offset)
    {
        String hql = "FROM Post";
        return getSession().createQuery(hql, Post.class)
                .setMaxResults(nums)
                .setFirstResult(offset)
                .getResultList();
    }

    @Override
    public long getTotalPostsNum()
    {
        String hql = "SELECT count(id) FROM Post";
        return getSession().createQuery(hql, Long.class).uniqueResult();
    }
}
