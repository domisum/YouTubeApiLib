package io.domisum.lib.youtubeapilib.video.actors.impl.upload;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube.Videos.Insert;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import io.domisum.lib.youtubeapilib.PrivacyStatus;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeDataApiClientSource;
import io.domisum.lib.youtubeapilib.video.YouTubeVideo;
import io.domisum.lib.youtubeapilib.video.actors.upload.VideoUploader;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoUploaderUsingApi
		extends YouTubeUploader
		implements VideoUploader
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeDataApiClientSource authorizedYouTubeDataApiClientSource;
	
	
	// UPLOAD
	@Override
	public String upload(YouTubeApiCredentials credentials, YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus)
			throws IOException
	{
		logger.info("Preparing to upload '{}' to YouTube ({})", youTubeVideo, privacyStatus);
		
		try(var videoInputStream = youTubeVideo.getVideoStream().getInputStream())
		{
			var videoContent = new InputStreamContent("video/*", videoInputStream);
			videoContent.setLength(youTubeVideo.getVideoStream().getLength());
			
			var videoToUploadSettings = buildVideoToUploadSettings(youTubeVideo, privacyStatus);
			
			logger.info("Starting video upload...");
			var uploadRequest = createUploadRequest(credentials, videoContent, videoToUploadSettings);
			var returnedVideo = uploadRequest.execute();
			logger.info("Video upload complete. Video id: {}", returnedVideo.getId());
			
			return returnedVideo.getId();
		}
	}
	
	
	// VIDEO METADATA
	private Video buildVideoToUploadSettings(YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus)
	{
		var videoToUpload = new Video();
		
		videoToUpload.setSnippet(createVideoSnippet(youTubeVideo));
		videoToUpload.setStatus(createVideoStatus(privacyStatus));
		
		return videoToUpload;
	}
	
	private VideoStatus createVideoStatus(PrivacyStatus privacyStatus)
	{
		var status = new VideoStatus();
		status.setPrivacyStatus(privacyStatus.name());
		return status;
	}
	
	private VideoSnippet createVideoSnippet(YouTubeVideo youTubeVideo)
	{
		var youTubeVideoMetadata = youTubeVideo.getMetadata();
		
		var snippet = new VideoSnippet();
		snippet.setTitle(youTubeVideoMetadata.getTitle());
		snippet.setDescription(youTubeVideoMetadata.getDescription());
		snippet.setTags(youTubeVideoMetadata.getTags());
		snippet.setCategoryId(youTubeVideoMetadata.getCategory().categoryId+"");
		
		return snippet;
	}
	
	
	// UPLOAD REQUEST
	private Insert createUploadRequest(YouTubeApiCredentials credentials, InputStreamContent videoContent, Video videoToUpload)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeDataApiClientSource.getFor(credentials).getYouTubeApiClient();
		
		var videoInsert = youTubeDataApiClient.videos().insert("snippet,statistics,status", videoToUpload, videoContent);
		configureMediaHttpUploader(videoInsert.getMediaHttpUploader(), videoContent.getLength());
		return videoInsert;
	}
	
}
