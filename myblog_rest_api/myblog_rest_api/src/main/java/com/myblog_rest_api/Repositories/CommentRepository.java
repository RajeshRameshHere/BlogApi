package com.myblog_rest_api.Repositories;

import com.myblog_rest_api.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {


 //to get list of comments based on columns other than id
 //built customized methods followed by camel casing
 //spring   automatically fetch the data, based on the column name that given in camel casing

    //here the required method is to find the list of comments based on the post id which is foreign key in this case
    //and it's not id which basically is primary key,
    //so we built the custom method followed by amel casing
    List<Comment>findByPostId(long podtId);


}
