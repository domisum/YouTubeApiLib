package de.domisum.youtubevideouploadlib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.PlaylistItems.Insert;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.ResourceId;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.playlist.VideoIntoPlaylistInserter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoIntoPlaylistUsingApi implements VideoIntoPlaylistInserter
{

	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// UPLOAD
	@Override
	public void insert(String playlistId, String videoId, InsertionPosition insertionPosition) throws IOException
	{
		PlaylistItem playlistItem = createPlaylistItem(playlistId, videoId, insertionPosition);
		Insert playlistItemsInsertRequest = createInsertRequest(playlistItem);
		playlistItemsInsertRequest.execute();
	}

	private PlaylistItem createPlaylistItem(String playlistId, String videoId, InsertionPosition insertionPosition)
	{
		ResourceId resourceId = new ResourceId();
		resourceId.set("kind", "youtube#video");
		resourceId.set("videoId", videoId);

		PlaylistItemSnippet snippet = new PlaylistItemSnippet();
		snippet.setPlaylistId(playlistId);
		snippet.setResourceId(resourceId);
		if(insertionPosition == InsertionPosition.FIRST)
			snippet.setPosition(0L);

		PlaylistItem playlistItem = new PlaylistItem();
		playlistItem.setSnippet(snippet);
		return playlistItem;
	}

	private Insert createInsertRequest(PlaylistItem playlistItem) throws IOException
	{
		YouTube youtube = authorizedYouTubeApiClient.getYouTubeApiClient();
		return youtube.playlistItems().insert("snippet", playlistItem);
	}

}
