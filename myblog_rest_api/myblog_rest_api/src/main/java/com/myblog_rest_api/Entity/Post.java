package com.myblog_rest_api.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor  //for constructor
@AllArgsConstructor
@Table(name = "post",
uniqueConstraints = {@UniqueConstraint(columnNames = "title")})//used to define unique column, here we can mark multiple columns
@Getter
@Setter     //used for generating getters and setters using annotation           //as unique in a single line of annotation(reducing code)
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name ="title" ,nullable = false)
    private String title;
    @Column(name ="description" ,nullable = false)
    private String description;
    @Lob
    @Column(name ="content" ,nullable = false)
    private  String content;
    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL,orphanRemoval = true)
    Set<Comment> Comments=new HashSet<>();

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getContent() {
        return content;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
