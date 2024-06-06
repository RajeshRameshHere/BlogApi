package com.myblog_rest_api.Repositories;

import com.myblog_rest_api.Entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post,Long> {
}
