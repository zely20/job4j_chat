package ru.job4j.job4j_chat.repository;

import org.springframework.data.repository.CrudRepository;
import ru.job4j.job4j_chat.entity.Person;

public interface PersonRepository extends CrudRepository<Person,Integer>{
}
