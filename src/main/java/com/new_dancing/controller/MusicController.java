package com.new_dancing.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import com.new_dancing.model.Music;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    
    @GetMapping("/list")
    public ResponseEntity<List<Music>> getMusicList() {
        List<Music> musicList = new ArrayList<>();
        File musicDir = new File("e:\\IdeaProjects\\new_dancing\\dancing_music");
        File[] musicFiles = musicDir.listFiles((dir, name) -> name.endsWith(".mp3"));
        
        if (musicFiles != null) {
            int id = 1;
            for (File file : musicFiles) {
                String fileName = file.getName().replace(".mp3", "");
                String[] parts = fileName.split("-");
                String title = parts.length > 0 ? parts[0] : fileName;
                String artist = parts.length > 1 ? parts[1] : "未知艺术家";
                musicList.add(new Music(id++, title, artist, "00:00"));
            }
        }
        
        return ResponseEntity.ok(musicList);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<String> getMusicFile(@PathVariable int id) {
        File musicDir = new File("e:\\IdeaProjects\\new_dancing\\dancing_music");
        File[] musicFiles = musicDir.listFiles((dir, name) -> name.endsWith(".mp3"));
        
        if (musicFiles != null && id > 0 && id <= musicFiles.length) {
            return ResponseEntity.ok("/dancing_music/" + musicFiles[id-1].getName());
        }
        return ResponseEntity.notFound().build();
    }
    

    

}