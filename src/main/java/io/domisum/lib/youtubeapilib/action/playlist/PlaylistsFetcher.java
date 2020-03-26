package io.domisum.lib.youtubeapilib.action.playlist;

import io.domisum.lib.youtubeapilib.model.playlist.YouTubePlaylist;

import java.io.IOException;
import java.util.Collection;

public interface PlaylistsFetcher
{
	
	Collection<YouTubePlaylist> fetchAll()
			throws IOException;
	
}
