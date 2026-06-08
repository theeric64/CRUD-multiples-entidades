package com.sena.database_connection.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sena.database_connection.dtos.ProfileDto;
import com.sena.database_connection.entities.Profile;
import com.sena.database_connection.entities.User;
import com.sena.database_connection.services.ProfileService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService service;

    public ProfileController(ProfileService service) {
        this.service = service;
    }

    @GetMapping
    public List<Profile> get() {
        return this.service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profile> getById(@PathVariable("id") Long id) {
        return this.service.porId(id)
                .map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @PostMapping
    public ResponseEntity<Profile> create(@RequestBody ProfileDto body) {
        Optional<User> user = this.service.buscarUsuario(body.getUserId());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = new Profile();
        profile.setUsername(body.getUsername());
        profile.setDescription(body.getDescription());
        profile.setUser(user.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.crear(profile));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profile> update(@PathVariable("id") Long id, @RequestBody ProfileDto body) {
        Optional<User> user = this.service.buscarUsuario(body.getUserId());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Profile profile = new Profile();
        profile.setId(id);
        profile.setUsername(body.getUsername());
        profile.setDescription(body.getDescription());
        profile.setUser(user.get());

        Profile profileUpdated = this.service.actualizar(profile);

        if (profileUpdated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profileUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Profile> delete(@PathVariable("id") Long id) {
        Profile profileDeleted = this.service.eliminar(id);

        if (profileDeleted == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profileDeleted);
    }
}