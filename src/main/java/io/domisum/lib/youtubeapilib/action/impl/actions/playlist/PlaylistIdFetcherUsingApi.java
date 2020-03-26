package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.PlaylistIdFetcher;
import io.domisum.lib.youtubeapilib.model.playlist.YouTubePlaylistSpec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class PlaylistIdFetcherUsingApi
		implements PlaylistIdFetcher
{
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	
	// CONSTANTS
	private static final long MAX_RESULTS_LIMIT = 50L;
	
	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// FETCH
	@Override
	public Optional<String> fetch(YouTubePlaylistSpec youTubePlaylistSpec)
			throws IOException
	{
		String nextPageToken = null;
		do
		{
			var response = fetchPlaylists(nextPageToken);
			
			var playlistIdOptional = extractPlaylist(response, youTubePlaylistSpec);
			if(playlistIdOptional.isPresent())
				return playlistIdOptional;
			
			nextPageToken = response.getNextPageToken();
			logger.debug("playlist wasn't contained in returned playlists, next page token: {}", nextPageToken);
		}
		while(nextPageToken != null);
		
		logger.debug("no next page token known, playlist doesn't exist");
		return Optional.empty();
	}
	
	private PlaylistListResponse fetchPlaylists(String pageToken)
			throws IOException
	{
		logger.debug("Fetching own playlists with page token '{}'", pageToken);
		
		var youTube = authorizedYouTubeApiClient.getYouTubeApiClient();
		var playlistsListRequest = youTube.playlists().list("snippet,contentDetails");
		playlistsListRequest.setMine(true);
		playlistsListRequest.setMaxResults(MAX_RESULTS_LIMIT);
		if(pageToken != null)
			playlistsListRequest.setPageToken(pageToken);
		
		return playlistsListRequest.execute();
	}
	
	private Optional<String> extractPlaylist(PlaylistListResponse response, YouTubePlaylistSpec youTubePlaylistSpec)
	{
		for(var playlist : response.getItems())
			if(doesPlaylistMatch(youTubePlaylistSpec, playlist))
			{
				String playlistId = playlist.getId();
				logger.debug("found playlist, id: {}", playlistId);
				return Optional.of(playlistId);
			}
		
		return Optional.empty();
	}
	
	
	// CONDITION UTIL
	private boolean doesPlaylistMatch(YouTubePlaylistSpec youTubePlaylistSpec, Playlist playlist)
	{
		var snippet = playlist.getSnippet();
		return snippet.getTitle().equalsIgnoreCase(youTubePlaylistSpec.getTitle());
	}
	
}
