package com.rjtech.common.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
 
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.ReadableByteChannel;

import com.rjtech.constants.ApplicationConstants;
 
@Component
public class UploadUtil {
 
	public static void storeFile(MultipartFile file , String file_path) throws IOException {
		String file_path_url = ApplicationConstants.FILE_DIRECTORY + "/" + file_path;
		System.out.println("file path url from UploadUtil:"+file_path_url);
		Path filePath = Paths.get( file_path_url+"/"+file.getOriginalFilename());
 
		Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
	}
	
	public static void uploadFile( MultipartFile file, String master_folder, String dir_path, String[] extras ) throws IOException {
		
		String file_path_url = ApplicationConstants.FILE_DIRECTORY + "/" + dir_path;
		
		if( master_folder != null || master_folder != "" )
		{
			//file_path_url = file_path_url + "/" + extras[0];
			for(int i=0;i<extras.length;i++)
			{
				file_path_url = file_path_url + "/" +extras[i];
			}
			System.out.println("if block of master_folder:"+file_path_url);
		}
		File theDir = new File(file_path_url);
		if ( !theDir.exists() && file_path_url != "" ) {
			System.out.println("not exists block");
		    theDir.mkdirs();
		}		
		System.out.println("file path url from UploadUtil:"+file_path_url);
		Path filePath = Paths.get( file_path_url+"/"+file.getOriginalFilename() );
 
		Files.copy( file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING );
	}
	
	public static void checkOutput() {
		System.out.println("checkOutput function");
	}
	
	public static byte[] downloadFile( String file_name ) throws IOException
	{
		URL url = new URL(file_name);
		ReadableByteChannel readableByteChannel = Channels.newChannel( url.openStream() );
		FileOutputStream fileOutputStream = new FileOutputStream( file_name );
		FileChannel fileChannel = fileOutputStream.getChannel();
		fileChannel.transferFrom( readableByteChannel, 0, Long.MAX_VALUE );
		byte[] file_size = Files.readAllBytes(Paths.get(file_name));
		System.out.println("uploadUtil downloadFile function");
		System.out.println(file_size);
		return file_size;
		
		/*URL url = new URL(sourceURL);
	    String fileName = sourceURL.substring(sourceURL.lastIndexOf('/') + 1, sourceURL.length());
	    Path targetPath = new File(targetDirectory + File.separator + fileName).toPath();
	    Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

	    return targetPath;*/
	}
	
	public static String bytesIntoHumanReadable(long bytes) {
	    long kilobyte = 1024;
	    long megabyte = kilobyte * 1024;
	    long gigabyte = megabyte * 1024;
	    long terabyte = gigabyte * 1024;

	    if ((bytes >= 0) && (bytes < kilobyte)) {
	        return bytes + " B";

	    } else if ((bytes >= kilobyte) && (bytes < megabyte)) {
	        return (bytes / kilobyte) + " KB";

	    } else if ((bytes >= megabyte) && (bytes < gigabyte)) {
	        return (bytes / megabyte) + " MB";

	    } else if ((bytes >= gigabyte) && (bytes < terabyte)) {
	        return (bytes / gigabyte) + " GB";

	    } else if (bytes >= terabyte) {
	        return (bytes / terabyte) + " TB";

	    } else {
	        return bytes + " Bytes";
	    }
	}
}
