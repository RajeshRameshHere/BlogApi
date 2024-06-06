package com.myblog_rest_api.Controller;

import com.myblog_rest_api.Payload.PostDto;
import com.myblog_rest_api.Payload.PostResponse;
import com.myblog_rest_api.Services.PostService;
import com.myblog_rest_api.Util.AppConstatns;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //localhost:8080/api/posts
    @PostMapping
    public ResponseEntity<Object>createPost(@Valid @RequestBody PostDto postDto){


    return  new ResponseEntity<>(postService.createpost(postDto), HttpStatus.CREATED);

    }

    //Beofre PostResponse class as response return type


//    @GetMapping()
//    public List<PostDto> getAllPost(@RequestParam(value = "pageNo",defaultValue = "0",required = false)int pageNo,
//                                    @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize){
//        return postService.getAllPosts(pageNo,pageSize);
//    }


//url:localhost:8080/api/posts?pageNo=0&pageSize=20
//url:localhost:8080/api/posts?pageNo=0&pageSize=20&sortby=title&sortDire=asc
@GetMapping()
public PostResponse getAllPost(@RequestParam(value = "pageNo",defaultValue = AppConstatns.DEFAULT_PAGE_NUMBER,required = false)int pageNo,
                               @RequestParam(value = "pageSize",defaultValue = AppConstatns.DEFAULT_PAGE_SIZE,required = false) int pageSize,
                               @RequestParam(value = "sortBy",defaultValue = AppConstatns.DEFAULT_SORT_BY,required = false) String sortBy,
                               @RequestParam(value = "sortDire",defaultValue = AppConstatns.DEFAULT_SORT_DIRE,required = false) String sortDire
                               ){
       return postService.getAllPosts(pageNo,pageSize,sortBy,sortDire);
   }
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> findPost(@PathVariable(name="id") long id){

       /* PostDto dto = postService.findPost(id);
        return ResponseEntity.ok(dto);
        */
        //simplified
       return ResponseEntity.ok(postService.findPost(id)) ;
    }
    @PutMapping({"/{id}"})
    public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto,@PathVariable(name="id" )long id){

        PostDto response = postService.editPost(postDto, id);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id){
        postService.deletePost(id);
        return new ResponseEntity<String>("Successfully deleted",HttpStatus.OK);
    }

}
