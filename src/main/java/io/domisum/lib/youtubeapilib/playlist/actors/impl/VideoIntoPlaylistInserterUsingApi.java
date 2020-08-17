package io.domisum.lib.youtubeapilib.playlist.actors.impl;

import com.google.api.services.youtube.YouTube.PlaylistItems.Insert;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeDataApiClientSource;
import io.domisum.lib.youtubeapilib.playlist.actors.VideoIntoPlaylistInserter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoIntoPlaylistInserterUsingApi
		implements VideoIntoPlaylistInserter
{
	
	// REFERENCES
	private final AuthorizedYouTubeDataApiClientSource authorizedYouTubeDataApiClientSource;
	
	
	// UPLOAD
	@Override
	public void insert(YouTubeApiCredentials credentials, String playlistId, String videoId, InsertionPosition insertionPosition)
			throws IOException
	{
		var playlistItem = createPlaylistItem(playlistId, videoId, insertionPosition);
		var insertRequest = createInsertRequest(credentials, playlistItem);
		
		insertRequest.execute();
	}
	
	private PlaylistItem createPlaylistItem(String playlistId, String videoId, InsertionPosition insertionPosition)
	{
		var resourceId = new ResourceId();
		resourceId.set("kind", "youtube#video");
		resourceId.set("videoId", videoId);
		
		var snippet = new PlaylistItemSnippet();
		snippet.setPlaylistId(playlistId);
		snippet.setResourceId(resourceId);
		if(insertionPosition == InsertionPosition.FIRST)
			snippet.setPosition(0L);
		
		var playlistItem = new PlaylistItem();
		playlistItem.setSnippet(snippet);
		return playlistItem;
	}
	
	private Insert createInsertRequest(YouTubeApiCredentials credentials, PlaylistItem playlistItem)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeDataApiClientSource.getFor(credentials).getYouTubeApiClient();
		
		var insert = youTubeDataApiClient.playlistItems().insert("snippet", playlistItem);
		return insert;
	}
	
}
