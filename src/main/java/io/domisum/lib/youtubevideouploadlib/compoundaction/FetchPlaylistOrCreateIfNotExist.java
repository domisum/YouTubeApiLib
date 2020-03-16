package io.domisum.lib.youtubevideouploadlib.compoundaction;

import io.domisum.lib.auxiliumlib.util.java.annotations.API;
import io.domisum.lib.youtubevideouploadlib.action.playlist.PlaylistCreator;
import io.domisum.lib.youtubevideouploadlib.action.playlist.PlaylistIdFetcher;
import io.domisum.lib.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Optional;

@API
@RequiredArgsConstructor
public class FetchPlaylistOrCreateIfNotExist
{

	private final Logger logger = LoggerFactory.getLogger(getClass());


	// INPUT
	private final PlaylistIdFetcher playlistIdFetcher;
	private final PlaylistCreator playlistCreator;


	// UPLOAD
	@API
	public String fetch(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException
	{
		Optional<String> playlistIdOptional = getPreexistingPlaylistId(youTubePlaylistSpec);
		if(playlistIdOptional.isPresent())
		{
			logger.info("playlist {} already exists, returning id {}", youTubePlaylistSpec, playlistIdOptional.get());
			return playlistIdOptional.get();
		}

		logger.info("playlist {} doesn't exists, creating", youTubePlaylistSpec);
		return createNewPlaylist(youTubePlaylistSpec);
	}

	private Optional<String> getPreexistingPlaylistId(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException
	{
		return playlistIdFetcher.fetch(youTubePlaylistSpec);
	}

	private String createNewPlaylist(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException
	{
		return playlistCreator.create(youTubePlaylistSpec);
	}

}
