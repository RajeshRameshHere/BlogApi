package com.myblog_rest_api.Payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDto
{
    private long id;
    @NotEmpty
    @Size(min=3,message = "Title must be more than 3 characters")
    private String title;
    @NotEmpty
    @Size(min =5,message = "Description must be more than 5 characters")
    private String description;
    @NotEmpty
    @Size(min =5,message = "Description must be more than 5 characters")
    private String content;

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
