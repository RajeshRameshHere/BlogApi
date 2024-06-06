package com.myblog_rest_api.Payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostResponse {
    private List<PostDto> contents;
    private int pageNo;
    private int pageSize;
    private int totalPages;
    private long totalElements;
    private boolean lastPage;



}
