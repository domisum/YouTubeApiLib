package io.domisum.lib.youtubeapilib.playlist.actors.impl;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeDataApiClientSource;
import io.domisum.lib.youtubeapilib.playlist.actors.PlaylistDeleter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaylistDeleterUsingApi
		implements PlaylistDeleter
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeDataApiClientSource authorizedYouTubeDataApiClientSource;
	
	
	// UPLOAD
	@Override
	public void delete(YouTubeApiCredentials credentials, String playlistId)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeDataApiClientSource.getFor(credentials).getYouTubeApiClient();
		
		var delete = youTubeDataApiClient.playlists().delete(playlistId);
		delete.execute();
	}
	
}
