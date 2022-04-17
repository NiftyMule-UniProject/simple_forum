package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.PostType;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostTypeDaoImpl implements PostTypeDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    List<PostType> types = null;

    @Override
    public void add(PostType postType) {
        Session session = getSession();
        session.beginTransaction();

        session.save(postType);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        PostType postType = get(id);
        Session session = getSession();
        session.beginTransaction();

        session.delete(postType);

        session.getTransaction().commit();
    }

    @Override
    public PostType get(Long id) {
        return (PostType) getSession().get(PostType.class, id);
    }

    @Override
    public void update(PostType postType) {
        Session session = getSession();
        session.beginTransaction();

        session.merge(postType);

        session.getTransaction().commit();
    }

    @Override
    public PostType getPostTypeByName(String postTypeName)
    {
        String hql = "FROM PostType WHERE typeName = :postTypeName";
        return getSession().createQuery(hql, PostType.class)
                .setParameter("postTypeName", postTypeName)
                .uniqueResult();
    }

    @Override
    public List<PostType> list(boolean refreshCache) {
        // Post types are unlikely to be changed when running the application,
        // so we cached the list of post types
        if (types == null || refreshCache)
        {
            String hql = "FROM PostType";
            types = getSession().createQuery(hql, PostType.class).getResultList();
        }
        return types;
    }
}
