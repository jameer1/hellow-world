package com.rjtech.admin.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
 
@Component
public class FileService {
	public static final String FILE_DIRECTORY = "E://Local/Downloads";
 
	public void storeFile(MultipartFile file) throws IOException {
		Path filePath = Paths.get(FILE_DIRECTORY + "/" + file.getOriginalFilename());
 
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	}
}