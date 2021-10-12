package com.test.inbody.api.dto;

import com.test.inbody.api.db.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    int id;
    String name;
    int age;
    int gender;
    int score;

    public static UserDto getUserObj(User user){
        UserDto userdto = new UserDto();
        userdto.setId(user.getId().intValue());
        userdto.setName(user.getName());
        userdto.setAge(user.getAge());
        userdto.setGender(user.getGender());
        userdto.setScore(user.getScore());
        return userdto;
    }
}
