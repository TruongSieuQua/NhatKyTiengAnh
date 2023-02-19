package edu.truong.meanings_generator.service;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;

public class FileService {
	final String STORING_DIRECTORY = Paths.get("").toAbsolutePath().toString() + "/Note";
	
	public void openFile(String fileName) {
		File f = getFile(fileName);
		if(f==null) {
			f = new File(STORING_DIRECTORY);
		}
		try {
			Desktop.getDesktop().browse(f.toURI());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public File getFile(String fileName) {
		if(!fileName.isBlank()) {
			return createIfNotExist(fileName);
		}
		return createIfNotExist(getDefaultFileName());
	}
	
	private File createIfNotExist(String fileName) {
		if(fileName.isBlank()) {
			fileName = getDefaultFileName();
		}
		File directory = new File(STORING_DIRECTORY);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		if (!directory.isDirectory()) {
			return null;
		}
		
		File f = new File(directory.getAbsoluteFile() + "/" + fileName + ".html");	
		if (f.exists()) {
			return f;
		}
		try {
			f.createNewFile();
			copyFile(new File(STORING_DIRECTORY +"/Model/html_model.html"), f);
			return f;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private String getDefaultFileName() {
		 return java.time.LocalDate.now().toString().replaceAll("-", "_");
	}
	
	private static void copyFile(File sourceFile, File destFile) throws IOException {
		if (!sourceFile.exists()) {
			System.out.println("Thieu mat " + sourceFile.getAbsolutePath());
		}
		FileInputStream source = null;
		FileOutputStream destination = null;
		try {
			source = new FileInputStream(sourceFile);
			destination = new FileOutputStream(destFile);
			destination.getChannel().transferFrom(source.getChannel(), 0, source.getChannel().size());
		} finally {
			if (source != null) {
				source.close();
			}
			if (destination != null) {
				destination.close();
			}
		}
	}
	
	public void insertJsonToFile(String fileName, String dataJson) {
		try {
			 File file = getFile(fileName);
			 if(file==null) {return;}
			    BufferedReader reader = new BufferedReader(new FileReader(file));
			    String line;
			    StringBuilder sb = new StringBuilder();
			    while ((line = reader.readLine()) != null) {
			    	if (line.contains("jsonData = {\"data\": [")) {
			    		line = line.replace("{\"data\": [", "{\"data\": [" + dataJson);
			      }
			      sb.append(line).append("\n");
			    }
			    reader.close();
			    
			    FileWriter writer = new FileWriter(file);
			    writer.write(sb.toString());
			    writer.close();
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
	
	public void appendToFile(String fileName, String dataJson) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter("./jsonData.txt", true))){
			bw.write(dataJson);
			bw.write(",\r\n");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
