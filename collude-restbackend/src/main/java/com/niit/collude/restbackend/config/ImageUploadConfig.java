package com.niit.collude.restbackend.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import com.niit.collude.restbackend.service.FileUploadService;

@ApplicationPath("/rest")
public class ImageUploadConfig extends Application {

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> s = new HashSet<Class<?>>();
		s.add(FileUploadService.class);
		
		return s;
	}

}
