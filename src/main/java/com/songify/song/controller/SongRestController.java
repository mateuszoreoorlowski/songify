package com.songify.song.controller;

import com.songify.song.dto.request.PartiallyUpdateSongRequestDto;
import com.songify.song.dto.request.CreateSongRequestDto;
import com.songify.song.dto.request.UpdateSongRequestDto;
import com.songify.song.dto.response.*;
import com.songify.song.error.SongNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@Log4j2
@RequestMapping("/songs")
public class SongRestController {

    Map<Integer, Song> database = new HashMap<>(Map.of(
            1, new Song("shawnmendes song1", "Shawn Mendes"),
            2, new Song("ariana grande song2", "Ariana Grande"),
            3, new Song("shawnmendes songfgsdf", "Shawn Mendes"),
            4, new Song("ariana grande song648723yfnb", "Ariana Grande")
    ));


    @GetMapping
    public ResponseEntity<SongResponseDto> getAllSongs(){
        SongResponseDto response = new SongResponseDto(database);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
//    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<GetAllSongsResponseDto> getSongByIdWithHeader(@PathVariable Integer id, @RequestHeader(required = false) String requestId){
        log.info(requestId);
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id "+ id + " not found");
        }

        Song song = database.get(id);
        GetAllSongsResponseDto response = new GetAllSongsResponseDto(song);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CreateSongResponseDto> postSong(@RequestBody @Valid CreateSongRequestDto request){
        Song song = new Song(request.songName(), request.artist());
        log.info("Adding new song: " + song);
        database.put(database.size() + 1, song);
        return ResponseEntity.ok(new CreateSongResponseDto(song));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DeleteSongResponseDto> deleteSongByIdUsingPathVariable(@PathVariable Integer id){
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id "+ id + " not found");
        }
        database.remove(id);
        return ResponseEntity.ok(new DeleteSongResponseDto("You deleted song with id: " + id, HttpStatus.OK));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdateSongResponseDto> update(@PathVariable Integer id,
                                                        @RequestBody @Valid UpdateSongRequestDto request){
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id "+ id + " not found");
        }

        String newSongName = request.songName();
        String newArtist = request.artist();
        Song newSong = new Song(newSongName, newArtist);
        Song oldSong = database.put(id, newSong);
        log.info("Updated newSong with id: " + id +
                " with oldSongName: " + oldSong.name() + " to newSongName: " + newSong.name() +
                " oldArtist: " + oldSong.artist() + " to newArtist: " + newSong.artist());

        return ResponseEntity.ok(new UpdateSongResponseDto(newSong.name(), newSong.artist()));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<PartiallyUpdateSongResponseDto> partiallyUpdateSong(@PathVariable Integer id,
                                                                              @RequestBody PartiallyUpdateSongRequestDto request){
        if(!database.containsKey(id)){
            throw new SongNotFoundException("Song with id "+ id + " not found");
        }

        Song songFromDatabase = database.get(id);
        Song.SongBuilder builder = Song.builder();
        if(request.songName() != null){
            builder.name(request.songName());
            log.info("Partially updated song name");
        }else{
            builder.name(songFromDatabase.name());
        }

        if(request.artist() != null){
            builder.artist(request.artist());
            log.info("Partially updated song artist");
        } else{
            builder.artist(songFromDatabase.artist());
        }
        Song updatedSong = builder.build();
        database.put(id, updatedSong);

        return ResponseEntity.ok(new PartiallyUpdateSongResponseDto(updatedSong));

    }

//    @DeleteMapping("/songs")
//    public ResponseEntity<String> deleteSongByIdUsingRequestParam(@RequestParam(required = false) Integer id){
//        database.remove(id);
//        return ResponseEntity.ok("You deleted song with id: " + id);
//    }

    //        @GetMapping("/songs")
//    public ResponseEntity<SongResponseDto> getSongByIdWithParam(@RequestParam(required = false) Integer limit){
//        if(limit != null){
//            Map<Integer, Song> limitedMap = database.entrySet()
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
