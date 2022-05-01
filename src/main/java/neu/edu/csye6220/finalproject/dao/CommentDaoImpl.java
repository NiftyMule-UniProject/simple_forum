package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.Comment;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CommentDaoImpl implements CommentDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(Comment comment) {
        getSession().save(comment);
    }

    @Override
    public void delete(Long id) {
        Comment comment = get(id);
        getSession().delete(comment);
    }

    @Override
    public Comment get(Long id) {
        return (Comment) getSession().get(Comment.class, id);
    }

    @Override
    public List<Comment> getCommentsByPostId(Long postId) {
        String hql = "FROM Comment WHERE postId = :postId";
        return getSession()
                .createQuery(hql, Comment.class)
                .setParameter("postId", postId)
                .getResultList();
    }

    @Override
    public void update(Comment comment) {
        getSession().merge(comment);
    }
}
