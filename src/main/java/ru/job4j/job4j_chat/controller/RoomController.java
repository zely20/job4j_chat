package ru.job4j.job4j_chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.job4j_chat.entity.Role;
import ru.job4j.job4j_chat.entity.Room;
import ru.job4j.job4j_chat.repository.RoleRepository;
import ru.job4j.job4j_chat.repository.RoomRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/room")
public class RoomController {

    private final RoomRepository roomRepository;

    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping
    public List<Room> findAll(){
        return StreamSupport.stream(
                roomRepository.findAll().spliterator(), false
        ).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> findById(@PathVariable int id) {
        Optional<Room> role = roomRepository.findById(id);
        return new ResponseEntity<>(role.get(),
                role.isEmpty() ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update (@RequestBody Room room) {
        roomRepository.save(room);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/")
    public ResponseEntity<Room> create(@RequestBody Room room) {
        return new ResponseEntity<>(roomRepository.save(room),
                HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Room> delete(@PathVariable int id) {
        roomRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
