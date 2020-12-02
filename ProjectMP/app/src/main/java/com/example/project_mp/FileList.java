package com.example.project_mp;

import android.app.Application;

import java.util.ArrayList;

public class FileList extends Application {
    private ArrayList<String> FileName=new ArrayList<>();
    private ArrayList<String> FileOwnerId=new ArrayList<>();

    public ArrayList<String> getFileName() {
        return FileName;
    }

    public void setFileName(ArrayList<String> fileName) {
        FileName = fileName;
    }

    public ArrayList<String> getFileOwnerId() {
        return FileOwnerId;
    }

    public void setFileOwnerId(ArrayList<String> fileOwnerId) {
        FileOwnerId = fileOwnerId;
    }

}
