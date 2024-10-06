package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.exception.user.ExceptionNoDataFoundUser;
import com.nxhu.eventosDeAutos.exception.user.ExceptionUserNotFound;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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
        return iUserRepository.findById(userId).orElseThrow(() -> new ExceptionUserNotFound(userId));
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return iUserRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long userId) {
        iUserRepository.deleteById(userId);
    }

    @Override
    public UserEntity updateUser(Long userId, String newUsername, String newEmail, String newPassword) {
        UserEntity newUser =  this.getUser(userId);

        if (newUser == null) {
            throw new ExceptionUserNotFound(userId);
        }

        newUser.setUsername(newUsername);
        newUser.setEmail(newEmail);
        newUser.setPassword(newPassword);

        this.createUser(newUser);
        return newUser;
    }
}
