package io.domisum.lib.youtubeapilib.action.playlist;

import io.domisum.lib.youtubeapilib.model.playlist.YouTubePlaylistSpec;

import java.io.IOException;

public interface PlaylistCreator
{
	
	String create(YouTubePlaylistSpec youTubePlaylistSpec)
			throws IOException;
	
}
