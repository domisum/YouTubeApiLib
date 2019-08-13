package de.domisum.youtubevideouploadlib.action.playlist;

import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;

import java.io.IOException;
import java.util.Optional;

public interface PlaylistIdFetcher
{

	Optional<String> fetch(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException;

}
