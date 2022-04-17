package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AdminDaoImpl implements AdminDao
{
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(Admin admin)
    {
        Session session = getSession();
        session.beginTransaction();

        session.save(admin);

        session.getTransaction().commit();
    }

    @Override
    public void delete(Long id) {
        Admin admin = get(id);
        Session session = getSession();
        session.beginTransaction();

        session.delete(admin);

        session.getTransaction().commit();
    }

    @Override
    public Admin get(Long id) {
        return getSession().get(Admin.class, id);
    }

    @Override
    public Admin getByUserId(Long userId)
    {
        String hql = "FROM Admin WHERE userId = :userid";
        List results = getSession()
                .createQuery(hql)
                .setParameter("userid", userId)
                .getResultList();
        if (results.isEmpty())
            return null;
        return (Admin) results.get(0);
    }

    @Override
    public void update(Admin admin) {
        Session session = getSession();
        session.beginTransaction();

        session.merge(admin);

        session.getTransaction().commit();
    }

    @Override
    public List<Admin> list()
    {
        String hql = "FROM Admin";
        return getSession().createQuery(hql, Admin.class).getResultList();
    }
}
