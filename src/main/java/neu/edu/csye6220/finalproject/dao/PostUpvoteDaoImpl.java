package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.PostUpvote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostUpvoteDaoImpl implements PostUpvoteDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(PostUpvote postUpvote) {
        Session session = getSession();
        session.beginTransaction();

        session.save(postUpvote);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        PostUpvote postUpvote = get(id);

        Session session = getSession();
        session.beginTransaction();
        getSession().delete(postUpvote);
        session.getTransaction().commit();
    }

    @Override
    public PostUpvote get(Long id) {
        return (PostUpvote) getSession().get(PostUpvote.class, id);
    }

    @Override
    public void update(PostUpvote postUpvote) {
        Session session = getSession();
        session.beginTransaction();
        getSession().merge(postUpvote);
        session.getTransaction().commit();
    }

    @Override
    public PostUpvote getPostUpvote(long postId, long userId) {
        String hql = "FROM PostUpvote WHERE postId=:postId AND userId=:userId";
        return getSession().createQuery(hql, PostUpvote.class)
                .setParameter("postId", postId)
                .setParameter("userId", userId)
                .uniqueResult();
    }
}
