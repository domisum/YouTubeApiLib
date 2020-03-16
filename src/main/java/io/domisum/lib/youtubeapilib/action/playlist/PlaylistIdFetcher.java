package io.domisum.lib.youtubeapilib.action.playlist;

import io.domisum.lib.youtubeapilib.model.playlist.YouTubePlaylistSpec;

import java.io.IOException;
import java.util.Optional;

public interface PlaylistIdFetcher
{

	Optional<String> fetch(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException;

}
