package io.domisum.lib.youtubeapilib.playlist.actors.impl;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeApiClientSource;
import io.domisum.lib.youtubeapilib.playlist.PlaylistDoesNotExistException;
import io.domisum.lib.youtubeapilib.playlist.actors.PlaylistVideoCountFetcher;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@API
@RequiredArgsConstructor
public class PlaylistVideoCountFetcherUsingApi
		implements PlaylistVideoCountFetcher
{
	
	// REFERENCES
	private final AuthorizedYouTubeApiClientSource authorizedYouTubeApiClientSource;
	
	
	// FETCH
	@Override
	public int fetchVideoCount(YouTubeApiCredentials credentials, String playlistId)
			throws IOException
	{
		
		var youTubeDataApiClient = authorizedYouTubeApiClientSource.getFor(credentials).getYouTubeDataApiClient();
		
		var request = youTubeDataApiClient.playlists().list("contentDetails");
		request.setId(playlistId);
		var response = request.execute();
		
		if(response.getItems().isEmpty())
			throw new PlaylistDoesNotExistException();
		var contentDetails = response.getItems().get(0).getContentDetails();
		int videoCount = contentDetails.getItemCount().intValue();
		
		return videoCount;
	}
	
}
