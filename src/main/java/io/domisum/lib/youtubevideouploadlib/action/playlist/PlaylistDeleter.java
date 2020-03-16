package io.domisum.lib.youtubevideouploadlib.action.playlist;

import io.domisum.lib.youtubevideouploadlib.model.playlist.YouTubePlaylist;

import java.io.IOException;

public interface PlaylistDeleter
{

	void delete(String playlistId) throws IOException;

	default void delete(YouTubePlaylist youTubePlaylist) throws IOException
	{
		delete(youTubePlaylist.getPlaylistId());
	}

}
