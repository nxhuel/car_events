package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.exception.user.ExceptionNoDataFoundUser;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.repository.IUserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

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
    }

    @Test
    void testGetUsers() {
//        Given
        UserEntity userTwo = new UserEntity();
        userTwo.setUser_id(2l);
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("1234");

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
}
