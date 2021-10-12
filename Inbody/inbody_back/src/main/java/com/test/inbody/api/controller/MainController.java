package com.test.inbody.api.controller;

import com.test.inbody.api.db.entity.User;
import com.test.inbody.api.dto.UserDto;
import com.test.inbody.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/main")
public class MainController {

    @Autowired
    UserService userService;

    @GetMapping("/getOneUser/{name}")
    public ResponseEntity<UserDto> getRequest(@PathVariable String name){
        User user = userService.getUser(name);

        return ResponseEntity.status(200).body(UserDto.getUserObj(user));
    }

    @GetMapping("/getUserList")
    public ResponseEntity<List<User>> getRequest() {
        return ResponseEntity.status(200).body(userService.getUserList());
    }

    @PostMapping("/postUser")
    public ResponseEntity postUser(@RequestBody UserDto userDto){
        userService.register(userDto);
        return ResponseEntity.status(200).body("");
    }

}
