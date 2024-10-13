package com.nxhu.eventosDeAutos.repository;

import com.nxhu.eventosDeAutos.model.AddressEntity;
import com.nxhu.eventosDeAutos.model.CategoryEntity;
import com.nxhu.eventosDeAutos.model.EventEntity;
import com.nxhu.eventosDeAutos.model.UserEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EventRepositoryTest {

    @Autowired
    private IEventRepository iEventRepository;

    @Autowired
    private IAdressRepository iAdressRepository;

    @Autowired
    private ICategoryRepository iCategoryRepository;

    @Autowired
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
                .name("Autos deportivos")
                .address(addressOne)
                .date(LocalDate.of(2024, 2, 15))
                .description("Evento de autos deportivos!")
                .category(categoryPublic)
                .creator(userOne)
                .build();

        iEventRepository.save(eventTwo);
//        When
        List<EventEntity> eventList = iEventRepository.findAll();
//        Then
        Assertions.assertThat(eventList).isNotNull();
        Assertions.assertThat(eventList.size()).isEqualTo(2);
    }

    @Test
    void testGetEvent() {
//        Given

//        When
        EventEntity foundEvent = iEventRepository.findById(eventOne.getEvent_id()).get();
//        Then
        Assertions.assertThat(foundEvent).isNotNull();
        Assertions.assertThat(foundEvent.getEvent_id()).isEqualTo(1l);
    }

    @Test
    void testCreateEvent() {
//        Given
        EventEntity eventTwo = EventEntity.builder()
                .name("Autos deportivos")
                .address(addressOne)
                .date(LocalDate.of(2024, 2, 15))
                .description("Evento de autos deportivos!")
                .category(categoryPublic)
                .creator(userOne)
                .build();

        Optional<EventEntity> eventTwoIsFound = iEventRepository.findById(2l);
//        When
        EventEntity eventCreate = iEventRepository.save(eventTwo);

//        Then
        Assertions.assertThat(eventTwoIsFound).isEmpty();
        Assertions.assertThat(eventCreate).isNotNull();
        Assertions.assertThat(eventCreate.getEvent_id()).isEqualTo(2l);
    }

    @Test
    void testDeleteEvent() {
//        Given
        EventEntity eventFound = iEventRepository.findById(eventOne.getEvent_id()).get();
//        When
        iEventRepository.deleteById(eventOne.getEvent_id());
        Optional<EventEntity> eventDelete = iEventRepository.findById(eventOne.getEvent_id());
//        Then
        Assertions.assertThat(eventFound).isNotNull();
        Assertions.assertThat(eventDelete).isEmpty();
    }

    @Test
    void testUpdateEvent() {
//        Given
        EventEntity eventFound = iEventRepository.findById(eventOne.getEvent_id()).get();
        eventFound.setName("Autos clasicos");
        eventFound.setAddress(addressOne);
        eventFound.setDate(LocalDate.of(2024, 2, 15));
        eventFound.setDescription("Evento de autos clasicos y de coleccion!");
        eventFound.setCategory(categoryPublic);
        eventFound.setCreator(userOne);
//        When
        EventEntity updateEvent = iEventRepository.save(eventOne);

//        Then
        Assertions.assertThat(eventFound).isNotNull();
        Assertions.assertThat(updateEvent.getCategory()).isEqualTo(categoryPublic);
    }
}
