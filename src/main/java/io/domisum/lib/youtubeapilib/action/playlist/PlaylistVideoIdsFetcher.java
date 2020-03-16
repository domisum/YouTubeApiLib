package io.domisum.lib.youtubeapilib.action.playlist;

import java.io.IOException;
import java.util.List;

public interface PlaylistVideoIdsFetcher
{

	default List<String> fetch(String playlistId) throws IOException
	{
		return fetch(playlistId, Integer.MAX_VALUE);
	}

	List<String> fetch(String playlistId, int maxNrOfVideos) throws IOException;

}
