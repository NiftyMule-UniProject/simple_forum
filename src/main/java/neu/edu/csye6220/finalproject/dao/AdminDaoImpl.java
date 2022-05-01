package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.Admin;
import neu.edu.csye6220.finalproject.model.User;
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
        getSession().save(admin);
    }

    @Override
    public void delete(Long id) {
        Admin admin = get(id);
        getSession().delete(admin);
    }

    @Override
    public Admin get(Long id) {
        return getSession().get(Admin.class, id);
    }

    @Override
    public Admin getByUserId(Long userId)
    {
        String hql = "FROM Admin WHERE userId = :userid";
        List<Admin> results = getSession()
                .createQuery(hql, Admin.class)
                .setParameter("userid", userId)
                .getResultList();
        if (results.isEmpty())
            return null;
        return (Admin) results.get(0);
    }

    @Override
    public void update(Admin admin) {
        getSession().merge(admin);
    }

    @Override
    public List<Admin> list()
    {
        String hql = "FROM Admin";
        return getSession().createQuery(hql, Admin.class).getResultList();
    }
}
