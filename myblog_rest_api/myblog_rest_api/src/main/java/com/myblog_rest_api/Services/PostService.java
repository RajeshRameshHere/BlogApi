package com.myblog_rest_api.Services;

import com.myblog_rest_api.Payload.PostDto;
import com.myblog_rest_api.Payload.PostResponse;

public interface PostService {

    PostDto createpost (PostDto postDto);

   public PostResponse getAllPosts(int pageNo, int pageSize,String sortBy,String sortDire);


    PostDto findPost(long id);

    PostDto editPost(PostDto postDto, long id);

    void deletePost(long id);
}
