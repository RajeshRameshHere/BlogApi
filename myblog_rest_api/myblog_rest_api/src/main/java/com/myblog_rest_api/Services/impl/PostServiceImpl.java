package com.myblog_rest_api.Services.impl;

import com.myblog_rest_api.Entity.Post;
import com.myblog_rest_api.Exception.ResourceNotFoundException;
import com.myblog_rest_api.Payload.PostDto;
import com.myblog_rest_api.Payload.PostResponse;
import com.myblog_rest_api.Repositories.PostRepository;
import com.myblog_rest_api.Services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private PostRepository postRepo;
    private ModelMapper modelMapper;



    public PostServiceImpl(PostRepository postRepo,ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public PostDto createpost(PostDto postDto) {

        Post post=maptoEntity(postDto);
        Post newPost = postRepo.save(post);


        PostDto postResponse=maptoDto(newPost);
        return postResponse;
    }

    //Before pagination

//    @Override
//    public List<PostDto> getAllPosts(){
//        List<Post> posts = postRepo.findAll();
//
//        return   posts.stream().map(p->maptoDto(p)).collect(Collectors.toList());;
//    }

    @Override                       //args to supply page constraints no. and size
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDire) {

        Sort sortByN = sortDire.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

//calling Pageable interface to request the pagination support

        Pageable pageable = PageRequest.of(pageNo, pageSize, sortByN);

        //we pass pageable object into the findAll method,and it gives page of posts in return type
        //then store it in allPosts as an object

        Page<Post> allPosts = postRepo.findAll(pageable);

        //we need to convert the page<posts> into List in order to iterate in the stream,
        //so we use get content method to covert it into the List

        List<Post> content = allPosts.getContent();

        //using stream to iterate all the posts and converting into dto object using mapToDto method
        List<PostDto> contents = content.stream().map(p -> maptoDto(p)).collect(Collectors.toList());

        PostResponse postResponse= new PostResponse();
        postResponse.setContents(contents);
        postResponse.setPageNo(allPosts.getNumber());
        postResponse.setPageSize(allPosts.getSize());
        postResponse.setTotalPages(allPosts.getTotalPages());
        postResponse.setTotalElements(allPosts.getTotalElements());
        postResponse.setLastPage(allPosts.isLast());

        return postResponse;

    }
    @Override
    public PostDto findPost(long id) {
//without handling the exception code looks like this

      /*  Optional<Post> postbyid = postRepo.findById(id);
        Post post = postbyid.get();
        PostDto postDto = maptoDto(post);

        return postDto;*/
 //handeled with exception

        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id)
        );
        PostDto dto = maptoDto(post);
        return dto;
    }

    @Override
    public PostDto editPost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id));
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        Post updatedPost = postRepo.save(post);
        return maptoDto(updatedPost);
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", id));
        postRepo.deleteById(id);
    }

    public Post maptoEntity(PostDto postDto){

        Post post = modelMapper.map(postDto, Post.class);

        //before using modelMapper class
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
        return post;
    }
    public PostDto maptoDto(Post post){
        PostDto dto = modelMapper.map(post, PostDto.class);

        //before using modelMapper class
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;

    }
}
