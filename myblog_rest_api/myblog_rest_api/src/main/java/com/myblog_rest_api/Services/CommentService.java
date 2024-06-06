package com.myblog_rest_api.Services;

import com.myblog_rest_api.Payload.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long post_id,CommentDto commentDto);

    List<CommentDto> getComments(long postId);

    CommentDto updateComment(long postId,long commentId,CommentDto commentDto);

    void deletComment(long postId, long commentId);
}
