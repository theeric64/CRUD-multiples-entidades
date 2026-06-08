package com.sena.database_connection.dtos;

import com.sena.database_connection.entities.Profile;
import com.sena.database_connection.entities.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileDto {

    private String username;
    private String description;
    private Long userId;

  
    public Profile mapToEntity(User user) {
        Profile profile = new Profile();
        profile.setUsername(this.username);
        profile.setDescription(this.description);
        profile.setUser(user);
        return profile;
    }
}