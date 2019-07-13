package com.orxan.sweetstorerest.mappers;

import com.orxan.sweetstorerest.enums.UserRole;
import com.orxan.sweetstorerest.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int i) throws SQLException {
        User user=new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setActive(rs.getBoolean("is_active"));
        user.setPassword(rs.getString("Password"));
        user.setRole(UserRole.valueOf(rs.getString("role")));

        return user;
    }
}
