package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
//@RequiredArgsConstructor Lombok > Constoructor 생성자를 만들어 초기화를 시켜버림!!! 와우
public class UserRestController {

   /* @Autowired
    private UserRepository userRepository;*/

    private UserRepository userRepository;

    //alt + insert > Contstructor Injection
    public UserRestController(UserRepository userRepository) {
        this.userRepository = userRepository;
        log.info("UserRepository Object {}", userRepository.getClass().getName());
    }
    //Autowire대신 장점 : 협업 개발중에 목객체를 주입 받을수가 없으니깐... 이렇게 받아서 작업하는거

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping(value = "/{id}")
    public User getUser(@PathVariable Long id) {
        return getUserNotFound(id);
    }

    private User getUserNotFound(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }

    //@RequestMapping(value = "/users", produces = {"application/json"})
    @GetMapping
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PatchMapping("/{email}/")
    public User updateUser(@PathVariable String email, @RequestBody User userDetail) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        user.setName(userDetail.getName());
        //@Transactional하면 auto commit인데 controll단이라서(controll단에선 transactional X) save호출 해야 db반영됨
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = getUserNotFound(id);

        userRepository.delete(user);

        //return ResponseEntity.ok(user);
        //return ResponseEntity.ok().build();
        String msg = String.format("id = %d User Deleted Success!", id);
        return ResponseEntity.ok(msg);
    }


}