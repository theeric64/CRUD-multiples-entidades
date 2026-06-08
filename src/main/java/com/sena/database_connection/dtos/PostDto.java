package com.sena.database_connection.dtos;

import com.sena.database_connection.entities.Post;
import com.sena.database_connection.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostDto {

    private String title;
    private String description;
    private Integer likes;
    private Long userId;

    
    public Post mapToEntity(User user) {
        Post post = new Post();
        post.setTitle(this.title);
        post.setDescription(this.description);
        post.setLikes(this.likes);
        post.setUser(user);
        return post;
    }
}