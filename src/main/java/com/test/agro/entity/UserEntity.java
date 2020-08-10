package com.test.agro.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(value = "users")
public class UserEntity {
    @Id
    @Indexed(unique = true)
    String id;
    @Indexed(unique = true)
    String username;
    String password;
    @Indexed(unique = true)
    String email;
    String role;
    String roleId;
}
