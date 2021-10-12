package com.test.inbody.api.service;

import com.test.inbody.api.db.entity.User;
import com.test.inbody.api.dto.UserDto;

import java.util.List;

public interface UserService {
    void register(UserDto userdto);
    User getUser(String name);
    List<User> getUserList();
}
