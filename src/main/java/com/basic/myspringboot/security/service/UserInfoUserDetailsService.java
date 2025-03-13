package com.basic.myspringboot.security.service;

import com.basic.myspringboot.security.config.UserInfoUserDetails;
import com.basic.myspringboot.security.model.UserInfo;
import com.basic.myspringboot.security.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoUserDetailsService implements UserDetailsService {
    @Autowired
    private UserInfoRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> optionalUserInfo = repository.findByEmail(username);

        //긍까 UserInfoUserDetails service에서 UserInfoUserDetails 를 쓴다는거 아니여?!
        //위에 clss선언에서 implements를 한다는거랑 똑같
        //return optionalUserInfo.map(userInfo -> new UserInfoUserDetails(userInfo))
        return optionalUserInfo.map(UserInfoUserDetails::new)

                .orElseThrow(() -> new UsernameNotFoundException("user not found " + username));

    }

    public String addUser(UserInfo userInfo) {
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        UserInfo savedUserInfo = repository.save(userInfo);
        return savedUserInfo.getName() + " user added!!";
    }
}
