package com.songify.song.dto.response;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateSongResponseDto(
        @NotNull(message = "Song name cannot be null")
        @NotEmpty(message = "Song name cannot be empty")
        String songName,

        @NotNull(message = "Artist cannot be null")
        @NotEmpty(message = "Artist cannot be empty")
        String artist
) {
}
