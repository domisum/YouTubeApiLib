package de.domisum.youtubevideouploadlib.action.impl.actions.upload;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Videos.Insert;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.upload.VideoUploader;
import de.domisum.youtubevideouploadlib.model.PrivacyStatus;
import de.domisum.youtubevideouploadlib.model.video.YouTubeVideo;
import de.domisum.youtubevideouploadlib.model.video.YouTubeVideoMetadata;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;

@RequiredArgsConstructor
public class VideoUploaderUsingApi extends Uploader implements VideoUploader
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// UPLOAD
	@Override public String upload(YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus) throws IOException
	{
		logger.info("Preparing to upload '{}' to YouTube ({})", youTubeVideo, privacyStatus);

		try(InputStream videoInputStream = youTubeVideo.getVideoStream().getInputStream())
		{
			InputStreamContent videoContent = new InputStreamContent("video/*", videoInputStream);
			videoContent.setLength(youTubeVideo.getVideoStream().getLength());

			Video videoToUploadSettings = buildVideoToUploadSettings(youTubeVideo, privacyStatus);

			logger.info("Starting video upload...");
			Insert uploadRequest = createUploadRequest(videoContent, videoToUploadSettings);
			Video returnedVideo = uploadRequest.execute();
			logger.info("Video upload complete. Video id: {}", returnedVideo.getId());

			return returnedVideo.getId();
		}
	}


	// VIDEO SETTINGS
	private Video buildVideoToUploadSettings(
			YouTubeVideo youTubeVideo, PrivacyStatus privacyStatus)
	{
		Video videoToUpload = new Video();

		videoToUpload.setSnippet(createVideoSnippet(youTubeVideo));
		videoToUpload.setStatus(createVideoStatus(privacyStatus));

		return videoToUpload;
	}

	private VideoStatus createVideoStatus(PrivacyStatus privacyStatus)
	{
		VideoStatus status = new VideoStatus();
		status.setPrivacyStatus(privacyStatus.name());

		return status;
	}

	private VideoSnippet createVideoSnippet(YouTubeVideo youTubeVideo)
	{
		YouTubeVideoMetadata youTubeVideoMetadata = youTubeVideo.getMetadata();

		VideoSnippet snippet = new VideoSnippet();
		snippet.setTitle(youTubeVideoMetadata.getTitle());
		snippet.setDescription(youTubeVideoMetadata.getDescription());
		snippet.setTags(youTubeVideoMetadata.getTags());
		snippet.setCategoryId(youTubeVideoMetadata.getCategory().categoryId+"");

		return snippet;
	}


	// UPLOAD REQUEST
	private Insert createUploadRequest(InputStreamContent videoContent, Video videoToUpload) throws IOException
	{
		YouTube youtube = authorizedYouTubeApiClient.getYouTubeApiClient();
		Insert videoInsert = youtube.videos().insert("snippet,statistics,status", videoToUpload, videoContent);
		configureMediaHttpUploader(videoInsert.getMediaHttpUploader(), videoContent.getLength());

		return videoInsert;
	}

}
