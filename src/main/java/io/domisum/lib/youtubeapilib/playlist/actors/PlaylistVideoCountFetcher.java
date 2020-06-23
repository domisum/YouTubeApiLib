package io.domisum.lib.youtubeapilib.playlist.actors;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

import java.io.IOException;

public interface PlaylistVideoCountFetcher
{
	
	int fetchVideoCount(YouTubeApiCredentials credentials, String playlistId)
			throws IOException;
	
}
