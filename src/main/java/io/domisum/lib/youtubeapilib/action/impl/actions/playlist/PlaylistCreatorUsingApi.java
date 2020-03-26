package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube.Playlists.Insert;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.PlaylistCreator;
import io.domisum.lib.youtubeapilib.model.playlist.YouTubePlaylistSpec;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaylistCreatorUsingApi
		implements PlaylistCreator
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// UPLOAD
	@Override
	public String create(YouTubePlaylistSpec youTubePlaylistSpec)
			throws IOException
	{
		var playlist = createRequestPlaylist(youTubePlaylistSpec);
		
		var playlistsInsertRequest = createInsertRequest(playlist);
		var response = playlistsInsertRequest.execute();
		
		return response.getId();
	}
	
	private Playlist createRequestPlaylist(YouTubePlaylistSpec youTubePlaylistSpec)
	{
		var playlist = new Playlist();
		
		var snippet = new PlaylistSnippet();
		snippet.setTitle(youTubePlaylistSpec.getTitle());
		snippet.setDescription(youTubePlaylistSpec.getDescription());
		playlist.setSnippet(snippet);
		
		var status = new PlaylistStatus();
		status.setPrivacyStatus(youTubePlaylistSpec.getPrivacyStatus().name());
		playlist.setStatus(status);
		return playlist;
	}
	
	private Insert createInsertRequest(Playlist playlist)
			throws IOException
	{
		var youtube = authorizedYouTubeApiClient.getYouTubeApiClient();
		return youtube.playlists().insert("snippet,status", playlist);
	}
	
}
