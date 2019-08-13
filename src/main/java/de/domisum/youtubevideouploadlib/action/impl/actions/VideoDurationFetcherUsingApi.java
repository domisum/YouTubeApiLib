package de.domisum.youtubevideouploadlib.action.impl.actions;

import com.google.api.services.youtube.YouTube.Videos.List;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.videometadata.VideoDurationFetcher;
import de.domisum.youtubevideouploadlib.exceptions.VideoDoesNotExistException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.Duration;

@RequiredArgsConstructor
public class VideoDurationFetcherUsingApi implements VideoDurationFetcher
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// FETCH
	@Override
	public Duration fetch(String videoId) throws IOException
	{
		List videosListByIdRequest = authorizedYouTubeApiClient.getYouTubeApiClient().videos().list("contentDetails");
		videosListByIdRequest.setId(videoId);

		VideoListResponse response = videosListByIdRequest.execute();
		java.util.List<Video> responseItems = response.getItems();
		if(responseItems.isEmpty())
			throw new VideoDoesNotExistException();

		String durationString = responseItems.get(0).getContentDetails().getDuration();
		return Duration.parse(durationString);
	}

}
