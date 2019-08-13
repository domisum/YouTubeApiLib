package de.domisum.youtubevideouploadlib.action.playlist;

import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylist;

import java.io.IOException;
import java.util.Collection;

public interface PlaylistsFetcher
{

	Collection<YouTubePlaylist> fetchAll() throws IOException;

}
