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
    public UserEntity getUser(Long user_dni) {
        return iUserRepository.findById(user_dni).orElseThrow(() -> new ExceptionUserNotFound(user_dni));
    }

    @Override
    public UserEntity createUser(UserEntity userEntity) {
        return iUserRepository.save(userEntity);
    }

    @Override
    public void deleteUser(Long user_dni) {
        iUserRepository.deleteById(user_dni);
    }

    @Override
    public UserEntity updateUser(Long user_dni, String newUsername, String newEmail, String newPassword) {
        UserEntity newUser =  this.getUser(user_dni);

        if (newUser == null) {
            throw new ExceptionUserNotFound(user_dni);
        }

        newUser.setUsername(newUsername);
        newUser.setEmail(newEmail);
        newUser.setPassword(newPassword);

        this.createUser(newUser);
        return newUser;
    }
}
