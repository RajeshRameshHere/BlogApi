package com.myblog_rest_api.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "roles",uniqueConstraints = {@UniqueConstraint(columnNames =  "role")})
@Data
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(length = 10,nullable = false)
    private String name;


}
