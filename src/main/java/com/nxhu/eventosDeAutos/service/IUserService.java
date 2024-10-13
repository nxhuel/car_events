package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.model.UserEntity;

import java.util.List;

public interface IUserService {
    public List<UserEntity> getUsers();

    public UserEntity getUser(Long user_dni);

    public UserEntity createUser(UserEntity userEntity);

    public void deleteUser(Long user_dni);

    public UserEntity updateUser(Long user_dni, String newUsername, String newEmail, String newPassword);
}
