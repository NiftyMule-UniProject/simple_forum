package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(User user) {
        Session session = getSession();
        session.beginTransaction();

        session.save(user);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        User user = get(id);

        Session session = getSession();
        session.beginTransaction();

        session.delete(user);

        session.getTransaction().commit();
    }

    @Override
    public User get(Long id) {
        return (User) getSession().get(User.class, id);
    }

    @Override
    public void update(User user) {
        Session session = getSession();
        session.beginTransaction();

        session.merge(user);

        session.getTransaction().commit();
    }

    @Override
    public User getByUsername(String username)
    {
        String hql = "FROM User U WHERE U.name = :name";
        List results = getSession()
                .createQuery(hql)
                .setParameter("name", username)
                .getResultList();
        if (results.isEmpty())
            return null;
        return (User) results.get(0);
    }
}
