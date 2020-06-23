package io.domisum.lib.youtubeapilib.video.actors.impl.upload;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.youtube.YouTube.Thumbnails.Set;
import io.domisum.lib.youtubeapilib.apiclient.YouTubeApiCredentials;
import io.domisum.lib.youtubeapilib.apiclient.source.AuthorizedYouTubeApiClientSource;
import io.domisum.lib.youtubeapilib.video.actors.upload.VideoThumbnailUploader;
import lombok.RequiredArgsConstructor;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@RequiredArgsConstructor
public class VideoThumbnailUploaderUsingApi
		extends YouTubeUploader
		implements VideoThumbnailUploader
{
	
	// DEPENDENCIES
	private final AuthorizedYouTubeApiClientSource authorizedYouTubeApiClientSource;
	
	
	// UPLOAD
	@Override
	public void uploadThumbnail(YouTubeApiCredentials credentials, String videoId, BufferedImage thumbnail)
			throws IOException
	{
		logger.info("Preparing to upload thumbnail for video '{}'", videoId);
		
		// remove alpha channel because we upload as jpg and that format doesn't support it
		var thumbnailWithoutAlpha = removeImageAlphaChannel(thumbnail);
		
		// convert to byte array to be able to measure length, which is required for upload
		byte[] imageRaw = imageToByteArray(thumbnailWithoutAlpha, "jpg");
		try(var inputStream = new ByteArrayInputStream(imageRaw))
		{
			var imageContent = new InputStreamContent("image/jpeg", inputStream);
			imageContent.setLength(imageRaw.length);
			
			logger.info("Starting thumbnail upload...");
			createApiRequest(credentials, videoId, imageContent).execute();
			logger.info("Thumbnail upload complete");
		}
	}
	
	private BufferedImage removeImageAlphaChannel(BufferedImage originalImage)
	{
		int width = originalImage.getWidth();
		int height = originalImage.getHeight();
		
		var newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		int[] rgb = originalImage.getRGB(0, 0, width, height, null, 0, width);
		newImage.setRGB(0, 0, width, height, rgb, 0, width);
		
		return newImage;
	}
	
	
	// REQUEST
	private Set createApiRequest(YouTubeApiCredentials credentials, String videoId, InputStreamContent imageContent)
			throws IOException
	{
		var youTubeDataApiClient = authorizedYouTubeApiClientSource.getFor(credentials).getYouTubeDataApiClient();
		
		var thumbnailSet = youTubeDataApiClient.thumbnails().set(videoId, imageContent);
		configureMediaHttpUploader(thumbnailSet.getMediaHttpUploader(), imageContent.getLength());
		return thumbnailSet;
	}
	
	
	// UTIL
	private byte[] imageToByteArray(RenderedImage image, String imageFormat)
			throws IOException
	{
		var outputStream = new ByteArrayOutputStream();
		ImageIO.write(image, imageFormat, outputStream);
		return outputStream.toByteArray();
	}
	
}
