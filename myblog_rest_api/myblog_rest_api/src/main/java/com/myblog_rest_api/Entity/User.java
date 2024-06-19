package com.myblog_rest_api.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "usersRecord",
 uniqueConstraints ={ @UniqueConstraint(columnNames = "email"),@UniqueConstraint(columnNames = "user_name")
})

public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false,length = 25)
    private String name;

    @Column(nullable = false,length = 25)
    private String email;

    @Column(nullable = false,length = 25)
    private String username;

    private String password;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(joinColumns = @JoinColumn(name = "user_id",referencedColumnName="id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;
}
