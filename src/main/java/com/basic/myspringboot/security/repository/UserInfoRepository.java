package com.basic.myspringboot.security.repository;

import com.basic.myspringboot.security.model.UserInfo;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserInfoRepository extends ListCrudRepository<UserInfo, Integer> {
Optional<UserInfo> findByEmail(String email);
}