package com.realestateblog.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



@Entity
@Table(name = "comment")
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="post_id")
	private Post post;
	@Column(name = "comment_content")
	private String commentContent;
	@Column(name = "commented_by")
	private String commentedBy;
	@Column(name = "commented_time")
	private Date commentedTime;
	
	public Comment() {
		
	}
	
	public Comment(int id, Post post, String commentContent, String commentedBy, Date commentedTime) {
		this.id = id;
		this.post = post;
		this.commentContent = commentContent;
		this.commentedBy = commentedBy;
		this.commentedTime = commentedTime;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}
	
	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public String getCommentedBy() {
		return commentedBy;
	}

	public void setCommentedBy(String commentedBy) {
		this.commentedBy = commentedBy;
	}

	public Date getCommentedTime() {
		return commentedTime;
	}

	public void setCommentedTime(Date commentedTime) {
		this.commentedTime = commentedTime;
	}

}
