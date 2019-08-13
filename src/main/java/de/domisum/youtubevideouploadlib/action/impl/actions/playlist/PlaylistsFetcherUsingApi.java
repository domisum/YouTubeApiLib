package de.domisum.youtubevideouploadlib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Playlists.List;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistListResponse;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.playlist.PlaylistsFetcher;
import de.domisum.youtubevideouploadlib.model.PrivacyStatus;
import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylist;
import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@API
@RequiredArgsConstructor
public class PlaylistsFetcherUsingApi implements PlaylistsFetcher
{

	private final Logger logger = LoggerFactory.getLogger(getClass());


	// CONSTANTS
	private static final long MAX_RESULTS_MAX = 50L;

	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// FETCH
	@Override public Collection<YouTubePlaylist> fetchAll() throws IOException
	{
		java.util.List<YouTubePlaylist> playlists = new ArrayList<>();

		String nextPageToken = null;
		do
		{
			PlaylistListResponse response = fetchPlaylists(nextPageToken);
			playlists.addAll(extractPlaylists(response));

			nextPageToken = response.getNextPageToken();
			logger.debug("next page token: {}", nextPageToken);
		}
		while(nextPageToken != null);

		return playlists;
	}

	private PlaylistListResponse fetchPlaylists(String pageToken) throws IOException
	{
		logger.debug("Fetching own playlists with page token '{}'", pageToken);

		YouTube youTube = authorizedYouTubeApiClient.getYouTubeApiClient();

		List playlistsListMineRequest = youTube.playlists().list("snippet,status");
		playlistsListMineRequest.setMine(true);
		playlistsListMineRequest.setMaxResults(MAX_RESULTS_MAX);
		if(pageToken != null)
			playlistsListMineRequest.setPageToken(pageToken);

		return playlistsListMineRequest.execute();
	}

	private Collection<YouTubePlaylist> extractPlaylists(PlaylistListResponse response)
	{
		java.util.List<YouTubePlaylist> playlists = new ArrayList<>();

		for(Playlist playlist : response.getItems())
		{
			String title = playlist.getSnippet().getTitle();
			String description = playlist.getSnippet().getDescription();
			String privacyStatusString = playlist.getStatus().getPrivacyStatus();
			PrivacyStatus privacyStatus = PrivacyStatus.parse(privacyStatusString);

			String playlistId = playlist.getId();

			YouTubePlaylistSpec spec = new YouTubePlaylistSpec(title, description, privacyStatus);
			YouTubePlaylist youTubePlaylist = new YouTubePlaylist(spec, playlistId);
			playlists.add(youTubePlaylist);
		}

		return playlists;
	}

}
