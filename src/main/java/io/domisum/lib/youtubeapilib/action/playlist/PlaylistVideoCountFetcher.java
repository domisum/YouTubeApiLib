package io.domisum.lib.youtubeapilib.action.playlist;

import java.io.IOException;

public interface PlaylistVideoCountFetcher
{

	int fetch(String playlistId) throws IOException;

}
