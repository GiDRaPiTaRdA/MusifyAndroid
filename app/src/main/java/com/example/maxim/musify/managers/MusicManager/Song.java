package com.example.maxim.musify.managers.MusicManager;

import java.io.File;

/**
 * Created by ${Maxim} on ${07.01.2017}.
 */

public class Song {

    private String path = "";

    public Song(){
        this.Initialize("");
    }
    public Song(String path){
        this.Initialize(path);
    }
    public Song(Song song){
        this.Initialize(song.getPath());
    }
    public Song(File file){
        this.Initialize(file.getPath());
    }


    private void Initialize(String path){
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
