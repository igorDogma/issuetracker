package com.axmor.db.entityes;

import com.axmor.db.entityes.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "account")
@Getter
@Setter
public class Account extends PersistentEntity{

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public boolean isAdmin() {
        return Objects.equals(getRole(), Role.ROLE_ADMIN);
    }

    public boolean isManager() {
        return Objects.equals(getRole(), Role.ROLE_MANAGER);
    }
}
