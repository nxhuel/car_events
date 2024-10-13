package com.nxhu.eventosDeAutos.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nxhu.eventosDeAutos.model.AddressEntity;
import com.nxhu.eventosDeAutos.model.CategoryEntity;
import com.nxhu.eventosDeAutos.model.EventEntity;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.repository.IAdressRepository;
import com.nxhu.eventosDeAutos.repository.ICategoryRepository;
import com.nxhu.eventosDeAutos.repository.IEventRepository;
import com.nxhu.eventosDeAutos.repository.IUserRepository;
import com.nxhu.eventosDeAutos.service.IEventService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(controllers = EventController.class)
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEventService iEventService;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private IAdressRepository iAdressRepository;

    @Mock
    private ICategoryRepository iCategoryRepository;

    @Mock
    private IUserRepository iUserRepository;

    @Mock
    private IEventRepository iEventRepository;

    private AddressEntity addressOne;
    private CategoryEntity categoryPrivate;
    private CategoryEntity categoryPublic;
    private UserEntity userOne;
    private EventEntity eventOne;

    @BeforeEach
    void setUp() {
        addressOne = AddressEntity.builder()
                .street("Palermo")
                .city("CABA")
                .country("Argentina")
                .build();
        iAdressRepository.save(addressOne);

        categoryPrivate = CategoryEntity.builder()
                .isPrivate(true)
                .build();
        iCategoryRepository.save(categoryPrivate);

        categoryPublic = CategoryEntity.builder()
                .isPrivate(false)
                .build();
        iCategoryRepository.save(categoryPublic);

        userOne = UserEntity.builder()
                .user_dni(45120130l)
                .username("Facundo")
                .email("facundo10@gmail.com")
                .password("1234")
                .build();
        iUserRepository.save(userOne);

        eventOne = EventEntity.builder()
                .event_id(1l)
                .name("Autos clasicos")
                .address(addressOne)
                .date(LocalDate.of(2024, 2, 15))
                .description("Evento de autos clasicos y de coleccion!")
                .category(categoryPrivate)
                .price(BigDecimal.valueOf(500))
                .image("https://th.bing.com/th/id/OIP.mSw6btfXe1Aqt4JTQ4FLbAHaEK?rs=1&pid=ImgDetMain")
                .creator(userOne)
                .build();
    }

    @Test
    void testGetEvents() throws Exception {
//        Given
        List<EventEntity> eventList = new ArrayList<>();

        EventEntity eventTwo = EventEntity.builder()
                .event_id(2l)
                .name("Autos deportivos")
                .address(addressOne)
                .date(LocalDate.of(2024, 2, 15))
                .description("Evento de autos deportivos!")
                .category(categoryPublic)
                .creator(userOne)
                .build();

        eventList.add(eventOne);
        eventList.add(eventTwo);

        BDDMockito.given(iEventService.getEvents()).willReturn(eventList);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventList)));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(eventList.size())));
    }

    @Test
    void testGetEvent() throws Exception {
//        Given
        iEventRepository.save(eventOne);

        BDDMockito.given(iEventService.getEvent(eventOne.getEvent_id())).willReturn(eventOne);
//        When
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders.get("/v1/api/event/{event_id}", eventOne.getEvent_id())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(eventOne)));
//        Then
        response.andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(eventOne.getName())));
    }

}
