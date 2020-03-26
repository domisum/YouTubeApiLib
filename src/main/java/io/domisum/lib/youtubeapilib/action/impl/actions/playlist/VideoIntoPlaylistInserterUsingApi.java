package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube.PlaylistItems.Insert;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.VideoIntoPlaylistInserter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoIntoPlaylistInserterUsingApi
		implements VideoIntoPlaylistInserter
{
	
	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// UPLOAD
	@Override
	public void insert(String playlistId, String videoId, InsertionPosition insertionPosition)
			throws IOException
	{
		var playlistItem = createPlaylistItem(playlistId, videoId, insertionPosition);
		var playlistItemsInsertRequest = createInsertRequest(playlistItem);
		
		playlistItemsInsertRequest.execute();
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
	
	private Insert createInsertRequest(PlaylistItem playlistItem)
			throws IOException
	{
		var youtube = authorizedYouTubeApiClient.getYouTubeApiClient();
		return youtube.playlistItems().insert("snippet", playlistItem);
	}
	
}
