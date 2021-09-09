package ru.job4j.job4j_chat.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
    @NotBlank(message = "Nickname must not be empty")
    private String nickname;
    @NotBlank(message = "Nickname must not be empty")
    private String password;
    @NotBlank(message = "Nickname must not be empty")
    @Email
    private String email;
    @OneToMany(mappedBy = "person")
    private List<Message> messages = new ArrayList<>();
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name="persons_roles",
            joinColumns=@JoinColumn(name="persons_id"),
            inverseJoinColumns=@JoinColumn(name="roles_id"))
    private Set<Role> roles = new HashSet<>();
}
