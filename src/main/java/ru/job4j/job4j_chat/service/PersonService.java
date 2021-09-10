package ru.job4j.job4j_chat.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.job4j_chat.entity.Person;
import ru.job4j.job4j_chat.repository.PersonRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class PersonService {

    private final PersonRepository personRepository;

    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return StreamSupport.stream(
                personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    public Person findById(int id) {
        return personRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Person is not found."));
    }

    public Person update (Person person) {
        var username = person.getNickname();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        return personRepository.save(person);
    }

    public Person create(Person person) {
        var username = person.getNickname();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if(username.length() < 4) {
            throw new IllegalArgumentException("Invalid nickname, Nickname must be more 5 characters.");
        }
        return personRepository.save(person);
    }

    public ResponseEntity<Person> delete(int id) {
        personRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
