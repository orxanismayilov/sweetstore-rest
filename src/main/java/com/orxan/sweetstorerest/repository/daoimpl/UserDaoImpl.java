package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.mappers.UserMapper;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.repository.UserDao;
import com.orxan.sweetstorerest.util.PasswordAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void startUserSesion(User user) {
    }

    @Override
    public void addUserAddList(User user) {
        String sql="INSERT INTO USERS (name,password,role,is_active) VALUES(?,?,?,?);";
        PasswordAuthentication pa=new PasswordAuthentication();
        String password=pa.hash(user.getPassword());
        jdbcTemplate.update(sql,user.getName(),password,user.getRole().toString(),user.isActive()? 1:0);
    }

    @Override
    public void deleteUserById(int id) {
        String sql="UPDATE USERS SET is_active=0 where id=?;";
        jdbcTemplate.update(sql,id);
    }

    @Override
    public boolean validateLogin(User user) {
        String sql="SELECT * FROM USERS WHERE name=? AND  is_active=1";
        try {
            User u= (User) jdbcTemplate.queryForObject(sql,new UserMapper(),user.getName());
            return  authcateUserPassword(u,user.getPassword());
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    private boolean authcateUserPassword(User user, String password) {
        PasswordAuthentication passwordAuthentication=new PasswordAuthentication();
        if(passwordAuthentication.authenticate(password,user.getPassword())) {
            startUserSesion(user);
            return true;
        }
        return false;
    }
}
