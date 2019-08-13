package de.domisum.youtubevideouploadlib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Playlists.List;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.PlaylistSnippet;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.playlist.PlaylistIdFetcher;
import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class PlaylistIdFetcherUsingApi implements PlaylistIdFetcher
{

	private final Logger logger = LoggerFactory.getLogger(getClass());


	// CONSTANTS
	private static final long MAX_RESULTS_MAX = 50L;

	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// FETCH
	@Override public Optional<String> fetch(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException
	{
		String nextPageToken = null;
		do
		{
			PlaylistListResponse response = fetchPlaylists(nextPageToken);

			Optional<String> playlistIdOptional = extractPlaylist(response, youTubePlaylistSpec);
			if(playlistIdOptional.isPresent())
				return playlistIdOptional;

			nextPageToken = response.getNextPageToken();
			logger.debug("playlist wasn't contained in returned playlists, next page token: {}", nextPageToken);
		}
		while(nextPageToken != null);

		logger.debug("no next page token known, playlist doesn't exist");
		return Optional.empty();
	}

	private PlaylistListResponse fetchPlaylists(String pageToken) throws IOException
	{
		logger.debug("Fetching own playlists with page token '{}'", pageToken);

		YouTube youTube = authorizedYouTubeApiClient.getYouTubeApiClient();

		List playlistsListMineRequest = youTube.playlists().list("snippet,contentDetails");
		playlistsListMineRequest.setMine(true);
		playlistsListMineRequest.setMaxResults(MAX_RESULTS_MAX);
		if(pageToken != null)
			playlistsListMineRequest.setPageToken(pageToken);

		return playlistsListMineRequest.execute();
	}

	private Optional<String> extractPlaylist(PlaylistListResponse response, YouTubePlaylistSpec youTubePlaylistSpec)
	{
		for(Playlist playlist : response.getItems())
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
		PlaylistSnippet snippet = playlist.getSnippet();
		return snippet.getTitle().equalsIgnoreCase(youTubePlaylistSpec.getTitle());
	}

}
