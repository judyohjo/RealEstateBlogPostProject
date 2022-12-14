package com.realestateblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.realestateblog.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
 
}
