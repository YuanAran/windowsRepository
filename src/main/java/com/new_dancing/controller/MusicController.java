package com.new_dancing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.core.io.Resource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.*;
import java.io.File;

import com.new_dancing.model.Music;

@RestController
@RequestMapping("/api/music")
public class MusicController {
    private final String MUSIC_DIR_PATH = "E:\\study\\project\\mmy\\windowsRepository\\dancing_music"; // 音乐文件夹路径
    @GetMapping("/list")
    public ResponseEntity<List<Music>> getMusicList() {
        List<Music> musicList = new ArrayList<>();
        File musicDir = new File("E:\\study\\project\\mmy\\windowsRepository\\dancing_music");
        File[] musicFiles = musicDir.listFiles((dir, name) -> name.endsWith(".mp3"));

        if (musicFiles != null) {
            int id = 1;
            for (File file : musicFiles) {
                String fileName = file.getName().replace(".mp3", "");
                String[] parts = fileName.split("-");
                String title = parts.length > 0 ? parts[0] : fileName;
                String artist = parts.length > 1 ? parts[1] : "未知艺术家";
                musicList.add(new Music(id++, title, artist));
            }
        }

        return ResponseEntity.ok(musicList);
    }

    // 根据音乐 ID 获取音频文件的 URL
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, String>> getMusicFile(@PathVariable int id) {
        File musicDir = new File(MUSIC_DIR_PATH);
        File[] musicFiles = musicDir.listFiles((dir, name) -> name.endsWith(".mp3"));
        System.out.println("请求的 id: " + id);
        System.out.println("所有音乐文件: " + Arrays.toString(musicFiles));

        if (musicFiles != null && id > 0 && id <= musicFiles.length) {
            Map<String, String> response = new HashMap<>();
            String fileName = musicFiles[id - 1].getName();  // 根据 ID 获取文件名
            String url = "http://localhost:3000/" + fileName;  // 构建音频文件的 URL
            response.put("url", url);  // 音频文件的 URL
            response.put("format", "mp3");  // 音频文件的格式（mp3）

            // 打印返回的数据，检查返回的 URL
            System.out.println("返回的音频 URL: " + url);
            return ResponseEntity.ok(response);  // 返回响应，包含音频文件的 URL 和格式
        }

        return ResponseEntity.notFound().build();  // 如果找不到文件，返回 404
    }

    // 根据文件名获取音频文件
    @GetMapping("/music/{filename}")
    public ResponseEntity<Resource> getMusicFile(@PathVariable String filename) {
        // 获取文件路径（解码文件名以处理中文或特殊字符）
        Path filePath = Paths.get(MUSIC_DIR_PATH, filename);
        Resource resource = new FileSystemResource(filePath);

        if (resource.exists() && resource.isReadable()) {
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("audio/mpeg"))  // 设置媒体类型为音频格式
                    .body(resource);  // 返回音频资源
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // 如果文件不存在或不可读，返回 404
        }
    }

}
