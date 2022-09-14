package com.realestateblog.controller;


import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.realestateblog.model.Account;
import com.realestateblog.model.Category;
import com.realestateblog.model.Comment;
import com.realestateblog.model.FileUploadUtil;
import com.realestateblog.model.Post;
import com.realestateblog.service.RealEstateWebsiteService;

@Controller
public class RealEstateWebsiteController {
	@Autowired
	RealEstateWebsiteService blogPostService;
	
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final int ROW_PER_PAGE = 5;
	
    
	/* View homepage */
	@GetMapping(value= {"", "/", "/homepage"})
    public String viewHomePage(Model model) {
		List<Post> posts = blogPostService.getAllPost();
		List<Post> newsPosts = new ArrayList<Post>();
		for(int i=0; i<posts.size(); i++) {
			if(posts.get(i).getPostCategory() == Category.News) {
				newsPosts.add(posts.get(i));
			}
		}
		Collections.reverse(newsPosts);
		List<Post> sortedPostsUpToThree = new ArrayList<Post>();
		for(int i=0; i<3; i++) {
			sortedPostsUpToThree.add(newsPosts.get(i));
		}
	    model.addAttribute("posts", sortedPostsUpToThree);
        return "homepage";
    }
	
	/* View login page */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	/* View About us page */
	@GetMapping(value= {"/about_us"})
    public String viewAboutUs() {
        return "about_us";
    }
	
	/* Calculation page */
	@GetMapping("/calculation")
	public String calculation() {
		return "calculation";
	}
	
	/* Registration form */
	@GetMapping("/register")
	public String showRegistrationForm(Model model) {
	    model.addAttribute("Account", new Account());
	    return "register";
	}
	
	/* Process Registration */
	@PostMapping("/process_register")
	public String processRegister(Account account) {
	    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	    String encodedPassword = passwordEncoder.encode(account.getPassword());
	    account.setPassword(encodedPassword);
	    blogPostService.saveUser(account);
	    return "register_success";
	}
	
	/* View account */
	@GetMapping("/accounts")
    public String showMyAccountPage(Model model) {
        Account authUser = blogPostService.getAuthenticatedUser();
        model.addAttribute("user", authUser);
        return "accounts";
    }
	
	/* Edit account */
	@GetMapping(value = {"/accounts/edit"})
    public String showEditAccountPage(Account editAccountForm, Model model) {
        Account authUser = blogPostService.getAuthenticatedUser();
        model.addAttribute("user", authUser);
        return "edit_account";
    }
	
	@PostMapping(value = {"/accounts/edit"})
    public String setAccountDetails(Model model, Principal principal, @ModelAttribute("user") Account user){
        Account authUser = null;
        authUser = blogPostService.getAuthenticatedUser();      
        try {        	
        	BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    	    String encodedPassword = passwordEncoder.encode(user.getPassword());        	
        	authUser.setUsername(principal.getName());
        	authUser.setPassword(encodedPassword);
        	authUser.setConfirmPassword(user.getConfirmPassword());
        	authUser.setEmail(user.getEmail());
        	authUser.setContact(user.getContact());
            blogPostService.updateUser(authUser);
            return "redirect:/accounts";

	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	        String errorMessage = ex.getMessage();
	        logger.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	        return "redirect:/homepage";
	    }
    }
	
	/* Add new post */
	@GetMapping(value= {"/add_new_post", "/all_news_post/add_new_post", "/all_board_post/add_new_post", "/search/add_new_post"})
	public String showPostForm(Model model) {
		Post newPost = new Post();
	    model.addAttribute("categories", Category.values());
	    model.addAttribute("Post", newPost);
	    return "add_new_post";
	}
	
	/* Process adding new post */
	@PostMapping("/process_post")
	public String processPost(Post post, Model model, Principal principal, @RequestParam("image") MultipartFile multipartFile) throws IOException {
		post.setCreatedTime(new Date());
		post.setUpdatedTime(new Date());
		post.setAccount(principal.getName());
		String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
		post.setPhotoImage(fileName);
        blogPostService.savePost(post);
        String uploadDir = "user-photos/" + post.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
	    return "process_success";
	}
	
	/* View all board posts */
	@GetMapping("/all_board_post")
	public String showAllBoardPosts(Model model) {
		List<Post> posts = blogPostService.getAllPost();
		List<Post> boardPosts = new ArrayList<Post>();
		for(int i=0; i<posts.size(); i++) {
			if(posts.get(i).getPostCategory() == Category.Board) {
				boardPosts.add(posts.get(i));
			}
		}
	    model.addAttribute("posts", boardPosts);
	    return "all_board_post";
	}
	
	/* View all news posts */
	@GetMapping("/all_news_post")
	public String showAllNewsPosts(Model model) {
		List<Post> posts = blogPostService.getAllPost();
		List<Post> newsPosts = new ArrayList<Post>();
		for(int i=0; i<posts.size(); i++) {
			if(posts.get(i).getPostCategory() == Category.News) {
				newsPosts.add(posts.get(i));
			}
		}

	    model.addAttribute("posts", newsPosts);
	    return "all_news_post";
	}
	
	
	/* Add news post comment */
	@PostMapping(value = {"/all_news_post/{postId}"})
	public String postComment(@PathVariable Integer postId, String comment, Model model){
		model.addAttribute("Comment", new Comment());		
		if(comment == null) return "redirect:/all_news_post/" + String.valueOf(postId);

		Account authUser = blogPostService.getAuthenticatedUser();
		if(authUser == null) return "redirect:/login";

		Post currentPost = blogPostService.getPost(postId);

		if(comment != null) {
			Comment newComment = new Comment();
			newComment.setCommentedTime(new Date());
			newComment.setCommentedBy(authUser.getUsername());
			newComment.setCommentContent(comment);
			newComment.setPost(currentPost);
			blogPostService.saveComment(newComment);
		}
		return "redirect:/all_news_post/{postId}";
	}
	
	/* Add board post comment */
	@PostMapping(value = {"/all_board_post/{postId}"})
	public String boardPostComment(@PathVariable Integer postId, String comment, Model model){		
		model.addAttribute("Comment", new Comment());
		
		if(comment == null) return "redirect:/all_board_post/" + String.valueOf(postId);

		Account authUser = blogPostService.getAuthenticatedUser();
		if(authUser == null) return "redirect:/login";

		Post currentPost = blogPostService.getPost(postId);

		if(comment != null) {
			Comment newComment = new Comment();
			newComment.setCommentedTime(new Date());
			newComment.setCommentedBy(authUser.getUsername());
			newComment.setCommentContent(comment);
			newComment.setPost(currentPost);
			blogPostService.saveComment(newComment);
		}
		return "redirect:/all_board_post/{postId}";
	}
	

	//////
	
	/* Display news post */
	/*@GetMapping(value = "/news-all-post")
	public String getNewsPost(Model model,
	        @RequestParam(value = "page", defaultValue = "1") int pageNumber) {
	    List<Post> posts = blogPostService.findAll(pageNumber, ROW_PER_PAGE);
		List<Post> newsPosts = new ArrayList<Post>();
		for(int i=0; i<posts.size(); i++) {
			if(posts.get(i).getPostCategory() == Category.News) {
				newsPosts.add(posts.get(i));
			}
		}
		System.out.print(newsPosts.size());
	    long count = newsPosts.size();
	    boolean hasPrev = pageNumber > 1;
	    boolean hasNext = (pageNumber * ROW_PER_PAGE) < count;
	    model.addAttribute("posts", newsPosts);
	    model.addAttribute("hasPrev", hasPrev);
	    model.addAttribute("prev", pageNumber - 1);
	    model.addAttribute("hasNext", hasNext);
	    model.addAttribute("next", pageNumber + 1);
	    
	    return "news-post-list";
	}
	*/
	
	
	
	/* Get news post by id */
	@GetMapping(value = "/all_news_post/{postId}")
	public String getNewsPostById(Model model, @PathVariable Integer postId) {
	    Post post = null;
	    List<Comment> commentAllList = new ArrayList<Comment>();
	    List<Comment> commentList = new ArrayList<Comment>();
	    try {
	        post = blogPostService.getPost(postId);
	        commentAllList = blogPostService.getAllComment();
	        for(int i=0; i< commentAllList.size(); i++) {
	        	if(commentAllList.get(i).getPost().getId() == postId) {
	        		commentList.add(commentAllList.get(i));
	        	} 
	        }
	        
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	        model.addAttribute("errorMessage", "Comments not found");
	    }
	    post.setViewCount(post.getViewCount()+1);
	    blogPostService.savePost(post);
	    model.addAttribute("post", post);
	    model.addAttribute("commentList", commentList);
	    
	    return "post";
	}

	
	/* Edit News post */
	@GetMapping(value = {"/all_news_post/{postId}/edit"})
	public String showEditNewsPost(Model model, @PathVariable Integer postId) {
	    Post post = null;
	    try {
	    	post = blogPostService.getPost(postId);
	    	model.addAttribute("add", false);
		    model.addAttribute("post", post);
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	    }
	    
	    
	    return "post_edit";
	}
	 
	@PostMapping(value = {"/all_news_post/{postId}/edit"})
	public String updateNewsPost(Model model, Principal principal, @PathVariable Integer postId, @ModelAttribute("post") Post post) {        
		Post findPost = blogPostService.getPost(postId);
		try {
			post.setId(postId);
	        post.setPostCategory(findPost.getPostCategory());
	        post.setCreatedTime(findPost.getCreatedTime());
	        post.setUpdatedTime(new Date());
	        post.setAccount(principal.getName());
	        blogPostService.updatePost(post);
	        return "redirect:/all_news_post/" + String.valueOf(post.getId());
	        
	    } catch (Exception ex) {
	        String errorMessage = ex.getMessage();
	        logger.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	 
	         model.addAttribute("add", false);
	        return "post_edit";
	    }
	}
	
	/* Delete News post */
	@GetMapping(value = {"/all_news_post/{postId}/delete"})
	public String showDeleteNewsPostById(Model model, @PathVariable Integer postId) {
		Post post = null;
	    try {
	    	post = blogPostService.getPost(postId);
	    	model.addAttribute("allowDelete", true);
		    model.addAttribute("post", post);
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	    }
	    
	    return "delete_post";
	}
	 
	@PostMapping(value = {"/all_news_post/{postId}/delete"})
	public String deleteNewsContactById(Model model, @PathVariable Integer postId) {
	    try {
	    	blogPostService.deletePost(postId);
	        return "redirect:/all_news_post";
	    } catch (Exception ex) {
	        String errorMessage = ex.getMessage();
	        logger.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	        return "delete_post";
	    }
	}
	
	////////
	
	/* Get board post by id */
	@GetMapping(value = "/all_board_post/{postId}")
	public String getBoardPostById(Model model, @PathVariable Integer postId) {
	    Post post = null;
	    List<Comment> commentAllList = new ArrayList<Comment>();
	    List<Comment> commentList = new ArrayList<Comment>();
	    try {
	        post = blogPostService.getPost(postId);
	        commentAllList = blogPostService.getAllComment();
	        for(int i=0; i< commentAllList.size(); i++) {
	        	if(commentAllList.get(i).getPost().getId() == postId) {
	        		commentList.add(commentAllList.get(i));
	        	} 
	        }
	        
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	        model.addAttribute("errorMessage", "Comments not found");
	    }
	    post.setViewCount(post.getViewCount()+1);
	    blogPostService.savePost(post);
	    model.addAttribute("post", post);
	    model.addAttribute("commentList", commentList);
	    
	    return "board_post";
	}

	
	/* Edit board post */
	@GetMapping(value = {"/all_board_post/{postId}/edit"})
	public String showEditBoardPost(Model model, @PathVariable Integer postId) {
	    Post post = null;
	    try {
	    	post = blogPostService.getPost(postId);
	    	model.addAttribute("add", false);
		    model.addAttribute("post", post);
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	    }

	    return "board_post_edit";
	}
	 
	@PostMapping(value = {"/all_board_post/{postId}/edit"})
	public String updateBoardPost(Model model, Principal principal, @PathVariable Integer postId, @ModelAttribute("post") Post post) {        
		Post findPost = blogPostService.getPost(postId);
		try {
			post.setId(postId);
	        post.setPostCategory(findPost.getPostCategory());
	        post.setCreatedTime(findPost.getCreatedTime());
	        post.setUpdatedTime(new Date());
	        post.setAccount(principal.getName());
	        blogPostService.updatePost(post);
	        return "redirect:/all_board_post/" + String.valueOf(post.getId());
	        
	    } catch (Exception ex) {
	        String errorMessage = ex.getMessage();
	        logger.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	        model.addAttribute("add", false);
	        return "board_post_edit";
	    }
	}
	
	/* Delete board post */
	@GetMapping(value = {"/all_board_post/{postId}/delete"})
	public String showDeleteBoardPostById(Model model, @PathVariable Integer postId) {
		Post post = null;
	    try {
	    	post = blogPostService.getPost(postId);
	    	model.addAttribute("allowDelete", true);
		    model.addAttribute("post", post);
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	    }
	    return "delete_board_post";
	}
	 
	@PostMapping(value = {"/all_board_post/{postId}/delete"})
	public String deleteBoardContactById(Model model, @PathVariable Integer postId) {
	    try {
	    	blogPostService.deletePost(postId);
	        return "redirect:/all_board_post";
	    } catch (Exception ex) {
	        String errorMessage = ex.getMessage();
	        logger.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	        return "delete_board_post";
	    }
	}
	
	
	/* Search feature */
	@GetMapping("/search")
	public String search(Model model, @RequestParam String keyword) {
	    List<Post> result = null;
		try {
		    result = blogPostService.search(keyword);
		    model.addAttribute("result", result);

		} catch(Exception e) {
			model.addAttribute("errorMessage", "There are no searches");
		}
	    return "search";    
	}
	
	/* Get searched posts by id */
	@GetMapping(value = "/search/{postId}")
	public String getSearchPostById(Model model, @PathVariable Integer postId) {
	    Post post = null;
	    List<Comment> commentAllList = new ArrayList<Comment>();
	    List<Comment> commentList = new ArrayList<Comment>();
	    try {
	        post = blogPostService.getPost(postId);
	        commentAllList = blogPostService.getAllComment();
	        for(int i=0; i< commentAllList.size(); i++) {
	        	if(commentAllList.get(i).getPost().getId() == postId) {
	        		commentList.add(commentAllList.get(i));
	        	} 
	        }
	        
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	        model.addAttribute("errorMessage", "Comments not found");
	    }
	    post.setViewCount(post.getViewCount()+1);
	    blogPostService.savePost(post);
	    model.addAttribute("post", post);
	    model.addAttribute("commentList", commentList);
	    
	    return "searched_posts";
	}
	
	/* Add search post comment */
	@PostMapping(value = {"/search/{postId}"})
	public String searchComment(@PathVariable Integer postId, String comment, Model model){
		model.addAttribute("Comment", new Comment());		
		if(comment == null) return "redirect:/search/" + String.valueOf(postId);

		Account authUser = blogPostService.getAuthenticatedUser();
		if(authUser == null) return "redirect:/login";

		Post currentPost = blogPostService.getPost(postId);

		if(comment != null) {
			Comment newComment = new Comment();
			newComment.setCommentedTime(new Date());
			newComment.setCommentedBy(authUser.getUsername());
			newComment.setCommentContent(comment);
			newComment.setPost(currentPost);

			blogPostService.saveComment(newComment);
		}
		return "redirect:/search/{postId}";
	}
	
	/* Edit Searched post */
	@GetMapping(value = {"/search/{postId}/edit"})
	public String showEditSearchedPost(Model model, @PathVariable Integer postId) {
	    Post post = null;
	    try {
	    	post = blogPostService.getPost(postId);
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	    }
	    model.addAttribute("add", false);
	    model.addAttribute("post", post);
	    
	    return "searched_posts_edit";
	}
	 
	@PostMapping(value = {"/search/{postId}/edit"})
	public String updateSearchedPost(Model model, Principal principal, @PathVariable Integer postId, @ModelAttribute("post") Post post) {        
		Post findPost = blogPostService.getPost(postId);
		try {
			post.setId(postId);
	        post.setPostCategory(findPost.getPostCategory());
	        post.setCreatedTime(findPost.getCreatedTime());
	        post.setUpdatedTime(new Date());
	        post.setAccount(principal.getName());
	        blogPostService.updatePost(post);
	        return "redirect:/search/" + String.valueOf(post.getId());
	        
	    } catch (Exception ex) {
	        String errorMessage = ex.getMessage();
	        logger.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	 
	         model.addAttribute("add", false);
	        return "searched_posts_edit";
	    }
	}
	
	/* Delete News post */
	@GetMapping(value = {"/search/{postId}/delete"})
	public String showDeleteSearchedPostById(Model model, @PathVariable Integer postId) {
		Post post = null;
	    try {
	    	post = blogPostService.getPost(postId);
	    } catch (Exception ex) {
	        model.addAttribute("errorMessage", "Post not found");
	    }
	    model.addAttribute("allowDelete", true);
	    model.addAttribute("post", post);
	    return "delete_search_post";
	}
	 
	@PostMapping(value = {"/search/{postId}/delete"})
	public String deleteSearchedPostsById(Model model, @PathVariable Integer postId) {
	    try {
	    	blogPostService.deletePost(postId);
	        return "redirect:/homepage";
	    } catch (Exception ex) {
	        String errorMessage = ex.getMessage();
	        logger.error(errorMessage);
	        model.addAttribute("errorMessage", errorMessage);
	        return "delete_search_post";
	    }
	}

}
