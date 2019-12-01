package de.domisum.youtubevideouploadlib.compoundaction;

import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.youtubevideouploadlib.action.playlist.PlaylistCreator;
import de.domisum.youtubevideouploadlib.action.playlist.PlaylistIdFetcher;
import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;
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
