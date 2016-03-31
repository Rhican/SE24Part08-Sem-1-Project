/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.nus.iss.SE24PT8.universityStore.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 *
 * @author hendry
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
        BufferedReader br;
        String currentLine;
        try {
            br = new BufferedReader(new FileReader(filename));
            while ((currentLine = br.readLine()) != null) {
                data.add(currentLine);
            }
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }
        return data;
    }

    public static void writeFile(ArrayList<String> data, String filename, boolean append) {
        System.out.println("Writing File");
        FileWriter fw;
        try {
            fw = new FileWriter(filename, append);
            for (String line : data) {
                //System.out.println("Writing:"+line);
                fw.write(line + "\n");
                fw.flush();
            }
        } catch (Exception e) {
            System.out.println("Exception" + e);
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

}
