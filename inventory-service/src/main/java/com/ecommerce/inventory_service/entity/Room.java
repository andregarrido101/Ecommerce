package com.ecommerce.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "room_name")
    private String roomName;

    @Column(name = "capacity")
    private Boolean capacity;
}
