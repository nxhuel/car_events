package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.exception.user.ExceptionNoDataFoundUser;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService implements IUserService{

    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public List<UserEntity> getUsers() {
        var users = (List<UserEntity>) iUserRepository.findAll();
        if (users.isEmpty()) {
            throw new ExceptionNoDataFoundUser();
        }
        return users;
    }

    @Override
    public UserEntity getUser(Long userId) {
        return null;
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return null;
    }

    @Override
    public void deleteUser(Long userId) {

    }

    @Override
    public UserEntity updateUser(Long userId, String newUsername, String newEmail, String newPassword) {
        return null;
    }
}
