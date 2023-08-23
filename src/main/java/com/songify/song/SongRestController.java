package com.songify.song;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
public class SongRestController {

    Map<Integer, String> database = new HashMap<>(Map.of(
            1, "shawnmendes song1",
            2, "ariana grande song2",
            3, "shawnmendes songfgsdf",
            4, "ariana grande song648723yfnb2"
    ));


    @GetMapping("/songs")
    public ResponseEntity<SongResponseDto> getAllSongs(){
        SongResponseDto response = new SongResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/songs/{id}")
    public ResponseEntity<SingleSongResponseDto> getSongByIdWithHeader(@PathVariable Integer id, @RequestHeader(required = false) String requestId){
        log.info(requestId);
        String song = database.get(id);
        if(song == null){
            return ResponseEntity.notFound().build();
        }
        SingleSongResponseDto response = new SingleSongResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/songs")
    public ResponseEntity<SingleSongResponseDto> postSong(@RequestBody @Valid SongRequestDto request){
        String songName = request.songName();
        log.info("Adding new song: " + songName);
        database.put(database.size() + 1, songName);
        return ResponseEntity.ok(new SingleSongResponseDto(songName));
    }

//    @DeleteMapping("/songs/{id}")
//    public ResponseEntity<String> deleteSongByIdUsingPathVariable(@PathVariable Integer id){
//        database.remove(id);
//        return ResponseEntity.ok("You deleted song with id: " + id);
//    }

    @DeleteMapping("/songs")
    public ResponseEntity<String> deleteSongByIdUsingRequestParam(@RequestParam(required = false) Integer id){
        database.remove(id);
        return ResponseEntity.ok("You deleted song with id: " + id);
    }

    //        @GetMapping("/songs")
//    public ResponseEntity<SongResponseDto> getSongByIdWithParam(@RequestParam(required = false) Integer limit){
//        if(limit != null){
//            Map<Integer, String> limitedMap = database.entrySet()
//                    .stream()
//                    .limit(limit)
//                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//            SongResponseDto response = new SongResponseDto(limitedMap);
//            return ResponseEntity.ok(response);
//        }
//        SongResponseDto response = new SongResponseDto(database);
//        return ResponseEntity.ok(response);
//    }

//    @GetMapping("/songs/{id}")
//    public ResponseEntity<SingleSongResponseDto> getSongById(@PathVariable Integer id){
//        String song = database.get(id);
//        if(song == null){
//            return ResponseEntity.notFound().build();
//        }
//        SingleSongResponseDto response = new SingleSongResponseDto(song);
//        return ResponseEntity.ok(response);
//    }



}
