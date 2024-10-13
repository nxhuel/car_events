package com.nxhu.eventosDeAutos.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.service.IUserService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(controllers = UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService iUserService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testGetUsers() throws Exception {
//        Given
        List<UserEntity> userList = new ArrayList<>();
        UserEntity userOne = new UserEntity();
        userOne.setUser_dni(1l);
        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@gmail.com");
        userOne.setPassword("1234");
        userList.add(userOne);

        UserEntity userTwo = new UserEntity();
        userTwo.setUser_dni(2l);
        userTwo.setUsername("Delfina");
        userTwo.setEmail("delfina10@gmail.com");
        userTwo.setPassword("5678");
        userList.add(userTwo);

        UserEntity userThree = new UserEntity();
        userThree.setUser_dni(3l);
        userThree.setUsername("Santino");
        userThree.setEmail("santino10@gmail.com");
        userThree.setPassword("9101112");
        userList.add(userThree);

        BDDMockito.given(iUserService.getUsers()).willReturn(userList);
//        When
        ResultActions respone = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userList)));
//        Then
        respone.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(userList.size())));
    }

    @Test
    void testGetUser() throws Exception {
//        Given
        UserEntity userOne = new UserEntity();
        userOne.setUser_dni(1l);
        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@gmail.com");
        userOne.setPassword("1234");

        BDDMockito.given(iUserService.getUser(userOne.getUser_dni())).willReturn(userOne);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/user/{userId}", userOne.getUser_dni())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOne)));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(userOne.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userOne.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(userOne.getPassword())));
    }

    @Test
    void testCreateUser() throws Exception{
//        Given
        UserEntity userOne = new UserEntity();
        userOne.setUser_dni(1l);
        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@gmail.com");
        userOne.setPassword("1234");
        BDDMockito.given(iUserService.createUser(any(UserEntity.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.post("/v1/api/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOne)));
//        When
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is(userOne.getUsername())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is(userOne.getEmail())))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is(userOne.getPassword())));
    }

    @Test
    void testDeleteUser() throws Exception {
//        Given
        Long userId = 1l;
        BDDMockito.willDoNothing().given(iUserService).deleteUser(userId);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.delete("/v1/api/user/delete/{userId}", userId));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void testUpdateUser() throws Exception {
//        Given
        UserEntity userOne = new UserEntity();
        userOne.setUser_dni(1l);
        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@gmail.com");
        userOne.setPassword("1234");

        BDDMockito.given(iUserService.getUser(userOne.getUser_dni())).willReturn(userOne);

        userOne.setUsername("Santiago");
        userOne.setEmail("santiago10@yahoo.com");
        userOne.setPassword("5678");

        BDDMockito.given(iUserService.getUser(userOne.getUser_dni())).willReturn(userOne);
        BDDMockito.given(iUserService.updateUser(userOne.getUser_dni(), userOne.getUsername(), userOne.getEmail(), userOne.getPassword()))
                .willAnswer((invocation) -> invocation.getArgument(0));
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.put("/v1/api/user/update/{userId}", userOne.getUser_dni())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userOne)));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", CoreMatchers.is("Santiago")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email", CoreMatchers.is("santiago10@yahoo.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password", CoreMatchers.is("5678")));
    }
}
