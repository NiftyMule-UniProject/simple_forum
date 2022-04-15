package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.CommentUpvote;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CommentUpvoteDaoImpl implements CommentUpvoteDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(CommentUpvote commentUpvote) {
        Session session = getSession();
        session.beginTransaction();

        session.save(commentUpvote);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        CommentUpvote commentUpvote = get(id);

        Session session = getSession();
        session.beginTransaction();
        getSession().delete(commentUpvote);
        session.getTransaction().commit();
    }

    @Override
    public CommentUpvote get(Long id) {
        return (CommentUpvote) getSession().get(CommentUpvote.class, id);
    }

    @Override
    public void update(CommentUpvote commentUpvote) {
        Session session = getSession();
        session.beginTransaction();
        getSession().merge(commentUpvote);
        session.getTransaction().commit();
    }

    @Override
    public CommentUpvote getCommentUpvote(long commentId, long userId) {
        String hql = "FROM CommentUpvote WHERE commentId=:postId AND userId=:userId";
        return getSession().createQuery(hql, CommentUpvote.class)
                .setParameter("postId", commentId)
                .setParameter("userId", userId)
                .uniqueResult();
    }
}
