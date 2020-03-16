package io.domisum.lib.youtubevideouploadlib.action.playlist;

import io.domisum.lib.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;

import java.io.IOException;
import java.util.Optional;

public interface PlaylistIdFetcher
{

	Optional<String> fetch(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException;

}
