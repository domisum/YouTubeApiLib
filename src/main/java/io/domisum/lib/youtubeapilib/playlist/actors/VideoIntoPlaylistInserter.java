package io.domisum.lib.youtubeapilib.playlist.actors;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;

import java.io.IOException;

public interface VideoIntoPlaylistInserter
{
	
	void insert(YouTubeApiCredentials credentials, String playlistId, String videoId, InsertionPosition insertionPosition)
			throws IOException;
	
	
	enum InsertionPosition
	{
		@API
		FIRST,
		@API
		LAST
		
	}
	
}
