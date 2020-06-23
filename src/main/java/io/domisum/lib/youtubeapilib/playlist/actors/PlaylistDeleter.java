package io.domisum.lib.youtubeapilib.playlist.actors;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.playlist.YouTubePlaylist;

import java.io.IOException;

public interface PlaylistDeleter
{
	
	void delete(YouTubeApiCredentials credentials, String playlistId)
			throws IOException;
	
	default void delete(YouTubeApiCredentials credentials, YouTubePlaylist youTubePlaylist)
			throws IOException
	{
		delete(credentials, youTubePlaylist.getPlaylistId());
	}
	
}
