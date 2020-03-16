package io.domisum.lib.youtubevideouploadlib.action.playlist;

import java.io.IOException;

public interface PlaylistVideoCountFetcher
{

	int fetch(String playlistId) throws IOException;

}
