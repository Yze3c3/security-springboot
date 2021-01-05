package com.lsl.security.service;

import com.lsl.security.dao.UserDao;
import com.lsl.security.model.PermissionDetail;
import com.lsl.security.model.UserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpringDataUserDetailsService implements UserDetailsService {
    @Autowired
    UserDao userDao;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("username="+username);
        System.out.println("password="+ BCrypt.hashpw("1234",BCrypt.gensalt()));
        UserDetail user = userDao.getUserByUserName(username);
         if(user==null){
             return null;
         }
        List<String> permissions = userDao.getAuthByUserId(user.getId());

         String[]perarray =new String[permissions.size()];

        //将permissions取出装入perarray数组中
        permissions.toArray(perarray);
        UserDetails userDetails = User.withUsername(user.getUsername()).password(user.getPassword()).authorities(perarray).build();
        return userDetails;
    }
}
