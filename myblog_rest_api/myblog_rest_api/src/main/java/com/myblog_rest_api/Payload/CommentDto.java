package com.myblog_rest_api.Payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 3, message = "Comment must be at least 3 Characters")
    private String body;
    @Email(message = "Email format is not correct")
    private String email;
    @NotEmpty
    @Size(min = 2,message = "Name must be at least 2 characters")
    private String name;
}
