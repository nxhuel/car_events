package com.nxhu.eventosDeAutos.controller;

import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class UserController {

    @Autowired
    private IUserService iUserService;

    @GetMapping("/users")
    public ResponseEntity<List<UserEntity>> getUsers() {
        return new ResponseEntity<>(iUserService.getUsers(), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserEntity> getUser(@PathVariable Long userId) {
        return new ResponseEntity<>(iUserService.getUser(userId), HttpStatus.OK);
    }

    @PostMapping("/user/create")
    public ResponseEntity<UserEntity> createUser(@RequestBody @Validated UserEntity user) {
        return new ResponseEntity<>(iUserService.createUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/user/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable Long userId) {
        iUserService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/update/{userId}")
    private ResponseEntity<UserEntity> updateUser(@PathVariable Long userId,
                                                  @RequestParam(required = false, value = "username") String newUsername,
                                                  @RequestParam(required = false, value = "email") String newEmail,
                                                  @RequestParam(required = false, value = "password") String newPassword) {
        iUserService.updateUser(userId, newUsername, newEmail, newPassword);
        UserEntity updatedUser = iUserService.getUser(userId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}
