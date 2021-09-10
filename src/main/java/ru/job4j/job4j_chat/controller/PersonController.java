package ru.job4j.job4j_chat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_chat.dto.PersonDTO;
import ru.job4j.job4j_chat.entity.Person;
import ru.job4j.job4j_chat.repository.PersonRepository;
import ru.job4j.job4j_chat.service.PersonService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final ObjectMapper objectMapper;
    private final PersonRepository personRepository;
    @Autowired
    private ModelMapper modelMapper;
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class.getSimpleName());

    public PersonController(PersonService personService, ObjectMapper objectMapper, PersonRepository personRepository) {
        this.personService = personService;
        this.objectMapper = objectMapper;
        this.personRepository = personRepository;
    }

    @GetMapping
    public List<Person> findAll(){
        return personService.findAll();
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
    public PersonDTO findById(@PathVariable int id) {
        return modelMapper.map(personService.findById(id), PersonDTO.class);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Person update (@RequestBody Person person) {
        return personService.update(person);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Person create(@RequestBody Person person) {
        return personService.create(person);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Person> delete(@PathVariable int id) {
        return personService.delete(id);
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
