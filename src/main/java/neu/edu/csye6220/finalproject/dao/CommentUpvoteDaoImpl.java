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
        getSession().save(commentUpvote);
    }

    @Override
    public void delete(Long id) {
        CommentUpvote commentUpvote = get(id);
        getSession().delete(commentUpvote);
    }

    @Override
    public CommentUpvote get(Long id) {
        return getSession().get(CommentUpvote.class, id);
    }

    @Override
    public void update(CommentUpvote commentUpvote) {
        getSession().merge(commentUpvote);
    }

    @Override
    public CommentUpvote getCommentUpvote(long commentId, long userId) {
        String hql = "FROM CommentUpvote WHERE commentId=:postId AND userId=:userId";
        return getSession().createQuery(hql, CommentUpvote.class)
                .setParameter("postId", commentId)
                .setParameter("userId", userId)
                .uniqueResult();
    }

    @Override
    public void deleteByCommentId(Long commentId) {
        String hql = "DELETE FROM CommentUpvote WHERE commentId=:commentId";
        getSession().createQuery(hql)
                .setParameter("commentId", commentId)
                .executeUpdate();
    }
}
