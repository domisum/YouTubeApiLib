package io.domisum.lib.youtubevideouploadlib.action.playlist;

import io.domisum.lib.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;

import java.io.IOException;

public interface PlaylistCreator
{

	String create(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException;

}
