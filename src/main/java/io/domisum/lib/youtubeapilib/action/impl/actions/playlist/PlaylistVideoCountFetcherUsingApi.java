package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Playlists.List;
import com.google.api.services.youtube.model.PlaylistContentDetails;
import com.google.api.services.youtube.model.PlaylistListResponse;
import io.domisum.lib.auxiliumlib.util.java.annotations.API;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.PlaylistVideoCountFetcher;
import io.domisum.lib.youtubeapilib.exceptions.PlaylistDoesNotExistException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@API
@RequiredArgsConstructor
public class PlaylistVideoCountFetcherUsingApi implements PlaylistVideoCountFetcher
{

	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// FETCH
	@Override
	public int fetch(String playlistId) throws IOException
	{
		YouTube youTube = authorizedYouTubeApiClient.getYouTubeApiClient();
		List request = youTube.playlists().list("contentDetails");
		request.setId(playlistId);

		PlaylistListResponse response = request.execute();
		if(response.getItems().isEmpty())
			throw new PlaylistDoesNotExistException();

		PlaylistContentDetails contentDetails = response.getItems().get(0).getContentDetails();
		return contentDetails.getItemCount().intValue();
	}

}
