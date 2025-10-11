package com.ecommerce.inventory_service.repository;

import com.ecommerce.inventory_service.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
}
