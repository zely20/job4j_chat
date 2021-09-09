package ru.job4j.job4j_chat.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nickname;
    private String password;
    private String email;
    @OneToMany(mappedBy = "person")
    private List<Message> messages = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="persons_roles",
            joinColumns=@JoinColumn(name="persons_id"),
            inverseJoinColumns=@JoinColumn(name="roles_id"))
    private Set<Role> roles = new HashSet<>();
}
