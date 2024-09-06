package org.example.userauthenticationservice.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class User extends BaseModel{

    private String email;

    private String password;

    private State state;

//    public User (String email, String password, State state) {
//        this.email = email;
//        this.password = password;
//        this.state = state;
//    }

    @ManyToMany
    private Set<Role> roles = new HashSet<>();
}
