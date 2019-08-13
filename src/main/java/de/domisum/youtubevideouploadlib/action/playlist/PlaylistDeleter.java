package de.domisum.youtubevideouploadlib.action.playlist;

import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylist;

import java.io.IOException;

public interface PlaylistDeleter
{

	void delete(String playlistId) throws IOException;

	default void delete(YouTubePlaylist youTubePlaylist) throws IOException
	{
		delete(youTubePlaylist.getPlaylistId());
	}

}
