package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.PlaylistDeleter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaylistDeleterUsingApi
		implements PlaylistDeleter
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// UPLOAD
	@Override
	public void delete(String playlistId)
			throws IOException
	{
		var youtube = authorizedYouTubeApiClient.getYouTubeApiClient();
		youtube.playlists().delete(playlistId).execute();
	}
	
}
