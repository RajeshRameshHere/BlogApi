package com.myblog_rest_api.Controller;

import com.myblog_rest_api.Payload.CommentDto;
import com.myblog_rest_api.Services.impl.CommentServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {

    private CommentServiceImpl commentServ;

    public CommentController(CommentServiceImpl commentServ) {
        this.commentServ = commentServ;
    }
@PostMapping("/posts/{postId}/comments")
public ResponseEntity<CommentDto> comment(@PathVariable(name = "postId")long postId, @Valid @RequestBody CommentDto commentDto){
    CommentDto dto = commentServ.createComment(postId, commentDto);


    return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }
 @GetMapping("/posts/{postId}/comments")
 public List<CommentDto> getAllComments(@PathVariable(name="postId") long postId){

       List<CommentDto> dto = commentServ.getComments(postId);
        return dto;

    }

 @PutMapping("/posts/{postId}/comments/{id}")
 public ResponseEntity<CommentDto> updateComment(@PathVariable("postId") long postId,
                                              @PathVariable("id") long commentId,@Valid @RequestBody CommentDto commentDto){

            CommentDto dto = commentServ.updateComment(postId, commentId, commentDto);
            return new ResponseEntity<>(dto,HttpStatus.OK);
        }

 @DeleteMapping("/posts/{postId}/comments/{id}")
 public ResponseEntity<String> deleteComment(@PathVariable("postId") long postId,@PathVariable("id") long commentId){

        commentServ.deletComment(postId,commentId);
        return new ResponseEntity<>("Comment Deleted Sucessfully",HttpStatus.OK);
        }

}
