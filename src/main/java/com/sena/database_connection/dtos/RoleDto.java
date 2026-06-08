package com.sena.database_connection.dtos;

import com.sena.database_connection.entities.Role;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RoleDto {

    private String name;

   
    public Role mapToEntity() {
        Role role = new Role();
        role.setName(this.name);
        return role;
    }
}