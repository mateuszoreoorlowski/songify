package com.songify;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class SongsController {

    Map<Integer, String> database = new HashMap<>();

//    @GetMapping("/songs")
//    public ResponseEntity<SongResponseDto> getAllSongs(){
//        database.put(1, "shawnmendes song1");
//        database.put(2, "ariana grande song2");
//        SongResponseDto response = new SongResponseDto(database);
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable Integer id){
        String song = database.get(id);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getSongByIdWithParam(@RequestParam(required = false) Integer limit){
        database.put(1, "shawnmendes song1");
        database.put(2, "ariana grande song2");
        database.put(3, "shawnmendes songfgsdf");
        database.put(4, "ariana grande song648723yfnb2");
        if(limit != null){
            Map<Integer, String> limitedMap = database.entrySet()
                    .stream()
                    .limit(limit)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            SongResponseDto response = new SongResponseDto(limitedMap);
            return ResponseEntity.ok(response);
        }
        SongResponseDto response = new SongResponseDto(database);
        return ResponseEntity.ok(response);
    }

}
