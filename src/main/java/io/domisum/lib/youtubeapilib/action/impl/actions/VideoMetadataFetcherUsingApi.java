package io.domisum.lib.youtubeapilib.action.impl.actions;

import com.google.api.services.youtube.YouTube.Videos.List;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import io.domisum.lib.youtubeapilib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubeapilib.action.videometadata.VideoMetadataFetcher;
import io.domisum.lib.youtubeapilib.model.video.VideoCategory;
import io.domisum.lib.youtubeapilib.model.video.YouTubeVideoMetadata;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoMetadataFetcherUsingApi implements VideoMetadataFetcher
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// FETCH
	@Override
	public YouTubeVideoMetadata fetch(String videoId) throws IOException
	{
		List videosListByIdRequest = authorizedYouTubeApiClient.getYouTubeApiClient().videos().list("snippet");
		videosListByIdRequest.setId(videoId);

		VideoListResponse response = videosListByIdRequest.execute();
		java.util.List<Video> responseItems = response.getItems();
		if(responseItems.isEmpty())
			throw new IOException("no video with id '"+videoId+"' (might be invisible with current permissions)");


		Video video = responseItems.get(0);

		String title = video.getSnippet().getTitle();
		String description = video.getSnippet().getDescription();
		java.util.List<String> tags = video.getSnippet().getTags();
		String categoryIdString = video.getSnippet().getCategoryId();

		int categoryId = Integer.parseInt(categoryIdString);
		VideoCategory videoCategory = VideoCategory.fromCategoryId(categoryId);

		return new YouTubeVideoMetadata(title, description, tags, videoCategory);
	}

}
