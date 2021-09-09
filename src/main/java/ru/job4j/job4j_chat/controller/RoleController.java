package ru.job4j.job4j_chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_chat.entity.Role;
import ru.job4j.job4j_chat.repository.RoleRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/role")
public class RoleController {

    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public List<Role> findAll(){
        return StreamSupport.stream(
                roleRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> findById(@PathVariable int id) {
        Optional<Role> role = roleRepository.findById(id);
        return new ResponseEntity<>(role.orElse(new Role()),
                role.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestBody Role role) {
        roleRepository.save(role);
        return ResponseEntity.ok().build();
    }

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody Role role) {
        return new ResponseEntity<>(roleRepository.save(role),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Role> delete(@PathVariable int id) {
        roleRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
