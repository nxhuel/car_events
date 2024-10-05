package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.model.UserEntity;

import java.util.List;

public interface IUserService {
    public List<UserEntity> getUsers();

    public UserEntity getUser(Long userId);

    public UserEntity createUser(UserEntity userEntity);

    public void deleteUser(Long userId);

    public UserEntity updateUser(Long userId, String newUsername, String newEmail, String newPassword);
}
