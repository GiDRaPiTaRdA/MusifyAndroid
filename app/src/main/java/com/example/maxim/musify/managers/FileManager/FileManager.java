package com.example.maxim.musify.managers.FileManager;


import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ${Maxim} on ${07.01.2017}.
 */
public class FileManager {

    private final String TAG = "FILE_MANAGER";

    // file writer/reader
    public static String GetStringFromFile(File file) throws IOException {
        FileReader fileReader =  new FileReader(file);
        String string = "";
        int charId;
        while ((charId=fileReader.read())!=-1)
            string+=(char)charId;
        fileReader.close();
        return string;
    }
    public static void WriteStringToFile(File file,String string) throws IOException {
        FileWriter fileWriter =  new FileWriter(file);
        fileWriter.write(string);
        fileWriter.close();
    }

    //file checking
    public static boolean CheckFileHasExtension(File file ,String extension){
        String name =  file.getName();
        String ex ="";
        boolean dotFound = false;
        for (char ch:name.toCharArray()) {
            if(dotFound)
                ex+=ch;
            if(ch=='.')
                dotFound = true;
        }
        boolean result = ex.equals(extension);
        return result;
    }
    public static boolean IsDirectory(File file){
        return file.exists()&&file.isDirectory();
    }
    public static boolean IsFile(File file){
        return file.exists()&&file.isFile();
    }

    public static File[] FilterFilesByExtension(String extension,  File[] files){
        ArrayList<File> resultList =  new ArrayList<>();
        for (File file: files) {
            if(CheckFileHasExtension(file,extension))
                resultList.add(file);
        }
        return resultList.toArray(new File[]{});
    }

    // gets file from directory directly
    public static File[] GetAllFilesFromDirectory(File file) {
        return file.listFiles();
    }
    public static File[] GetFoldersFromDirectory(File file){
        File[] files = GetAllFilesFromDirectory(file);
        ArrayList<File> resultList =  new ArrayList<>();
        for (File f:files) {
            if(IsDirectory(f)){
                resultList.add(file);
            }
        }
        return (File[]) resultList.toArray();
    }
    public static File[] GetFilesFromDirectory(File file){
        File[] files = GetAllFilesFromDirectory(file);
        ArrayList<File> resultList =  new ArrayList<>();
        for (File f:files) {
            if(IsFile(f)){
                resultList.add(f);
            }
        }
        return resultList.toArray(new File[]{});
    }

    //gets all files from directory
    public static File[] GetExtendedFilesFromFolder(File file){
        if(!IsDirectory(file))
            try {
                throw new Exception("File must exist and be a directory");

            } catch (Exception e) {
                e.printStackTrace();
                return new File[0];
            }
        ArrayList<File> listFiles = new ArrayList<>();
        getListFiles(file,listFiles);
        return listFiles.toArray(new File[]{});
    }
    public static File[] GetExtendedFilesFromFolder(File file,String extension){
        if(!IsDirectory(file))
            try {
                throw new Exception("File must exist and be a directory");

            } catch (Exception e) {
                e.printStackTrace();
                return new File[0];
            }
        ArrayList<File> listFiles = new ArrayList<>();
        getListFiles(file,listFiles,extension);
        return listFiles.toArray(new File[]{});
    }

    //recursive methods to find files
    private static File[] getListFiles(File file ,ArrayList<File> listWithFileNames) {
        if(file.exists()&&file.isDirectory()&&file.listFiles()!=null)
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                listWithFileNames.add(f);
            } else if (f.isDirectory()) {
                getListFiles(f, listWithFileNames);
            }
        }
        return listWithFileNames.toArray(new File[]{});
    }
    private static File [] getListFiles(File file ,ArrayList<File> listWithFileNames,String extension) {
        if(file.exists()&&file.isDirectory()&&file.listFiles()!=null)
        for (File f : file.listFiles()) {
            if (f.isFile()&&CheckFileHasExtension(f,extension)) {
                listWithFileNames.add(f);
            }
            else if (f.isDirectory()) {
                getListFiles(f, listWithFileNames,extension);
            }
        }
        return listWithFileNames.toArray(new File[]{});
    }
}
