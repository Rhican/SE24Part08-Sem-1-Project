/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author hendry
 * More methods added by Mugunthan
 */
public class FileOperations {

    public static ArrayList<ArrayList<String>> readFileTokenized(String filename) {
        ArrayList<ArrayList<String>> dataTokenized = new ArrayList<>();
        ArrayList<String> tokens;
        ArrayList<String> readData = FileOperations.readFile(filename);

        for (String line : readData) {
            tokens = FileOperations.parseLine(line);
            dataTokenized.add(tokens);
        }
        return dataTokenized;
    }

    public static ArrayList<String> readFile(String filename) {
        ArrayList<String> data = new ArrayList<>();
        BufferedReader br = null;
        String currentLine;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((currentLine = br.readLine()) != null) {
                data.add(currentLine);
            }
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        } finally {
        	 if(br != null){
             	try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}        		 
        	 }
        }
        return data;
    }

    public static void writeFile(ArrayList<String> data, String filename, boolean append) {
        //System.out.println("Writing File");
        FileWriter fw = null;
        try {
            fw = new FileWriter(filename, append);
            for (String line : data) {
                //System.out.println("Writing:"+line);
                fw.write(line + "\n");
                fw.flush();
            }
        } catch (Exception e) {
            System.out.println("Exception" + e);
        } finally {
        	if(fw != null){
	        	try {
					fw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
        	}
        }

    }//end of writeFile

    public static ArrayList<String> parseLine(String lineData) {
        StringTokenizer st = new StringTokenizer(lineData, ",");
        ArrayList<String> dataToken = new ArrayList<>();
        while (st.hasMoreTokens()) {
            dataToken.add(st.nextToken().trim());
        }
        return dataToken;
    }

    //Added by Mugunthan
    public static void deleteFile(String filename) {
        System.out.println("Deleteing File");
        try {
            Path path = Paths.get(filename);
            Files.delete(path);
        } catch (NoSuchFileException ex) {
            System.out.println("Exception" + ex);        
        } catch (IOException ex) {
            System.out.println("Exception" + ex);
        }
    }
    
  //Added by Mugunthan
    public static void createFile(String filename) {
        System.out.println("Creating a File");
        try {
            Path path = Paths.get(filename);
            if(Files.exists(path))
            	Files.delete(path);
        	Files.createFile(path);
        } catch (NoSuchFileException ex) {
            System.out.println("Exception" + ex);        
        } catch (IOException ex) {
            System.out.println("Exception" + ex);
        }
    }
    
  //Added by Mugunthan
    public static void copyFile(String src, String dest) throws IOException {
		FileChannel inputChannel = null;
		FileChannel outputChannel = null;
		try {
			inputChannel = new FileInputStream(src).getChannel();
			outputChannel = new FileOutputStream(dest).getChannel();
			outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
		} finally {
			inputChannel.close();
			outputChannel.close();
		}
	}
    
    //Added by Mugunthan
    public static void moveDir(Path source, Path target){
    	try {
    		deleteDir(target);
			Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    //Added by Mugunthan    
    public static void deleteDir(Path dir) throws IOException{
		Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
			   @Override
			   public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				   Files.delete(file);
				   return FileVisitResult.CONTINUE;
			   }

			   @Override
			   public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
				   Files.delete(dir);
				   return FileVisitResult.CONTINUE;
			   }

		 });
    	
    }
    
    //Added by Mugunthan      
    public static void createDir(Path dir){
    	try {
			Files.createDirectory(dir);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
