package com.realestateblog.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.realestateblog.model.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
	@Query("SELECT u FROM Post u")
    public List<Post> findAllPost();
	
    void deletePostById(int id);
    
    @Query(value = "SELECT c FROM Post c WHERE c.title LIKE '%' || :keyword || '%'"
            + " OR c.content LIKE '%' || :keyword || '%'")
    public List<Post> search(@Param("keyword") String keyword);
}
