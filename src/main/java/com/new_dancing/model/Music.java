package com.new_dancing.model;

import lombok.Data;

@Data
public class Music {
    private int id;
    private String title;
    private String artist;
    
    
    public Music(int id, String title, String artist) {
        this.id = id;
        this.title = title;
        this.artist = artist;

    }
    
}