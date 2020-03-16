package io.domisum.lib.youtubeapilib.action.playlist;

import java.io.IOException;

public interface VideoIntoPlaylistInserter
{

	void insert(String playlistId, String videoId, InsertionPosition insertionPosition) throws IOException;


	enum InsertionPosition
	{

		FIRST,
		LAST

	}

}
