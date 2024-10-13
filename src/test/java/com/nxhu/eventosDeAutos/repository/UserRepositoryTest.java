package com.nxhu.eventosDeAutos.repository;

import com.nxhu.eventosDeAutos.model.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private IUserRepository iUserRepository;

    private UserEntity userOne = new UserEntity();

    @BeforeEach
    void setUp() {
        userOne.setUser_dni(1l);
        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@gmail.com");
        userOne.setPassword("1234");
        iUserRepository.save(userOne);
    }

    @Test
    void testGetUsers() {
//        Given
        UserEntity userTwo = new UserEntity();
        userTwo.setUser_dni(2l);
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("5678");
        iUserRepository.save(userTwo);
//        When
        List<UserEntity> userList = iUserRepository.findAll();
//        Then
        Assertions.assertThat(userList).isNotNull();
        Assertions.assertThat(userList.size()).isEqualTo(2);
    }

    @Test
    void testGetUser() {
//        Given
        iUserRepository.save(userOne);
//        When
        UserEntity bringUser = iUserRepository.findById(userOne.getUser_dni()).get();
//        Then
        Assertions.assertThat(bringUser).isNotNull();
        Assertions.assertThat(bringUser.getUser_dni()).isEqualTo(userOne.getUser_dni());
    }

    @Test
    void testCreateUser() {
//        Given
        UserEntity userTwo = new UserEntity();
        userTwo.setUser_dni(2l);
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("5678");
//        When
        UserEntity createUser = iUserRepository.save(userTwo);
//        Then
        Assertions.assertThat(createUser).isNotNull();
        Assertions.assertThat(createUser.getUser_dni()).isEqualTo(2l);
    }

    @Test
    void testDeleteUser() {
//        Given
        UserEntity userTwo = new UserEntity();
        userTwo.setUser_dni(2l);
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("5678");
        iUserRepository.save(userTwo);
//        When
        iUserRepository.deleteById(userTwo.getUser_dni());
        Optional<UserEntity> optionalUser = iUserRepository.findById(userTwo.getUser_dni());
//        Then
        Assertions.assertThat(optionalUser).isEmpty();
    }

    @Test
    void updateUser() {
//        Given
        UserEntity userTwo = new UserEntity();
        userTwo.setUser_dni(2l);
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("5678");
        iUserRepository.save(userTwo);
//        When
        UserEntity bringUser = iUserRepository.findById(userTwo.getUser_dni()).get();
        bringUser.setUsername("Delfina");
        bringUser.setEmail("delfina10@yahoo.com");
        bringUser.setPassword("9101112");
        UserEntity updateUser = iUserRepository.save(bringUser);
//        Then
        Assertions.assertThat(updateUser.getUser_dni()).isEqualTo(2l);
        Assertions.assertThat(updateUser.getUsername()).isEqualTo("Delfina");
        Assertions.assertThat(updateUser.getEmail()).isEqualTo("delfina10@yahoo.com");
        Assertions.assertThat(updateUser.getPassword()).isEqualTo("9101112");
    }
}
