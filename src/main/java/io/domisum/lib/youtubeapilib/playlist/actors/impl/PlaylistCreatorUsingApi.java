package io.domisum.lib.youtubeapilib.playlist.actors.impl;

import com.google.api.services.youtube.YouTube.Playlists.Insert;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeDataApiClientSource;
import io.domisum.lib.youtubeapilib.playlist.PlaylistSpecification;
import io.domisum.lib.youtubeapilib.playlist.actors.PlaylistCreator;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaylistCreatorUsingApi
		implements PlaylistCreator
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeDataApiClientSource authorizedYouTubeDataApiClientSource;
	
	
	// UPLOAD
	@Override
	public String create(YouTubeApiCredentials credentials, PlaylistSpecification playlistSpecification)
			throws IOException
	{
		var playlist = createRequestPlaylist(playlistSpecification);
		
		var playlistsInsertRequest = createInsertRequest(credentials, playlist);
		var response = playlistsInsertRequest.execute();
		String playlistId = response.getId();
		
		return playlistId;
	}
	
	private Playlist createRequestPlaylist(PlaylistSpecification youTubePlaylistSpec)
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
	
	private Insert createInsertRequest(YouTubeApiCredentials credentials, Playlist playlist)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeDataApiClientSource.getFor(credentials).getYouTubeApiClient();
		
		var insert = youTubeDataApiClient.playlists().insert("snippet,status", playlist);
		return insert;
	}
	
}
