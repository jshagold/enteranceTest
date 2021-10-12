package com.test.inbody.api.service;

import com.test.inbody.api.db.entity.User;
import com.test.inbody.api.db.repository.UserRepository;
import com.test.inbody.api.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;


    @Override
    public void register(UserDto userdto) {
        User user = new User();
        user.setName(userdto.getName());
        user.setAge(userdto.getAge());
        user.setGender(userdto.getGender());
        user.setScore(userdto.getScore());

        userRepository.save(user);
    }

    @Override
    public User getUser(String name) {
        Optional<User> user = userRepository.findByName(name);
        return user.get();
    }

    @Override
    public List<User> getUserList() {
        List<User> userList = userRepository.findAll();
        return userList;
    }
}
