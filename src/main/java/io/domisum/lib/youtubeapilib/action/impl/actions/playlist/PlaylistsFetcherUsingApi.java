package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import com.google.api.services.youtube.model.PlaylistListResponse;
import io.domisum.lib.auxiliumlib.annotations.API;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.PlaylistsFetcher;
import io.domisum.lib.youtubeapilib.model.PrivacyStatus;
import io.domisum.lib.youtubeapilib.model.playlist.YouTubePlaylist;
import io.domisum.lib.youtubeapilib.model.playlist.YouTubePlaylistSpec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@API
@RequiredArgsConstructor
public class PlaylistsFetcherUsingApi
		implements PlaylistsFetcher
{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	// CONSTANTS
	private static final long MAX_RESULTS_LIMIT = 50L;
	
	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// FETCH
	@Override
	public Collection<YouTubePlaylist> fetchAll()
			throws IOException
	{
		var playlists = new ArrayList<YouTubePlaylist>();
		
		String nextPageToken = null;
		do
		{
			var response = fetchPlaylists(nextPageToken);
			playlists.addAll(extractPlaylists(response));
			
			nextPageToken = response.getNextPageToken();
			logger.debug("next page token: {}", nextPageToken);
		}
		while(nextPageToken != null);
		
		return playlists;
	}
	
	private PlaylistListResponse fetchPlaylists(String pageToken)
			throws IOException
	{
		logger.debug("Fetching own playlists with page token '{}'", pageToken);
		
		var youTube = authorizedYouTubeApiClient.getYouTubeApiClient();
		
		var playlistsListRequest = youTube.playlists().list("snippet,status");
		playlistsListRequest.setMine(true);
		playlistsListRequest.setMaxResults(MAX_RESULTS_LIMIT);
		if(pageToken != null)
			playlistsListRequest.setPageToken(pageToken);
		
		return playlistsListRequest.execute();
	}
	
	private Collection<YouTubePlaylist> extractPlaylists(PlaylistListResponse response)
	{
		var playlists = new ArrayList<YouTubePlaylist>();
		
		for(var playlist : response.getItems())
		{
			String title = playlist.getSnippet().getTitle();
			String description = playlist.getSnippet().getDescription();
			String privacyStatusString = playlist.getStatus().getPrivacyStatus();
			var privacyStatus = PrivacyStatus.parse(privacyStatusString);
			String playlistId = playlist.getId();
			
			var spec = new YouTubePlaylistSpec(title, description, privacyStatus);
			var youTubePlaylist = new YouTubePlaylist(spec, playlistId);
			playlists.add(youTubePlaylist);
		}
		
		return playlists;
	}
	
}
