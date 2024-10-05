package com.nxhu.eventosDeAutos.repository;

import com.nxhu.eventosDeAutos.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
}
