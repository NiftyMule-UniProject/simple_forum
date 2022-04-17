package neu.edu.csye6220.finalproject.dao;

import neu.edu.csye6220.finalproject.model.Admin;

import java.util.List;

public interface AdminDao
{
    void add(Admin admin);

    void delete(Long id);

    Admin get(Long id);

    Admin getByUserId(Long id);

    void update(Admin admin);

    List<Admin> list();
}
