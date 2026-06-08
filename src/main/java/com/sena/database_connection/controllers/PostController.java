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

import com.sena.database_connection.dtos.PostDto;
import com.sena.database_connection.entities.Post;
import com.sena.database_connection.entities.User;
import com.sena.database_connection.services.PostService;

@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService service;

    public PostController(PostService service) {
        this.service = service;
    }

    @GetMapping
    public List<Post> get() {
        return this.service.obtenerTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post> getById(@PathVariable("id") Long id) {
        return this.service.porId(id)
                .map(ResponseEntity::ok) 
                .orElseGet(() -> ResponseEntity.notFound().build()); 
    }

    @PostMapping
    public ResponseEntity<Post> create(@RequestBody PostDto body) {
        Optional<User> user = this.service.buscarUsuario(body.getUserId());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = body.mapToEntity(user.get());

        return ResponseEntity.status(HttpStatus.CREATED).body(this.service.crear(post));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(@PathVariable("id") Long id, @RequestBody PostDto body) {
        Optional<User> user = this.service.buscarUsuario(body.getUserId());

        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Post post = body.mapToEntity(user.get());
        post.setId(id);

        Post postUpdated = this.service.actualizar(post);

        if (postUpdated == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(postUpdated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Post> delete(@PathVariable("id") Long id) {
        Post postDeleted = this.service.eliminar(id);

        if (postDeleted == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(postDeleted);
    }
}