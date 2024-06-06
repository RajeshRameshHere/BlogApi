package com.myblog_rest_api.Services.impl;

import com.myblog_rest_api.Entity.Comment;
import com.myblog_rest_api.Entity.Post;
import com.myblog_rest_api.Exception.ResourceNotFoundException;
import com.myblog_rest_api.Payload.CommentDto;
import com.myblog_rest_api.Repositories.CommentRepository;
import com.myblog_rest_api.Repositories.PostRepository;
import com.myblog_rest_api.Services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepo;
    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public CommentServiceImpl(CommentRepository commentRepo, PostRepository postRepo,ModelMapper modelMapper) {
        this.commentRepo = commentRepo;
        this.postRepo = postRepo;
        this.modelMapper=modelMapper;
    }

    @Override
    public CommentDto createComment(long post_id, CommentDto commentDto) {

        Comment comment = mapToCommentEntity(commentDto);

        Post post = postRepo.findById(post_id).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", post_id)
        );

        comment.setPost(post);
        Comment newComment = commentRepo.save(comment);

        return  mapToCommentDto(newComment);
    }

    @Override
    public List<CommentDto> getComments(long postId) {

        List<Comment> comments = commentRepo.findByPostId(postId);

        return comments.stream().map(comment->mapToCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));

        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("comment", "id", commentId)
        );
        comment.setPost(post);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment updatedComment = commentRepo.save(comment);
        return  mapToCommentDto(updatedComment);
    }

    @Override
    public void deletComment(long postId, long commentId) {
        postRepo.findById(postId).orElseThrow(
                ()->new ResourceNotFoundException("post","id",postId));
        Comment comment = commentRepo.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "id", commentId));
        commentRepo.delete(comment);

    }

    public CommentDto mapToCommentDto(Comment comment){

        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);

//        CommentDto commentdto=new CommentDto();
//        commentdto.setId(comment.getId());
//        commentdto.setName(comment.getName());
//        commentdto.setEmail(comment.getEmail());
//        commentdto.setBody(comment.getBody());
        return commentDto;
    }
    public Comment mapToCommentEntity(CommentDto commentDto){

        Comment comment = modelMapper.map(commentDto, Comment.class);
//       Comment comment=new Comment();
//       comment.setId(commentDto.getId());
//       comment.setName(commentDto.getName());
//       comment.setEmail(commentDto.getEmail());
//       comment.setBody(commentDto.getBody());
    return comment;
    }
}
