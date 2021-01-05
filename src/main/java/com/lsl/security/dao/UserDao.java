package com.lsl.security.dao;

import com.lsl.security.model.PermissionDetail;
import com.lsl.security.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
            @Autowired
            JdbcTemplate jdbcTemplate;
          public UserDetail getUserByUserName(String username){
            String sql ="select id,username,password,fullname,mobile from t_user where username=?";
              List<UserDetail> list = jdbcTemplate.query(sql, new Object[]{username}, new BeanPropertyRowMapper<>(UserDetail.class));
             if(list!=null &&list.size()==1){
                 return (UserDetail) list.get(0);
             }else {
                 return null;
             }
          };


          public List<String> getAuthByUserId(String userId){
              String sql ="    select code from t_permission where id in(\n" +
                      "            select permission_id from t_role_permission where role_id = (\n" +
                      "            select role_id from t_user_role where user_id= (\n" +
                      "            select id from t_user where id=?)\n" +
                      "           ))";


              List<PermissionDetail> list = jdbcTemplate.query(sql, new Object[]{userId}, new BeanPropertyRowMapper<>(PermissionDetail.class));
              List<String>permissions=new ArrayList<>();
              list.iterator().forEachRemaining(c ->permissions.add(c.getCode()));
               return permissions;
          };
}
