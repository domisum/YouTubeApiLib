package de.domisum.youtubevideouploadlib.action.playlist;

import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;

import java.io.IOException;

public interface PlaylistCreator
{

	String create(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException;

}
