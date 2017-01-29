package com.niit.collude.backend.dao;

import java.util.List;

import com.niit.collude.backend.model.Blog;

public interface BlogDao {

	void create(Blog blog);
	
	void update(Blog blog);
	
	void remove(Blog blog);
	
	Blog getBlogById(long blogId);
	
	List<Blog> getAllBlogs();
	
	List<Blog> getApprovedBlogs();
}
