package com.viktor.skype.service;

import com.viktor.skype.data.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public interface UserService extends UserDetailsService {
     User save(User user);

    User findByUsernameAndPassword(String username,String password);
}
