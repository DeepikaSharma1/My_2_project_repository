package com.niit.collude.restbackend.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.niit.collude.backend.dao.BlogDao;
import com.niit.collude.backend.model.Blog;

@RestController
public class BlogRestController {

	@Autowired
	BlogDao blogDao;
	
	@GetMapping(value = "/blog/all/")
	public ResponseEntity<List<Blog>> getAllBlogs() {
		List<Blog> blogs = blogDao.getAllBlogs();
		if (blogs.isEmpty()) {
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}
	
	@GetMapping(value = "/blog/approved/")
	public ResponseEntity<List<Blog>> getApprovedBlogs() {
		List<Blog> blogs = blogDao.getApprovedBlogs();
		if (blogs.isEmpty()) {
			return new ResponseEntity<List<Blog>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Blog>>(blogs, HttpStatus.OK);
	}

	@GetMapping(value = "/blog/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Blog> getBlog(@PathVariable("id") long id) {
		Blog blog = blogDao.getBlogById(id);
		if (blog == null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Blog>(blog, HttpStatus.OK);
	}
	
	@PostMapping(value = "/blog/")
	public ResponseEntity<Void> createBlog(@RequestBody Blog blog) {
		blog.setDateCreated(new Date());
		blog.setStatus("NEW_BLOG");
		
		blogDao.create(blog);
		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}
	
	@PutMapping(value = "/blog/{id}")
	public ResponseEntity<Blog> updateBlog(@PathVariable("id") long id, @RequestBody Blog blog) {
		Blog b = blogDao.getBlogById(id);
		if (b == null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		
		b.setTitle(blog.getTitle());
		b.setDescription(blog.getDescription());
		b.setUser(blog.getUser());
		b.setStatus(blog.getStatus());
		
		blogDao.update(b);
		
		return new ResponseEntity<Blog>(b, HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/blog/{id}")
	public ResponseEntity<Blog> deleteBlog(@PathVariable("id") long id) {
		Blog blog = blogDao.getBlogById(id);
		if (blog == null) {
			return new ResponseEntity<Blog>(HttpStatus.NOT_FOUND);
		}
		
		blogDao.remove(blog);
		
		return new ResponseEntity<Blog>(HttpStatus.OK);
	}
}
