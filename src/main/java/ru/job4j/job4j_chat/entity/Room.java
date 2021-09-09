package ru.job4j.job4j_chat.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Enter room'' name")
    private String name;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList<>();

}
