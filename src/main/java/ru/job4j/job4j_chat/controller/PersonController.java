package ru.job4j.job4j_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.job4j.job4j_chat.entity.Person;
import ru.job4j.job4j_chat.repository.PersonRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final ObjectMapper objectMapper;
    private final PersonRepository personRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    public PersonController(ObjectMapper objectMapper, PersonRepository personRepository) {
        this.objectMapper = objectMapper;
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> findAll(){
        return StreamSupport.stream(
                personRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/res/{id}")
    public ResponseEntity<String> findByIdResp(@PathVariable int id) {
        var person = personRepository.findById(id);
        Object body = new HashMap<>(){{
            put("nickname", person.get().getNickname());
        }};
        return new ResponseEntity(
                body,
                new MultiValueMapAdapter<>(Map.of("Job4jCustomHeader", List.of("job4j"))),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable int id) {
        return personRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Person is not found."));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestBody Person person) {
        var username = person.getNickname();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        personRepository.save(person);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Person> create(@RequestBody Person person) {
        var username = person.getNickname();
        var password = person.getPassword();
        if (username == null || password == null) {
            throw new NullPointerException("Username and password mustn't be empty");
        }
        if(username.length() < 4) {
            throw new IllegalArgumentException("Invalid nickname, Nickname must be more 5 characters.");
        }
        return new ResponseEntity<>(personRepository.save(person),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> delete(@PathVariable int id) {
        personRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @ExceptionHandler(value = { IllegalArgumentException.class })
    public void exceptionHandler(Exception e, HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType("application/json");
        response.getWriter().write(objectMapper.writeValueAsString(new HashMap<>() { {
            put("message", e.getMessage());
            put("type", e.getClass());
        }}));
        LOGGER.error(e.getLocalizedMessage());
    }
}
