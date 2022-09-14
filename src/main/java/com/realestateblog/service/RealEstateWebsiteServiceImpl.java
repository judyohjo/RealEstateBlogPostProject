package com.realestateblog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.realestateblog.model.Account;
import com.realestateblog.model.Category;
import com.realestateblog.model.Comment;
import com.realestateblog.model.Post;
import com.realestateblog.repository.AccountRepository;
import com.realestateblog.repository.CommentRepository;
import com.realestateblog.repository.PostRepository;

@Service
@Transactional
public class RealEstateWebsiteServiceImpl implements RealEstateWebsiteService {
	
	@Autowired
	private AccountRepository accountRepo;
	@Autowired
	private PostRepository postRepo;
	@Autowired
	private CommentRepository commentRepo;
	
	
    public Account getAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String loggedUser = auth.getName();
        return accountRepo.findByUsername(loggedUser);
    }
	
	/* Account(User) services */
	public List<Account> getAllUser() {
		return accountRepo.findAll();
	}
	
	public void saveUser(Account user) {
		accountRepo.save(user);
	}
	
	public Account getUser(Integer id) {
		return accountRepo.findById(id).get();
	}
	
	public void deleteUser(Integer id) {
		accountRepo.deleteById(id);
	}
	
	public Account findByUsername(String username) {
        return accountRepo.findByUsername(username);
    }
	
	
    public boolean checkPassword(String password, String hashPass) {

        BCryptPasswordEncoder bCrypt = new BCryptPasswordEncoder();

        if(bCrypt.matches(password, hashPass)) return true;

        return false;
    }

    public void updateUser(Account user) throws Exception {
    	if(user != null) {
			accountRepo.save(user);
		} else {
			throw new Exception("There is no such user");
		}
    }
	
	/* Post services */
	public List<Post> getAllPost() {
		return postRepo.findAll();
	}
	
	public void savePost(Post post) {
		postRepo.save(post);
	}
	
	public Post getPost(Integer id) {
		return postRepo.findById(id).get();
	}
	
	public void deletePost(Integer id) {
		postRepo.deleteById(id);
	}
	public void updatePost(Post post) throws Exception {
		if(post != null) {
			postRepo.save(post);
		} else {
			throw new Exception("Post is empty");
		}
        
    }
	
	/*public List<Post> findAllNewsPosts(int pageNumber, int rowPerPage) {
		List<Post> posts = new ArrayList<>();
		Pageable sortedByIdAsc = PageRequest.of(pageNumber - 1, rowPerPage, 
                Sort.by("id").ascending()).
        postRepo.findAll(sortedByIdAsc).forEach(posts::add);
        List<Post> newsPosts = new ArrayList<>();
        for(int i=0; i<posts.size(); i++) {
			if(posts.get(i).getPostCategory() == Category.News) {
				newsPosts.add(posts.get(i));
			}
		}
        
        postRepo.findAll(sortedByIdAsc).forEach(posts::add);
        return posts;
    }*/
	
	public Long count() {
        return postRepo.count();
    }
	
	public Post save(Post post) throws Exception {
		if(post != null) {
			return postRepo.save(post);
		} else {
			throw new Exception("Post is empty");
		}
    }
	
	public List<Post> search(String keyword) {
	    return postRepo.search(keyword);
	}
	
	
	/* Comment services */
	public List<Comment> getAllComment() {
		return commentRepo.findAll();
	}
	
	public void saveComment(Comment comment) {
		commentRepo.save(comment);
	}
	
	public Comment getComment(Integer id) {
		return commentRepo.findById(id).get();
	}

	
	
	

}
