package com.niit.collude.restbackend.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/file")
public class FileUploadService {

	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream instream, @FormDataParam("file") FormDataContentDisposition fileDetail) {
		String location = "D://uploaded/" + fileDetail.getFileName();
		saveToLocation(instream, location);
		String output = "File uploaded to: " + location;
		
		return Response.status(200).entity(output).build();
	}

	private void saveToLocation(InputStream instream, String location) {
		try {
			OutputStream outstream = new FileOutputStream(new File(location));
			int read = 0;
			byte[] buffer = new byte[1048576];
			
			while ((read = instream.read(buffer)) != -1) {
				outstream.write(buffer, 0, read);
			}
			outstream.flush();
			outstream.close();
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}
	}
}
