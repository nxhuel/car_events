package com.nxhu.eventosDeAutos.service;

import com.nxhu.eventosDeAutos.model.AddressEntity;
import com.nxhu.eventosDeAutos.model.CategoryEntity;
import com.nxhu.eventosDeAutos.model.EventEntity;
import com.nxhu.eventosDeAutos.model.UserEntity;
import com.nxhu.eventosDeAutos.repository.IAdressRepository;
import com.nxhu.eventosDeAutos.repository.ICategoryRepository;
import com.nxhu.eventosDeAutos.repository.IEventRepository;
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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {

    @Mock
    private IEventRepository iEventRepository;

    @InjectMocks
    private EventService eventService;

    @Mock
    private IAdressRepository iAdressRepository;

    @Mock
    private ICategoryRepository iCategoryRepository;

    @Mock
    private IUserRepository iUserRepository;

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
        iEventRepository.save(eventOne);
    }

    @Test
    void testGetEvents() {
//        Given
        EventEntity eventTwo = EventEntity.builder()
                .event_id(2l)
                .name("Autos deportivos")
                .address(addressOne)
                .date(LocalDate.of(2024, 2, 15))
                .description("Evento de autos deportivos!")
                .category(categoryPublic)
                .creator(userOne)
                .build();
        iEventRepository.save(eventTwo);

        BDDMockito.given(iEventRepository.findAll()).willReturn(List.of(eventOne, eventTwo));
//        When
        List<EventEntity> eventList = eventService.getEvents();
//        Then
        Assertions.assertThat(eventList).isNotNull();
        Assertions.assertThat(eventList.size()).isEqualTo(2);
    }

    @Test
    void testGetEventsEmpty() {
//        Given
        BDDMockito.given(iEventRepository.findAll()).willReturn(Collections.emptyList());
//        When
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> eventService.getEvents(),
                "Se esperaba que se lanzara una excepcion"
        );
//        Then
        Assertions.assertThat(exception.getMessage()).isEqualTo("No se encontraron eventos");
    }

    @Test
    void testGetEvent() {
//        Given
        BDDMockito.given(iEventRepository.findById(1l)).willReturn(Optional.of(eventOne));

//        When
        EventEntity eventFound = eventService.getEvent(eventOne.getEvent_id());

//        Then
        Assertions.assertThat(eventFound).isNotNull();
        Assertions.assertThat(eventFound.getEvent_id()).isEqualTo(1l);
    }

    @Test
    void testGetEventEmpty() {
//        Given
        BDDMockito.given(iEventRepository.findById(eventOne.getEvent_id())).willReturn(Optional.empty());

//        When
        RuntimeException exception = org.junit.jupiter.api.Assertions.assertThrows(
                RuntimeException.class,
                () -> eventService.getEvent(eventOne.getEvent_id()));
//        Then
        Assertions.assertThat(exception.getMessage()).isEqualTo(String.format("No existe el evento con el ID: %d", eventOne.getEvent_id()));
    }

    @Test
    void testCreateEvent() {
//        Given
        EventEntity eventTwo = EventEntity.builder()
                .event_id(2l)
                .name("Autos deportivos")
                .address(addressOne)
                .date(LocalDate.of(2024, 2, 15))
                .description("Evento de autos deportivos!")
                .category(categoryPublic)
                .creator(userOne)
                .build();

        BDDMockito.given(iEventRepository.save(eventTwo)).willReturn(eventTwo);

//        When
        EventEntity eventCreate = eventService.createEvent(eventTwo);

//        Then
        Assertions.assertThat(eventCreate).isNotNull();
        Assertions.assertThat(eventCreate.getEvent_id()).isEqualTo(2l);
    }

    @Test
    void testDeleteEvent() {
//        Given
        Long event_id = 1l;
        BDDMockito.willDoNothing().given(iEventRepository).deleteById(event_id);

//        When
        eventService.deleteEvent(event_id);

//        Then
        BDDMockito.verify(iEventRepository, Mockito.times(1)).deleteById(event_id);
    }

    @Test
    void testUpdateEvent() {
//        Given
        eventOne.setName("Autos de prueba");
        eventOne.setAddress(addressOne);
        eventOne.setDate(LocalDate.of(2025, 6, 15));
        eventOne.setDescription("Probar autos");
        eventOne.setCategory(categoryPublic);
        eventOne.setCreator(userOne);

        BDDMockito.given(iEventRepository.findById(1l)).willReturn(Optional.of(eventOne));

//        When
        EventEntity updateEvent = eventService.updateEvent(eventOne.getEvent_id(), eventOne.getName(), eventOne.getAddress(), eventOne.getDate(), eventOne.getDescription(), eventOne.getCategory(), eventOne.getPrice(), eventOne.getImage(), eventOne.getCreator());

//        Then
        Assertions.assertThat(updateEvent.getEvent_id()).isEqualTo(1l);
        Assertions.assertThat(updateEvent.getName()).isEqualTo("Autos de prueba");
        Assertions.assertThat(updateEvent.getPrice()).isNull();
        Assertions.assertThat(updateEvent.getImage()).isNull();
    }
}
