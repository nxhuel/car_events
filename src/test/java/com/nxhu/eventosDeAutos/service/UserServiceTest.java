package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.exception.user.ExceptionNoDataFoundUser;
import com.nxhu.eventosDeAutos.exception.user.ExceptionUserNotFound;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.repository.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private IUserRepository iUserRepository;

    @InjectMocks
    private UserService userService;

    private UserEntity userOne = new UserEntity();

    @BeforeEach
    void setUp() {
        userOne.setUser_id(1l);
        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@gmail.com");
        userOne.setPassword("1234");
        iUserRepository.save(userOne);
    }

    @Test
    void testGetUsers() {
//        Given
        UserEntity userTwo = new UserEntity();
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("5678");

        BDDMockito.given(iUserRepository.findAll()).willReturn(List.of(userOne, userTwo));
//        When
        List<UserEntity> userList = userService.getUsers();
//        Then
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    void testGetEmptyUsers() {
//        Given
        BDDMockito.given(iUserRepository.findAll()).willReturn(Collections.emptyList());
//        When
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ExceptionNoDataFoundUser.class, () -> {
            userService.getUsers();
        });
//        Then
        Assertions.assertThat(exception.getMessage()).isEqualTo("No se encontraron usuarios");
    }

    @Test
    void testGetUser() {
//        Given
        BDDMockito.given(iUserRepository.findById(1l)).willReturn(Optional.of(userOne));
//        When
        UserEntity userFound = userService.getUser(userOne.getUser_id());
//        Then
        Assertions.assertThat(userFound).isNotNull();
        Assertions.assertThat(userFound.getUser_id()).isEqualTo(1l);
    }

    @Test
    void testGetEmptyUser() {
//        Given
//        BDDMockito.given(iUserRepository.findAll()).willReturn(Collections.emptyList());
//        When
        Exception exception = org.junit.jupiter.api.Assertions.assertThrows(ExceptionUserNotFound.class, () -> {
            userService.getUser(30l);
        });
//        Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(String.format("No existe el usuario con el ID: %d", 30l));
    }

    @Test
    void testCreateUser() {
//        Given
        UserEntity userTwo = new UserEntity();
        userTwo.setUser_id(2l);
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("5678");

        BDDMockito.given(iUserRepository.save(userTwo)).willReturn(userTwo);
//        When
        UserEntity userCreated = userService.createUser(userTwo);
//        Then
        Assertions.assertThat(userCreated).isNotNull();
    }

    @Test
    void testDeleteUser() {
//        Given
        Long userId = 2l;
        BDDMockito.willDoNothing().given(iUserRepository).deleteById(userId);
//        When
        userService.deleteUser(userId);
//        Then
        Mockito.verify(iUserRepository, Mockito.times(1)).deleteById(userId);
    }

    @Test
    void testUpdateUser() {
//        Given
        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@gmail.com");
        userOne.setPassword("4321");
        BDDMockito.given(iUserRepository.findById(1l)).willReturn(Optional.of(userOne));
//        When
        UserEntity updatedUser = userService.updateUser(userOne.getUser_id(), userOne.getUsername(), userOne.getEmail(), userOne.getPassword());
//        Then
        Assertions.assertThat(updatedUser.getUser_id()).isEqualTo(1l);
        Assertions.assertThat(updatedUser.getUsername()).isEqualTo("Santiago");
        Assertions.assertThat(updatedUser.getEmail()).isEqualTo("santiago10@gmail.com");
        Assertions.assertThat(updatedUser.getPassword()).isEqualTo("4321");
    }
}
