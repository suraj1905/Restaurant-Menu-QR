package com.menuqr.helpers;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.web.multipart.MultipartFile;


public class FileHandler {
	
	static String baseUrl = "https://bristo.in";
	static String basePath = "/home/ubuntu/Bristo/Web/Business/";
	static String docPath = "/home/ubuntu/Bristo/Web/ClientDocs/";
	
	public static void createPath(String path) {
        try {
            Files.createDirectories(Paths.get(path));
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload folder!");
        }
    }
	
	public static String saveFile(MultipartFile file, String path) throws Exception {
		String fullpath = basePath+path;
		try {
			 Path root = Paths.get(fullpath);
	            if (!Files.exists(root)) {
	            	createPath(fullpath);
	            }
	         Instant instant = Instant.now();
		     long timestampSeconds = instant.getEpochSecond();
	         Files.copy(file.getInputStream(), root.resolve(timestampSeconds+"_"+file.getOriginalFilename()));
	         Path filePath = Paths.get(fullpath)
                     .resolve(timestampSeconds+"_"+file.getOriginalFilename());
	         Resource resource = new UrlResource(filePath.toUri());

	            if (resource.exists() || resource.isReadable()) {
	            	return baseUrl+"/Business/"+path.replace(" ", "%20")+"/"+resource.getFilename().replace(" ","%20");
	            } else {
	            	return "Could not read the file!";
	            }
		}catch(Exception ex) {
			throw new Exception(ex);
		}
	}

	public static String saveDoc(MultipartFile file) throws Exception {
		try {
			 Path root = Paths.get(docPath);
	            if (!Files.exists(root)) {
	            	createPath(docPath);
	            }
	         Instant instant = Instant.now();
		     long timestampSeconds = instant.getEpochSecond();
	         Files.copy(file.getInputStream(), root.resolve(timestampSeconds+"_"+file.getOriginalFilename()));
	         System.out.print(timestampSeconds);
	         Path filePath = Paths.get(docPath)
                     .resolve(timestampSeconds+"_"+file.getOriginalFilename());
	         Resource resource = new UrlResource(filePath.toUri());

	            if (resource.exists() || resource.isReadable()) {
	            	return baseUrl+"/ClientDocs/"+resource.getFilename();
	            } else {
	            	return "Could not read the file!";
	            }
		}catch(Exception ex) {
			throw new Exception(ex);
		}
	}
	
	public static boolean deleteFile(String fileUrl, String path) throws Exception {
		String pdfPath = basePath+path;
		try {
			String url = fileUrl;
			String filename =Paths.get(new URI(url).getPath()).getFileName().toString();
			Path myPath = Paths.get(pdfPath+"/"+filename);
			boolean fileDeleted = Files.deleteIfExists(myPath);
			return fileDeleted;
			
		}catch(Exception ex) {
			throw new Exception(ex);
		}
	}
	

	public static boolean deleteFile(String fileUrl) throws Exception {
		try {
			String url = fileUrl;
			String filename = URLEncoder.encode(Paths.get(new URI(url).getPath()).getFileName().toString(),StandardCharsets.UTF_8);
			Path myPath = Paths.get(docPath+"/"+filename);
			boolean fileDeleted = Files.deleteIfExists(myPath);
			return fileDeleted;
			
		}catch(Exception ex) {
			throw new Exception(ex);
		}
	}
}
