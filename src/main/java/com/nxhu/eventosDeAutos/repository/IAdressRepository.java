package com.nxhu.eventosDeAutos.repository;

import com.nxhu.eventosDeAutos.model.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdressRepository extends JpaRepository<AddressEntity, Long> {
}
