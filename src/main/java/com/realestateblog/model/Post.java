package com.realestateblog.model;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;


@Entity
@Table(name = "post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@JoinColumn(name = "account")
	private String account;
	@Column(name = "title")
	private String title;
	@Column(name = "content")
	private String content;
	@Column(name = "created_time")
	private Date createdTime;
	@Column(name = "updated_time")
	private Date updatedTime;
	@Column(name = "view_count")
	private int viewCount;
	@Column(name = "post_category")
	private Category postCategory;
	@Column(name = "photo_image", nullable = true, length = 64)
	private String photoImage;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	@OrderBy("commented_time")
    private List<Comment> comments;
	
	
	public Post() {
		
	}
	
	public Post(int id, String account, String title, String content, Date createdTime,  Date updatedTime, int viewCount, Category postCategory, String photoImage, List<Comment> comments) {
		this.id = id;
		this.account = account;
		this.title = title;
		this.content = content;
		this.createdTime = createdTime;
		this.updatedTime = updatedTime;
		this.viewCount = viewCount;
		this.postCategory = postCategory;
		this.photoImage = photoImage;
		this.comments = comments;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
	}
	
	public Category getPostCategory() {
		return postCategory;
	}

	public void setPostCategory(Category postCategory) {
		this.postCategory = postCategory;
	}

	public void setPhotoImage(String photoImage) {
		this.photoImage = photoImage;
	}
	

	@Transient
    public String getPhotoImage() {
        if (this.photoImage == null || this.id == 0) {
        	return null;
        } else {
        	return "/user-photos/" + id + "/" + this.photoImage;
        }
    }
	
	public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

}
