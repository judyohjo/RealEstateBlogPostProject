package com.realestateblog.service;

import java.util.List;
import com.realestateblog.model.Account;
import com.realestateblog.model.Comment;
import com.realestateblog.model.Post;

public interface RealEstateWebsiteService {
	
	public Account getAuthenticatedUser();
	
	
	/* Account(User) services */
	public List<Account> getAllUser();
	public void saveUser(Account user);
	public Account getUser(Integer id);
	public void deleteUser(Integer id);
	public Account findByUsername(String username);
	public boolean checkPassword(String password, String hashPass);
	public void updateUser(Account user) throws Exception;
	
	/* Post services */
	public List<Post> getAllPost();
	public void savePost(Post post);
	public Post getPost(Integer id);
	public void deletePost(Integer id);
	public void updatePost(Post post) throws Exception;
	//public List<Post> findAll(int pageNumber, int rowPerPage);
	public Long count();
	public Post save(Post post) throws Exception;
	public List<Post> search(String keyword);
	
	/* Comment services */
	public List<Comment> getAllComment();
	public void saveComment(Comment comment);
	public Comment getComment(Integer id);	
	
	
}
