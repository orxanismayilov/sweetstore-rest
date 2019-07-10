package com.orxan.sweetstorerest.repository.daoimpl;

import com.orxan.sweetstorerest.enums.UserRole;
import com.orxan.sweetstorerest.model.User;
import com.orxan.sweetstorerest.repository.UserDao;
import com.orxan.sweetstorerest.util.DBConnection;
import com.orxan.sweetstorerest.util.PasswordAuthentication;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class UserDaoImpl implements UserDao {
    @Override
    public void startUserSesion(User user) {

    }

    @Override
    public void addUserAddList(User user) {

    }

    @Override
    public void deleteUserById(int id) {

    }

    @Override
    public boolean validateLogin(User user) {
        String sql="SELECT * FROM USERS WHERE name=? AND  is_active=1";
        try (Connection con= DBConnection.getConnection();
             PreparedStatement ps=con.prepareStatement(sql))
         {
             ps.setString(1,user.getName());
             try (ResultSet rs=ps.executeQuery()){
                 while (rs.next()) {
                     User u=new User();
                     u.setId(rs.getInt("id"));
                     u.setName(rs.getString("name"));
                     u.setActive(rs.getBoolean("is_active"));
                     u.setPassword(rs.getString("maindb.password"));
                     u.setRole(UserRole.valueOf(rs.getString("role")));
                     return authcateUserPassword(u,user.getPassword());
                 }
             }catch (SQLException e) {
                 e.printStackTrace();
             }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
