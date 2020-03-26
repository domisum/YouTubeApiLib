package io.domisum.lib.youtubeapilib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube.PlaylistItems;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.playlist.PlaylistVideoIdsFetcher;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PlaylistVideoIdsFetcherUsingApi
		implements PlaylistVideoIdsFetcher
{
	
	// CONSTANTS
	private static final long MAX_RESULTS_LIMIT = 50L;
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;
	
	
	// UPLOAD
	@Override
	public List<String> fetch(String playlistId, int maxNrOfVideos)
			throws IOException
	{
		var listRequest = createBaseRequest(playlistId);
		return fetchVideoIdsUsingRequest(listRequest, maxNrOfVideos);
	}
	
	private List<String> fetchVideoIdsUsingRequest(PlaylistItems.List listRequest, int maxNrOfVideos)
			throws IOException
	{
		PlaylistItemListResponse response;
		var videoIds = new ArrayList<String>();
		do
		{
			response = listRequest.execute();
			for(var item : response.getItems())
				videoIds.add(item.getContentDetails().getVideoId());
			
			listRequest.setPageToken(response.getNextPageToken());
		}
		while((response.getNextPageToken() != null) && (videoIds.size() < maxNrOfVideos));
		
		// remove excessive videos ids to exactly match requested number of videos
		while(videoIds.size() > maxNrOfVideos)
			videoIds.remove(videoIds.size()-1);
		
		return videoIds;
	}
	
	private PlaylistItems.List createBaseRequest(String playlistId)
			throws IOException
	{
		var listRequest = authorizedYouTubeApiClient
				.getYouTubeApiClient()
				.playlistItems()
				.list("snippet,contentDetails");
		listRequest.setMaxResults(MAX_RESULTS_LIMIT);
		listRequest.setPlaylistId(playlistId);
		
		return listRequest;
	}
	
}
