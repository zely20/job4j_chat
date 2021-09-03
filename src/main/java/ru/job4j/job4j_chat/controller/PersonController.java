package ru.job4j.job4j_chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_chat.entity.Person;
import ru.job4j.job4j_chat.repository.PersonRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonRepository personRepository;

    public PersonController(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @GetMapping("/")
    public List<Person> findAll(){
        return StreamSupport.stream(
                personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Person> findById(@PathVariable int id) {
        Optional<Person> message = personRepository.findById(id);
        return new ResponseEntity<>(message.orElse(new Person()),
                message.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/")
    public ResponseEntity<Void> update (@RequestBody Person person) {
        personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<Person> create(@RequestBody Person person) {
        return new ResponseEntity<>(personRepository.save(person),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable int id) {
        Person person = new Person();
        person.setId(id);
        personRepository.delete(person);
        return ResponseEntity.ok().build();
    }
}
