package com.nxhu.eventosDeAutos.repository;

import com.nxhu.eventosDeAutos.model.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IEventRepository extends JpaRepository<EventEntity, Long> {
}
