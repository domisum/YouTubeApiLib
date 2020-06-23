package io.domisum.lib.youtubeapilib.playlist.actors;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.playlist.PlaylistSpecification;

import java.io.IOException;
import java.util.Optional;

public interface PlaylistIdFetcher
{
	
	Optional<String> fetch(YouTubeApiCredentials credentials, PlaylistSpecification youTubePlaylistSpec)
			throws IOException;
	
}
