package com.nxhu.eventosDeAutos.repository;

import com.nxhu.eventosDeAutos.model.AddressEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@DataJpaTest
public class AddressRepositoryTest {

    @Autowired
    private IAdressRepository iAdressRepository;

    private AddressEntity addressOne;

    @BeforeEach
    void setUp() {
        addressOne = AddressEntity.builder()
                .address_id(1l)
                .street("callePalermo 4444, Palermo")
                .city("CABA")
                .country("Argentina")
                .build();

        iAdressRepository.save(addressOne);
    }

    @Test
    void testGetAddresses() {
//        Given
        AddressEntity addressTwo = AddressEntity.builder()
                .address_id(2l)
                .street("callePalermo 2222, Palermo")
                .city("CABA")
                .country("Argentina")
                .build();
        iAdressRepository.save(addressTwo);
//        When
        List<AddressEntity> addressList = iAdressRepository.findAll();
//        Then
        Assertions.assertThat(addressList).isNotNull();
        Assertions.assertThat(addressList.size()).isEqualTo(2);
    }

    @Test
    void testGetAddress() {
//        When
        AddressEntity addressFound = iAdressRepository.findById(addressOne.getAddress_id()).get();
//        Then
        Assertions.assertThat(addressFound).isNotNull();
        Assertions.assertThat(addressFound.getAddress_id()).isEqualTo(1l);
    }

    @Test
    void testCreateAddress() {
//        Given
        AddressEntity addressTwo = AddressEntity.builder()
                .address_id(2l)
                .street("callePalermo 2222, Palermo")
                .city("CABA")
                .country("Argentina")
                .build();
//        When
        AddressEntity addressCreated = iAdressRepository.save(addressTwo);
//        Then
        Assertions.assertThat(addressCreated).isNotNull();
        Assertions.assertThat(addressCreated.getAddress_id()).isEqualTo(2);
    }

    @Test
    void testDeleteAddress() {
//        When
        iAdressRepository.deleteById(addressOne.getAddress_id());
        Optional<AddressEntity> addressDeleted = iAdressRepository.findById(addressOne.getAddress_id());
//        Then
        Assertions.assertThat(addressDeleted).isEmpty();
    }

    @Test
    void testUpdateAddress() {
//        Given & When
        AddressEntity addressFound = iAdressRepository.findById(addressOne.getAddress_id()).map(address -> {
            address.setStreet("callePalermo 9999, Palermo");
            address.setCity("CABA");
            address.setCountry("Argentina");

            return iAdressRepository.save(address);
        }).orElseThrow(() -> new RuntimeException("No existe esa direccion"));

//        Then
        Assertions.assertThat(addressFound.getAddress_id()).isEqualTo(1l);
        Assertions.assertThat(addressFound.getStreet()).isEqualTo("callePalermo 9999, Palermo");
    }
}
