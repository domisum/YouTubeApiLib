package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.PlaylistVideoCountFetcher;
import io.domisum.lib.youtubeapilib.exceptions.PlaylistDoesNotExistException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@API
@RequiredArgsConstructor
public class PlaylistVideoCountFetcherUsingApi
		implements PlaylistVideoCountFetcher
{
	
	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// FETCH
	@Override
	public int fetch(String playlistId)
			throws IOException
	{
		var youTube = authorizedYouTubeApiClient.getYouTubeApiClient();
		var request = youTube.playlists().list("contentDetails");
		request.setId(playlistId);
		
		var response = request.execute();
		if(response.getItems().isEmpty())
			throw new PlaylistDoesNotExistException();
		
		var contentDetails = response.getItems().get(0).getContentDetails();
		return contentDetails.getItemCount().intValue();
	}
	
}
