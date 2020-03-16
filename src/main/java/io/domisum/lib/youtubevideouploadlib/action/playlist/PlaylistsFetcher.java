package io.domisum.lib.youtubevideouploadlib.action.playlist;

import io.domisum.lib.youtubevideouploadlib.model.playlist.YouTubePlaylist;

import java.io.IOException;
import java.util.Collection;

public interface PlaylistsFetcher
{

	Collection<YouTubePlaylist> fetchAll() throws IOException;

}
