package io.domisum.lib.youtubeapilib.playlist.actors.impl;

import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeApiClientSource;
import io.domisum.lib.youtubeapilib.playlist.actors.PlaylistDeleter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaylistDeleterUsingApi
		implements PlaylistDeleter
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClientSource authorizedYouTubeApiClientSource;
	
	
	// UPLOAD
	@Override
	public void delete(YouTubeApiCredentials credentials, String playlistId)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeApiClientSource.getFor(credentials).getYouTubeDataApiClient();
		
		var delete = youTubeDataApiClient.playlists().delete(playlistId);
		delete.execute();
	}
	
}
