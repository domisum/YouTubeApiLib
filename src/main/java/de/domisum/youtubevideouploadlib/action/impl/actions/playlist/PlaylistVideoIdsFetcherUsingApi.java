package de.domisum.youtubevideouploadlib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube.PlaylistItems;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.playlist.PlaylistVideoIdsFetcher;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PlaylistVideoIdsFetcherUsingApi implements PlaylistVideoIdsFetcher
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// UPLOAD
	@Override public List<String> fetch(String playlistId, int maxNrOfVideos) throws IOException
	{
		PlaylistItems.List listRequest = createBaseRequest(playlistId);
		return fetchVideoIdsUsingRequest(listRequest, maxNrOfVideos);
	}

	private List<String> fetchVideoIdsUsingRequest(PlaylistItems.List listRequest, int maxNrOfVideos) throws IOException
	{
		PlaylistItemListResponse response;
		List<String> videoIds = new ArrayList<>();
		do
		{
			response = listRequest.execute();
			for(PlaylistItem item : response.getItems())
				videoIds.add(item.getContentDetails().getVideoId());

			listRequest.setPageToken(response.getNextPageToken());
		}
		while((response.getNextPageToken() != null) && (videoIds.size() < maxNrOfVideos));

		// remove excessive videos ids to exactly match requested number of videos
		while(videoIds.size() > maxNrOfVideos)
			videoIds.remove(videoIds.size()-1);

		return videoIds;
	}

	private PlaylistItems.List createBaseRequest(String playlistId) throws IOException
	{
		PlaylistItems.List listRequest = authorizedYouTubeApiClient.getYouTubeApiClient().playlistItems().list("snippet,contentDetails");
		listRequest.setMaxResults(50L);
		listRequest.setPlaylistId(playlistId);

		return listRequest;
	}

}
