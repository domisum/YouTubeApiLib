package de.domisum.youtubevideouploadlib.action.playlist;

import java.io.IOException;

public interface PlaylistVideoCountFetcher
{

	int fetch(String playlistId) throws IOException;

}
